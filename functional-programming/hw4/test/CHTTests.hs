module CHTTests (
              test
               ) where

import Test.Tasty (TestTree)
import Test.Tasty.Hspec (describe, it, shouldBe, testSpec)

import ConcurrentHashTable (ConcurrentHashTable (..), newCHT, putCHT, sizeCHT)
import Control.Concurrent.Async (forConcurrently)
import Control.Monad (forM_, void)

putVals :: ConcurrentHashTable String String -> (Int -> Int) -> Int -> IO ()
putVals table f sz = forM_ (fmap f [1..sz]) $ \i -> putCHT ("k" ++ show (i * 1599)) ("v" ++ show i) table

parallelPut :: ConcurrentHashTable String String -> Int -> Int -> IO ()
parallelPut table th sz = void $ forConcurrently [1..th] (\num -> putVals table (\i -> num * sz + i) sz)

multyPut :: Int -> Int -> IO (ConcurrentHashTable String String)
multyPut th sz = do
    table <- newCHT
    parallelPut table th (sz `div` th)
    return table

testSize :: IO (ConcurrentHashTable String String) -> Int -> IO ()
testSize f s = do
    table <- f
    sz <- sizeCHT table
    sz `shouldBe` s

testCHT :: Int -> IO ()
testCHT th = testSize (multyPut th 10000) 10000

testPut :: IO ()
testPut = do
    t <- multyPut 2 1000
    parallelPut t 2 1000
    testSize (return t) 3000


test :: IO TestTree
test =
  testSpec "Concurrent hashtable" $ do
    describe "10^5 puts" $ do
      it "1 thread" $ do
        testCHT 1
      it "2 threads" $ do
        testCHT 2
      it "10 threads" $ do
        testCHT 10
      it "100 threads" $ do
        testCHT 100
    describe "parallel put" $ do
      it "test" $ do
        testPut
