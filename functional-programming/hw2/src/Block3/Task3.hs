module Block3.Task3
                (
                    bracketParser,
                    intParser
                ) where

import Control.Applicative (Alternative (some, (<|>)))
import Data.Char (digitToInt, isDigit)

import Block3.Task1 (Parser)
import Block3.Task2 (element, eof, ok, satisfy)

intParser :: Parser Char Int
intParser = unsignedInt <|> sign <*> unsignedInt where
    sign = negate <$ element '-' <|> id <$ element '+'
    unsignedInt = foldl (\acc val -> acc * 10 + val) 0 <$> some (digitToInt <$> satisfy isDigit)

bracketParser :: Parser Char ()
bracketParser = recursiveParser <* eof where
    recursiveParser = (element '(' *> recursiveParser *> element ')' *> recursiveParser) <|> ok
