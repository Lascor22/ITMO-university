{-# LANGUAGE BlockArguments #-}

module Block2.Task2Test
                     (
                       test
                     ) where
import Block2.Task2 (moving)
import Test.Tasty (TestTree)
import Test.Tasty.Hspec (describe, it, shouldBe, testSpec)

test :: IO TestTree
test =
  testSpec "moving" $ do
    describe "Simple Moving Average" $ do
      it "test 1" $ moving (4::Int) [1, 3, 5, 7, 9, 11, 13] `shouldBe` [1.0::Double, 2.0::Double, 3.0::Double, 4.0::Double, 6.0::Double, 8.0::Double, 10.0::Double]
      it "test 2" $ moving (4::Int) [1, 5, 3, 8, 7, 9, 6] `shouldBe` [1.0::Double, 3.0::Double, 3.0::Double, 4.25::Double, 5.75::Double, 6.75::Double, 7.5::Double]
      it "test 3" $ moving (2::Int) [1, 5, 3, 8, 7, 9, 6] `shouldBe` [1.0::Double, 3.0::Double, 4.0::Double, 5.5::Double, 7.5::Double, 8.0::Double, 7.5::Double]
      it "test 4" $ moving (2::Int) [1, 3, 5] `shouldBe` [1.0::Double, 2.0::Double, 4.0::Double]
