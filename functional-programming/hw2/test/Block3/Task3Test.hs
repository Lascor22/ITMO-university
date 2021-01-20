{-# LANGUAGE BlockArguments #-}

module Block3.Task3Test
                     (
                       test
                     ) where

import Test.Tasty (TestTree)
import Test.Tasty.Hspec (describe, it, shouldBe, testSpec)

import Block3.Task1 (Parser (runParser))
import Block3.Task3 (bracketParser, intParser)

test :: IO TestTree
test =
  testSpec "simple parsers" $ do
    describe "intParser" $ do
      it "test 1" $ runParser intParser "0" `shouldBe` Just (0::Int, "")
      it "test 2" $ runParser intParser "1" `shouldBe` Just (1::Int, "")
      it "test 3" $ runParser intParser "-1" `shouldBe` Just ((-1)::Int, "")
      it "test 4" $ runParser intParser "12345" `shouldBe` Just (12345::Int, "")
      it "test 5" $ runParser intParser "+7" `shouldBe` Just (7::Int, "")
      it "test 6" $ runParser intParser "34abacab" `shouldBe` Just (34::Int,"abacab")
      it "test 7" $ runParser intParser "+79-31" `shouldBe` Just (79::Int,"-31")
      it "test 8" $ runParser intParser "-a" `shouldBe` Nothing

    describe "bracketParser" $ do
      it "test 1" $ runParser bracketParser "()" `shouldBe` Just ((), "")
      it "test 2" $ runParser bracketParser "" `shouldBe` Just ((), "")
      it "test 3" $ runParser bracketParser "(())" `shouldBe` Just ((), "")
      it "test 4" $ runParser bracketParser "()()()()()" `shouldBe` Just ((), "")
      it "test 5" $ runParser bracketParser "((())())" `shouldBe` Just ((), "")
      it "test 6" $ runParser bracketParser "()()(()())" `shouldBe` Just ((), "")
      it "test 7" $ runParser bracketParser "(())))" `shouldBe` Nothing
      it "test 8" $ runParser bracketParser "abacaba" `shouldBe` Nothing
      it "test 9" $ runParser bracketParser "(" `shouldBe` Nothing
      it "test 10" $ runParser bracketParser "))))))))0)" `shouldBe` Nothing
