{-# LANGUAGE BlockArguments #-}

module Block1Task3Test
  ( test
  ) where
import Block1Task3
import Test.Tasty (TestTree)
import Test.Tasty.Hspec (describe, it, shouldBe, testSpec)

test :: IO TestTree
test =
  testSpec "Binary Tree" $ do
    describe "isEmpty" $ do
      it "Leaf is empty" $ isEmpty Leaf `shouldBe` True
      it "Node isn't empty" $ isEmpty (Node [1::Int] Leaf Leaf) `shouldBe` False
    describe "treeSize" $ do
      it "Leaf size 0" $ treeSize Leaf `shouldBe` 0
      it "tree with size 3" $ treeSize (Node [2::Int] (Node [1::Int] Leaf Leaf) (Node [3::Int] Leaf Leaf)) `shouldBe` 3
      it "tree with size 5" $ treeSize (Node [1::Int, 1::Int, 1::Int, 1::Int, 1::Int] Leaf Leaf) `shouldBe` 5
      it "tree with size 1" $ treeSize (Node [100::Int] Leaf Leaf) `shouldBe` 1

    describe "findElement" $ do
      it "find in tree with size 1" $ findElement (Node [1::Int] Leaf Leaf) (1::Int) `shouldBe` Just (1::Int)
      it "find in tree with size 5" $ findElement (Node [1::Int, 1::Int, 1::Int, 1::Int] Leaf (Node [5::Int] Leaf Leaf)) (5::Int) `shouldBe` Just (5::Int)
      it "find in tree without element" $ findElement (Node [1::Int, 1::Int, 1::Int, 1::Int, 1::Int] (Node [0::Int] Leaf Leaf) (Node [5::Int] Leaf Leaf)) (13::Int) `shouldBe` Nothing

    describe "insertElement" $ do
      it "insert in empty tree" $ insertElement Leaf (1::Int) `shouldBe` Node [1::Int] Leaf Leaf
      it "insert in one vertex tree" $ insertElement (Node [100::Int] Leaf Leaf) (1::Int) `shouldBe` Node [100::Int] (Node [1::Int] Leaf Leaf) Leaf
      it "insert in 5 vertex tree 1" $ insertElement (Node [12::Int] (Node [5::Int, 5::Int] Leaf (Node [7::Int] Leaf Leaf)) (Node [15::Int] Leaf Leaf)) (3::Int) `shouldBe` Node [12::Int] (Node [5::Int, 5::Int] (Node [3::Int] Leaf Leaf) (Node [7::Int] Leaf Leaf)) (Node [15::Int] Leaf Leaf)
      it "insert in 5 vertex tree 2" $ insertElement (Node [12::Int] (Node [5::Int, 5::Int] Leaf (Node [7::Int] Leaf Leaf)) (Node [15::Int] Leaf Leaf)) (30::Int) `shouldBe` Node [12::Int] (Node [5::Int, 5::Int] Leaf (Node [7::Int] Leaf Leaf)) (Node [15::Int] Leaf (Node [30::Int] Leaf Leaf))
      it "insert in 5 vertex tree 3" $ insertElement (Node [12::Int] (Node [5::Int, 5::Int] Leaf (Node [7::Int] Leaf Leaf)) (Node [15::Int] Leaf Leaf)) (14::Int) `shouldBe` Node [12::Int] (Node [5::Int, 5::Int] Leaf (Node [7::Int] Leaf Leaf)) (Node [15::Int] (Node [14::Int] Leaf Leaf) Leaf)

    describe "fromList" $ do
      it "from list test 1" $ fromList [1::Int] `shouldBe` Node [1::Int] Leaf Leaf
      it "from list test 2" $ fromList [1::Int, 2::Int, 3::Int, 4::Int, 5::Int, 6::Int, 7::Int, 8::Int, 9::Int] `shouldBe` Node [1::Int] Leaf
                                                                                                                           (Node [2::Int] Leaf
                                                                                                                           (Node [3::Int] Leaf
                                                                                                                           (Node [4::Int] Leaf
                                                                                                                           (Node [5::Int] Leaf
                                                                                                                           (Node [6::Int] Leaf
                                                                                                                           (Node [7::Int] Leaf
                                                                                                                           (Node [8::Int] Leaf
                                                                                                                           (Node [9::Int] Leaf Leaf))))))))
      it "from list test 3" $ fromList [1::Int, 1::Int, 1::Int, 1::Int, 1::Int] `shouldBe` Node [1::Int, 1::Int, 1::Int, 1::Int, 1::Int] Leaf Leaf

    describe "deleteElement" $ do
      it "delete from tree test 1" $ deleteElement (Node [1::Int] Leaf Leaf) (1::Int) `shouldBe` Leaf
      it "delete from tree test 2" $ deleteElement (Node [1::Int, 1::Int] Leaf Leaf) (1::Int) `shouldBe` Node [1::Int] Leaf Leaf
      it "delete from tree test 3" $ deleteElement testTree (3::Int) `shouldBe` Node [5::Int] (Node [4::Int] Leaf Leaf) (Node [8::Int, 8::Int] Leaf Leaf)
      it "delete from tree test 4" $ deleteElement testTree (8::Int) `shouldBe` Node [5::Int] (Node [3::Int] Leaf (Node [4::Int] Leaf Leaf)) (Node [8] Leaf Leaf)
      it "delete from tree test 5" $ deleteElement testTree (5::Int) `shouldBe` Node [8::Int, 8::Int] (Node [3::Int] Leaf (Node [4::Int] Leaf Leaf)) Leaf


testTree :: Tree Int
testTree = Node [5::Int] (Node [3::Int] Leaf (Node [4::Int] Leaf Leaf)) (Node [8::Int, 8::Int] Leaf Leaf)

