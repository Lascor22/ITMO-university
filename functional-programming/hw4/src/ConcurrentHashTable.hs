{-# LANGUAGE BangPatterns #-}

module ConcurrentHashTable (
                         ConcurrentHashTable(..),
                         newCHT,
                         getCHT,
                         putCHT,
                         sizeCHT
                         ) where


import Control.Concurrent.STM (STM, TArray, TMVar, TVar, atomically, newTMVar, newTVar, readTMVar,
                               readTVar, writeTVar)
import Control.Monad (forM_)
import Data.Array.MArray (getBounds, getElems, newArray, readArray, writeArray)
import Data.Hashable (Hashable (..))


data ConcurrentHashTable k v = ConcurrentHashTable
    { size    :: TVar Int
    , content :: TMVar (TArray Int (Maybe (k, v)))
    }


newCHT  :: IO (ConcurrentHashTable k v)
newCHT = atomically $ do
     initSize <- newTVar 0
     array <- newArray (0, 256) Nothing
     initContent <- newTMVar array
     return $ ConcurrentHashTable initSize initContent

getCHT :: (Hashable k, Eq k) => k -> ConcurrentHashTable k v -> IO (Maybe v)
getCHT key table = atomically $ do
     array <- readTMVar (content table)
     (!left, !right) <- getBounds array
     let !hash_i = hash key `mod` right
     findElem key hash_i left right array

putCHT :: (Hashable k, Eq k) => k -> v -> ConcurrentHashTable k v -> IO ()
putCHT key value table = atomically $ do
     array <- readTMVar (content table)
     oldSize <- readTVar (size table)
     (left, right) <- getBounds array
     arr <- ensureCapacity left right (oldSize + 1) array
     (l, r) <- getBounds arr
     updateArray l r arr (Just (key, value))
     writeTVar (size table) (oldSize + 1)

sizeCHT :: ConcurrentHashTable k v -> IO Int
sizeCHT = atomically . readTVar . size


nextInd :: Int -> Int -> Int -> Int
nextInd !i !l !r = if i + 1 == r then l else i + 1

findElIndex :: (Eq k) => k -> Int -> Int -> Int -> TArray Int (Maybe(k, v)) -> STM Int
findElIndex key !i !l !r array = do
     e <- readArray array i
     case e of
          Nothing -> return i
          Just (k, _) -> if key == k
               then
                    return i
               else
                    findElIndex key (nextInd i l r) l r array

findElem :: (Eq k) => k -> Int -> Int -> Int -> TArray Int (Maybe(k, v)) -> STM(Maybe v)
findElem k !i !l !r array = findElIndex k i l r array >>= readArray array >>= \x ->
     case x of
     Nothing     -> return Nothing
     Just (_, v) -> return $ Just v



ensureCapacity :: (Hashable k, Eq k) => Int -> Int -> Int -> TArray Int (Maybe(k, v)) -> STM(TArray Int (Maybe(k, v)))
ensureCapacity !left !right !sz oldArray =
     if fromIntegral sz < (0.8::Double) * fromIntegral (right - left)
     then
          return oldArray
     else do
          array <- newArray (left, 2 * right - left) Nothing
          updateHash left (2 * right - left) oldArray array
          return array

updateHash :: (Hashable k, Eq k) => Int -> Int -> TArray Int (Maybe(k, v)) -> TArray Int (Maybe(k, v)) -> STM ()
updateHash !left !right old new = do
     elems <- getElems old
     forM_ elems (updateArray left right new)

updateArray :: (Hashable k, Eq k) => Int -> Int -> TArray Int (Maybe(k, v)) -> Maybe(k, v) -> STM ()
updateArray _ _ _ Nothing = return ()
updateArray !l !r array entry@(Just (key, _)) = do
     let !hash_i = hash key `mod` r
     i <- findElIndex key hash_i l r array
     writeArray array i entry
