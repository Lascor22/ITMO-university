{-# LANGUAGE InstanceSigs #-}

module Block1Task3
            (
                Tree(Leaf, Node),
                isEmpty,
                treeSize,
                findElement,
                insertElement,
                deleteElement,
                fromList
            ) where


data Tree a
         = Leaf
         | Node [a] (Tree a) (Tree a)
         deriving (Show, Eq)


isEmpty :: Tree a -> Bool
isEmpty Leaf = True
isEmpty _    = False

treeSize :: Tree a -> Int
treeSize (Node xs l r) = (length xs) + (treeSize l) + (treeSize r)
treeSize Leaf          = 0

findElement :: (Ord a) => Tree a -> a -> Maybe a
findElement (Node [] _ _) _ = error "node can't be empty in Tree"
findElement (Node (x:_) l r) e | x == e = Just x
                               | x > e  = findElement l e
                               | x < e  = findElement r e
findElement Leaf            _           = Nothing

insertElement :: (Ord a) => Tree a -> a -> Tree a
insertElement (Node [] _ _) _ = error "node can't be empty in Tree"
insertElement (Node xs@(x:_) l r) e | x == e = Node (e:xs) l r
                                    | x > e  = Node xs (insertElement l e) r
                                    | x < e  = Node xs l (insertElement r e)
insertElement Leaf e = Node [e] Leaf Leaf

fromList :: (Ord a) => [a] -> Tree a
fromList lst = recursiveFromList lst Leaf where
    recursiveFromList (x:xs) tree = recursiveFromList xs $ insertElement tree x
    recursiveFromList [] tree     = tree

deleteElement :: (Ord a) => Tree a -> a -> Tree a
deleteElement (Node [] _ _) _ = error "node can't be empty in Tree"
deleteElement (Node lst@(x:xs) l r) e | x > e                  = Node lst (deleteElement l e) r
                                      | x < e                  = Node lst l (deleteElement r e)
                                      | x == e && not (null xs) = Node xs l r
                                      | otherwise              = deleteRoot l r where
    findMin (Node ys Leaf _) = ys
    findMin (Node _ left _)  = findMin left
    findMin Leaf             = error "empty tree"
    deleteRoot Leaf right = right
    deleteRoot left Leaf  = left
    deleteRoot left right = Node (findMin right) left (deleteAllElements right (head (findMin right)))
    deleteAllElements (Node [] _ _) _ = error "node can't be empty in Tree"
    deleteAllElements (Node (y:_) left right) z | z < y  = deleteAllElements left z
                                                      | z > y  = deleteAllElements right z
                                                      | z == y = deleteRoot left right
deleteElement Leaf _ = error "element doesn't contains in Tree"


instance Foldable Tree where
    foldr :: (a -> b -> b) -> b -> Tree a -> b
    foldr _ z Leaf         = z
    foldr f z (Node e l r) = foldr f (foldr f (foldr f z r) e) l

    foldMap :: Monoid m => (a -> m) -> Tree a -> m
    foldMap _ Leaf         = mempty
    foldMap f (Node e l r) = foldMap f l `mappend` foldMap f e `mappend` foldMap f r
