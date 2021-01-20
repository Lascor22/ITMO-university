module Block3Task2
                  (
                    NonEmpty((:|)),
                    ThisOrThat(This, That, Both)
                  ) where

data ThisOrThat a b
                = This a
                | That b
                | Both a b deriving (Show, Eq)

data NonEmpty a
            = a :| [a] deriving (Show, Eq)

instance Semigroup (NonEmpty a) where
    (<>) (a :| as) (b :| bs) = a :| (as ++ [b] ++ bs)

instance (Semigroup a, Semigroup b) => Semigroup (ThisOrThat a b) where
    (<>) (This a) (This b)     = This (a <> b)
    (<>) (That a) (That b)     = That (a <> b)
    (<>) (This a) (That b)     = Both a b
    (<>) (That a) (This b)     = Both b a
    (<>) (This a) (Both b c)   = Both (a <> b) c
    (<>) (That a) (Both b c)   = Both b (a <> c)
    (<>) (Both a b) (This c)   = Both (a <> c) b
    (<>) (Both a b) (That c)   = Both a (b <> c)
    (<>) (Both a b) (Both c d) = Both (a <> c) (b <> d)
