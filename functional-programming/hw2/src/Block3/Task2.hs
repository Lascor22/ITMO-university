module Block3.Task2
                (
                    ok,
                    eof,
                    satisfy,
                    element,
                    stream
                ) where

import Block3.Task1 (Parser (Parser))

ok :: Parser s ()
ok = Parser $ \s -> Just ((), s)

eof :: Parser s ()
eof = Parser f where
    f [] = Just ((), [])
    f _  = Nothing

satisfy :: (a -> Bool) -> Parser a a
satisfy p = Parser f where
    f [] = Nothing
    f (x:xs) | p x       = Just (x, xs)
             | otherwise = Nothing

element :: (Eq a) => a -> Parser a a
element x = satisfy (==x)

stream :: (Eq a) => [a] -> Parser a [a]
stream = traverse element
