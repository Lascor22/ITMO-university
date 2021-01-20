{-# LANGUAGE BlockArguments #-}

module Block3Task1Test
                     (
                         test
                     ) where
import Block3Task1 (maybeConcat)

import Test.Tasty (TestTree)
import Test.Tasty.Hspec (describe, it, shouldBe, testSpec)

test :: IO TestTree
test =
  testSpec "maybeConcat" $ do
    describe "testing maybeConcat" $ do
      it "maybeConcat test 1" $ maybeConcat [Just "testing"] `shouldBe` "testing"
      it "maybeConcat test 2" $ maybeConcat [Just "testing", Nothing, Just "function", Nothing, Nothing, Just "reverse"] `shouldBe` "testingfunctionreverse"
      it "maybeConcat test 3" $ maybeConcat [Nothing] `shouldBe` ""
