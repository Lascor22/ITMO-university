module Block3Task1
                (
                    maybeConcat
                ) where

import Data.Maybe (fromMaybe)

maybeConcat :: [Maybe [a]] -> [a]
maybeConcat xs = fromMaybe [] $ mconcat xs
