module Block2Task2
                (
                    splitOn
                ) where

import Data.List.NonEmpty as NonEmpty (NonEmpty (..), (<|))

splitOn :: (Eq a) => a -> [a] -> NonEmpty [a]
splitOn delimiter = foldr privateSplitter ([] :| []) where
    privateSplitter x acc@(h :| t) | x == delimiter = [] <| acc
                                   | otherwise = (x : h) :| t
