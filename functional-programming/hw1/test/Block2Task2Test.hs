{-# LANGUAGE BlockArguments #-}

module Block2Task2Test( 
                        test
                      ) where
import Block2Task2 (splitOn)
import Data.List.NonEmpty as NonEmpty (NonEmpty ((:|)))
import Test.Tasty (TestTree)
import Test.Tasty.Hspec (describe, it, shouldBe, testSpec)

test :: IO TestTree
test =
  testSpec "splitOn" $ do
    describe "splitOn" $ do
      it "splitOn test 1" $ splitOn '/' "path/to/file" `shouldBe` ("path"  NonEmpty.:| ["to", "file"])
      it "splitOn test 2" $ splitOn ' ' "nothing"`shouldBe` ("nothing"  NonEmpty.:| [])
      it "splitOn test 3" $ splitOn (1::Int) [1::Int, 3::Int, 1::Int, 4::Int, 1::Int, 5::Int]  `shouldBe` ([] NonEmpty.:| [[3::Int], [4::Int], [5::Int]])
