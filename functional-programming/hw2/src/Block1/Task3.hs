module Block1.Task3
                (
                    NonEmpty(..)
                ) where

import Control.Applicative (Applicative (liftA2))

data NonEmpty a = a :| [a] deriving (Show)

instance Functor NonEmpty where
    fmap f (x :| xs) = f x :| fmap f xs

instance Applicative NonEmpty where
    pure x = x :| []
    fs <*> xs = do
            f <- fs
            f <$> xs

instance Foldable NonEmpty where
    foldMap f (x :| xs) = f x `mappend` foldMap f xs

instance Traversable NonEmpty where
    traverse f (x :| xs) = liftA2 (:|) (f x) (traverse f xs)

instance Monad NonEmpty where
    (x :| xs) >>= f = y :| (ys ++ ys') where
        y :| ys = f x
        ys' = xs >>= toList . f
        toList (z :| zs) = z : zs
