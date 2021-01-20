{-# LANGUAGE BlockArguments #-}

module Block3.Task2Test
                     (
                       test
                     ) where

import Test.Tasty (TestTree)
import Test.Tasty.Hspec (describe, it, shouldBe, testSpec)

import Block3.Task1 (Parser (runParser))
import Block3.Task2 (element, eof, ok, satisfy, stream)

test :: IO TestTree
test =
  testSpec "parsers" $ do
    describe "ok" $ do
      it "test 1" $ runParser ok "test" `shouldBe` Just ((), "test")
      it "test 2" $ runParser ok "" `shouldBe` Just ((), "")

    describe "eof" $ do
      it "test 1" $ runParser eof "test" `shouldBe` Nothing
      it "test 2" $ runParser eof "" `shouldBe` Just ((), [])

    describe "satisfy" $ do
      it "test 1" $ (runParser $ satisfy (== 'a')) "abc" `shouldBe` Just ('a', "bc")
      it "test 2" $ (runParser $ satisfy (== False)) [False, True, False] `shouldBe` Just (False, [True, False])
      it "test 3" $ (runParser $ satisfy (== 'd')) "rtas" `shouldBe` Nothing
      it "test 4" $ (runParser $ satisfy (const False)) "abc" `shouldBe` Nothing
      it "test 5" $ (runParser $ satisfy (=='s')) "" `shouldBe` Nothing

    describe "element" $ do
      it "test 1" $ (runParser $ element False) [False, True] `shouldBe` Just (False, [True])
      it "test 2" $ (runParser $ element 'e') "abacaba" `shouldBe` Nothing
      it "test 3" $ (runParser $ element 'r') "rtas" `shouldBe` Just ('r', "tas")
      it "test 4" $ (runParser $ element (1::Int)) [2::Int] `shouldBe` Nothing

    describe "stream" $ do
      it "test 1" $ (runParser $ stream "test") "testing my func" `shouldBe` Just ("test", "ing my func")
      it "test 2" $ (runParser $ stream "none") "sample" `shouldBe` Nothing
      it "test 3" $ (runParser $ stream [False, True, True]) [False, True, True, False] `shouldBe` Just ([False, True, True], [False])

