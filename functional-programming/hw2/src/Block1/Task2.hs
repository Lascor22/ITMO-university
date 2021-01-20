module Block1.Task2
                (
                    Tree(..)
                ) where

data Tree a
    = Branch (Tree a) (Tree a)
    | Leaf a deriving Show

instance Functor Tree where 
    fmap f (Leaf x) = Leaf (f x)
    fmap f (Branch l r) = Branch (fmap f l) (fmap f r)

instance Applicative Tree where 
    pure = Leaf
    (Leaf f) <*> (Leaf x) = Leaf (f x)
    f@(Leaf _) <*> (Branch l r) = Branch (f <*> l) (f <*> r)
    (Branch f g) <*> x@(Leaf _) = Branch (f <*> x) (g <*> x) 
    (Branch f g) <*> (Branch x y) = Branch (f <*> x) (g <*> y)

instance Foldable Tree where
    foldMap f (Leaf x)     = f x
    foldMap f (Branch l r) = foldMap f l `mappend` foldMap f r
instance Traversable Tree where
    traverse f (Leaf x)     = Leaf <$> f x
    traverse f (Branch l r) = Branch <$> traverse f l <*> traverse f r
