module Block3.Task1
                (
                    Parser(..),
                ) where

import Control.Applicative (Alternative (empty, (<|>)))

newtype Parser s a
    = Parser { runParser :: [s] -> Maybe (a, [s]) }

instance Functor (Parser s) where
    fmap f (Parser p) = Parser $ \s -> do
                                    (x, xs) <- p s
                                    return (f x, xs)

instance Applicative (Parser s) where
    pure x = Parser $ \s -> Just (x, s)
    (Parser pf) <*> (Parser pa) = Parser $ \s -> do
                                        (f, fs) <- pf s
                                        (a, as) <- pa fs
                                        return (f a, as)

instance Monad (Parser s) where
    return x = Parser $ \s -> Just (x, s)
    Parser p >>= f = Parser $ \s -> do
                                (x, xs) <- p s
                                runParser (f x) xs

instance Alternative (Parser s) where
    empty = Parser $ const Nothing
    (Parser l) <|> (Parser r) = Parser $ \s -> l s <|> r s
