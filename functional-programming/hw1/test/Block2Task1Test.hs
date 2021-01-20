{-# LANGUAGE BlockArguments #-}

module Block2Task1Test
                     (
                         test
                     ) where
import Block1Task3 (Tree (..), fromList)

import Data.Foldable (Foldable (toList))
import Data.List (sort)
import Test.Tasty (TestTree)
import Test.Tasty.Hspec (describe, it, shouldBe, testSpec)

test :: IO TestTree
test =
  testSpec "Foldable Tree" $ do
    describe "toList" $ do
      it "to list test 1" $ toList (Node [1::Int] Leaf Leaf) `shouldBe` [1::Int]
      it "to list test 2" $ toList testTree `shouldBe` [3::Int, 4::Int, 5::Int, 8::Int, 8::Int]
      it "to list test 3" $ toList (Node [5::Int, 5::Int, 5::Int, 5::Int, 5::Int] Leaf Leaf) `shouldBe` [5::Int, 5::Int, 5::Int, 5::Int, 5::Int]

    describe "sort" $ do
      it "toList . fromList test 1" $ (toList . fromList) [5::Int, 4::Int, 3::Int, 2::Int, 1::Int] `shouldBe` sort [5::Int, 4::Int, 3::Int, 2::Int, 1::Int]
      it "toList . fromList test 2" $ (toList . fromList) [5::Int, 4::Int, 3::Int, 2::Int, 1::Int, 2::Int, 3::Int, 4::Int, 5::Int] `shouldBe` sort [5::Int, 4::Int, 3::Int, 2::Int, 1::Int, 2::Int, 3::Int, 4::Int, 5::Int]
      it "toList . fromList test 3" $ (toList . fromList) [1::Int, 1::Int, 1::Int, 1::Int] `shouldBe` sort [1::Int, 1::Int, 1::Int, 1::Int]

    describe "foldr" $ do
      it "foldr test 1" $ foldr (\x y -> x ++ " " ++ y) "end" (Node ["root"] (Node ["left"] Leaf Leaf) (Node ["right"] Leaf Leaf)) `shouldBe` "left root right end"
      it "foldr test 2" $ foldr (+) (0::Int) (Node [5::Int, 5::Int, 5::Int, 5::Int, 5::Int] Leaf Leaf) `shouldBe` (25::Int)
      it "foldr test 3" $ foldr (*) (1::Int) testTree `shouldBe` (3840::Int)

    describe "foldMap" $ do
      it "foldMap test 1" $ foldMap show (Node [7::Int] (Node [5::Int] Leaf Leaf) (Node [10::Int] Leaf Leaf)) `shouldBe` "5710"
      it "foldMap test 2" $ foldMap show testTree `shouldBe` "34588"
      it "foldMap test 2" $ foldMap show (Node [500::Int] Leaf Leaf) `shouldBe` "500"

testTree:: Tree Int
testTree = Node [5::Int] (Node [3::Int] Leaf (Node [4::Int] Leaf Leaf)) (Node [8::Int, 8::Int] Leaf Leaf)
