{-# LANGUAGE BlockArguments #-}

module Block1.Task1Test
                     (
                       test
                     ) where
import Block1.Task1 (stringSum)
import Test.Tasty (TestTree)
import Test.Tasty.Hspec (describe, it, shouldBe, testSpec)

test :: IO TestTree
test =
  testSpec "stringSum" $ do
    describe "simpleTest" $ do
      it "1 2 3 4" $ stringSum "1 2 3 4" `shouldBe` Just (10::Int)
      it "1 1" $ stringSum "1 1" `shouldBe` Just (2::Int)
      it "0" $ stringSum "0" `shouldBe` Just (0::Int)
      it "100000 -30000" $ stringSum "100000 -30000" `shouldBe` Just (70000::Int)
      it "-1 -2 -3 -4" $ stringSum "-1 -2 -3 -4" `shouldBe` Just (-10::Int)

    describe "parse error" $ do
      it "test 1" $ stringSum "a" `shouldBe` Nothing
      it "test 2" $ stringSum "1 a" `shouldBe` Nothing
      it "test 3" $ stringSum "1 2 3 4 a 5 6 7" `shouldBe` Nothing
      it "test 4" $ stringSum "1o3-3 -3 2" `shouldBe` Nothing
      it "test 5" $ stringSum "nothing to say" `shouldBe` Nothing
