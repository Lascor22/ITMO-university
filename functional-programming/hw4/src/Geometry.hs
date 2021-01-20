{-# LANGUAGE BangPatterns #-}
module Geometry (
                Point(..),
                plus,
                minus,
                scalarProduct,
                crossProduct,
                perimeterNaive,
                perimeter,
                doubleAreaNaive,
                doubleArea
                ) where

import Data.List (foldl')

data Point = Point
    {
        x :: !Int,
        y :: !Int
    }

lenToPoint :: Point -> Double
lenToPoint (Point x1 y1) = (sqrt . fromIntegral) (x1 * x1 + y1 * y1)

plus :: Point -> Point -> Point
plus (Point x1 y1) (Point x2 y2) = Point (x1 + x2) (y1 + y2)

minus :: Point -> Point -> Point
minus (Point x1 y1) (Point x2 y2) = Point (x1 - x2) (y1 - y2)

scalarProduct :: Point -> Point -> Int
scalarProduct (Point x1 y1) (Point x2 y2) = x1 * x2 + y1 * y2

crossProduct :: Point -> Point -> Int
crossProduct (Point x1 y1) (Point x2 y2) = x1 * y2 - x2 * y1

len :: Point -> Point -> Double
len p1 p2 = lenToPoint $ minus p1 p2

neighboringPairs :: [a] -> [(a, a)]
neighboringPairs xs = helper (head xs) xs where
    helper :: a -> [a] -> [(a, a)]
    helper p [e]          = [(e, p)]
    helper p (e:es@(t:_)) = (e, t) : helper p es
    helper _ []           = error "empty list"

neighboring :: (a -> a -> b) -> [a] -> [b]
neighboring f xs = fmap (uncurry f) (neighboringPairs xs)


perimeterNaive  :: [Point] -> Double
perimeterNaive points = foldl' (+) 0.0 (neighboring len points)

perimeter  :: [Point] -> Double
perimeter xs@(x1:_) = helper x1 xs 0.0 where
    helper :: Point -> [Point] -> Double -> Double
    helper p [lst] !acc           = acc + len lst p
    helper p (e:es@(next:_)) !acc = helper p es (acc + len e next)
    helper _ [] _                 = error "empty list"
perimeter [] = 0.0


doubleAreaNaive :: [Point] -> Int
doubleAreaNaive points = foldl' (+) 0 (neighboring crossProduct points)

doubleArea :: [Point] -> Int
doubleArea xs@(x1:_) = helper x1 xs 0 where
    helper :: Point -> [Point] -> Int -> Int
    helper p [lst] !acc           = acc + crossProduct lst p
    helper p (e:es@(next:_)) !acc = helper p es (acc + crossProduct e next)
    helper _ [] _                 = error "empty list"
doubleArea [] = 0
