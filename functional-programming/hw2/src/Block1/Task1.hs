module Block1.Task1
                (
                    stringSum
                ) where

import Text.Read (readMaybe)

stringSum :: String -> Maybe Int
stringSum = fmap sum . traverse readInt . words where
    readInt :: String -> Maybe Int
    readInt r = readMaybe r :: Maybe Int
