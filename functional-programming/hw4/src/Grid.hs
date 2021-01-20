{-# LANGUAGE DeriveFunctor #-}

module Grid(
            ListZipper(..),
            Grid(..),
            listLeft,
            listRight,
            listWrite,
            toList,
            mkZipper,
            up,
            down,
            left,
            right,
            gridRead,
            gridWrite
            ) where

import Control.Comonad (Comonad (..))

data ListZipper a = LZ [a] a [a]
    deriving Functor

newtype Grid a = Grid { unGrid :: ListZipper (ListZipper a) }
    deriving Functor

instance Comonad Grid where
    extract = gridRead
    duplicate = Grid . fmap horizontal . vertical

instance Comonad ListZipper where
    extract (LZ _ x _) = x
    duplicate = mkZipper listLeft listRight

listLeft:: ListZipper a -> ListZipper a
listLeft  (LZ (a:as) x bs) = LZ as a (x:bs)
listLeft _                 = error "Error"

listRight :: ListZipper a -> ListZipper a
listRight (LZ as x (b:bs)) = LZ (x:as) b bs
listRight _                = error "Error"

listWrite :: a -> ListZipper a -> ListZipper a
listWrite x (LZ ls _ rs) = LZ ls x rs

toList :: ListZipper a -> Int -> [a]
toList (LZ ls x rs) n = reverse (take n ls) ++ [x] ++ take n rs

iterateTail :: (a -> a) -> a -> [a]
iterateTail f = tail . iterate f

mkZipper :: (a -> a) -> (a -> a) -> a -> ListZipper a
mkZipper f g e = LZ (iterateTail f e) e (iterateTail g e)

up:: Grid a -> Grid a
up   (Grid g) = Grid (listLeft  g)

down :: Grid a -> Grid a
down (Grid g) = Grid (listRight g)

left:: Grid a -> Grid a
left  (Grid g) = Grid (fmap listLeft  g)

right :: Grid a -> Grid a
right (Grid g) = Grid (fmap listRight g)

gridRead :: Grid a -> a
gridRead (Grid g) = extract $ extract g

gridWrite :: a -> Grid a -> Grid a
gridWrite x (Grid g) = Grid $ listWrite (listWrite x (extract g)) g

horizontal:: Grid a -> ListZipper (Grid a)
horizontal = mkZipper left right

vertical :: Grid a -> ListZipper (Grid a)
vertical = mkZipper up down
