{-# LANGUAGE BlockArguments #-}

module Block2.Task1Test
                     (
                       test
                     ) where
import Block2.Task1
import Test.Tasty (TestTree)
import Test.Tasty.Hspec (describe, it, shouldBe, testSpec)

test :: IO TestTree
test =
  testSpec "eval" $ do
    describe "Const" $ do
      it "test 1" $ eval (Const 2) `shouldBe` Right (2::Int)
      it "test 2" $ eval (Const 0) `shouldBe` Right (0::Int)
      it "test 3" $ eval (Const (-1)) `shouldBe` Right ((-1)::Int)

    describe "Sum" $ do
      it "test 1" $ eval (Sum (Const 1) (Const 1)) `shouldBe` Right (2::Int)
      it "test 2" $ eval (Sum (Const 0) (Const 0)) `shouldBe` Right (0::Int)
      it "test 3" $ eval (Sum (Const 1) (Const (-1))) `shouldBe` Right (0::Int)
      it "test 4" $ eval (Sum (Const 100) (Const 40)) `shouldBe` Right (140::Int)

    describe "Sub" $ do
      it "test 1" $ eval (Sub (Const 1) (Const 1)) `shouldBe` Right (0::Int)
      it "test 2" $ eval (Sub (Const 0) (Const 0)) `shouldBe` Right (0::Int)
      it "test 3" $ eval (Sub (Const 1) (Const (-1))) `shouldBe` Right (2::Int)
      it "test 4" $ eval (Sub (Const 100) (Const 40)) `shouldBe` Right (60::Int)

    describe "Mul" $ do
      it "test 1" $ eval (Mul (Const 3) (Const 1)) `shouldBe` Right (3::Int)
      it "test 2" $ eval (Mul (Const 0) (Const 5)) `shouldBe` Right (0::Int)
      it "test 3" $ eval (Mul (Const 15) (Const (-7))) `shouldBe` Right ((-105)::Int)
      it "test 4" $ eval (Mul (Const 123) (Const 45)) `shouldBe` Right (5535::Int)

    describe "Pow" $ do
      it "test 1" $ eval (Pow (Const 2) (Const 1)) `shouldBe` Right (2::Int)
      it "test 2" $ eval (Pow (Const 500) (Const 5)) `shouldBe` Right (31250000000000::Int)
      it "test 3" $ eval (Pow (Const 15) (Const (-7))) `shouldBe` Left NegativePower
      it "test 4" $ eval (Pow (Const 1) (Const (-1))) `shouldBe` Left NegativePower
      it "test 5" $ eval (Pow (Const 0) (Const 1)) `shouldBe` Right (0::Int)

    describe "Div" $ do
      it "test 1" $ eval (Div (Const 2) (Const 1)) `shouldBe` Right (2::Int)
      it "test 2" $ eval (Div (Const 500) (Const 5)) `shouldBe` Right (100::Int)
      it "test 3" $ eval (Div (Const 15) (Const 0)) `shouldBe` Left DivisionByZero
      it "test 4" $ eval (Div (Const 1) (Const 0)) `shouldBe` Left DivisionByZero
      it "test 5" $ eval (Div (Const 0) (Const 1)) `shouldBe` Right (0::Int)
      it "test 6" $ eval (Div (Const 7) (Const 3)) `shouldBe` Right (2::Int)
      
    describe "Expression composition" $ do
      it "test 1" $ eval (Mul (Const 3) (Div (Const 2) (Const 1))) `shouldBe` Right (6::Int)
      it "test 2" $ eval (Sum (Const 17) (Div (Const 500) (Const 5))) `shouldBe` Right (117::Int)
      it "test 3" $ eval (Sum (Sum (Const 3) (Const 4)) (Mul (Const 10) (Const 7))) `shouldBe` Right (77::Int)
      it "test 4" $ eval (Mul (Const 100) (Div (Const 1) (Const 0))) `shouldBe` Left DivisionByZero
      it "test 5" $ eval (Pow (Const 10) (Sub (Div (Const 0) (Const 1)) (Const 1))) `shouldBe` Left NegativePower