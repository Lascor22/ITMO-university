{-# LANGUAGE BlockArguments #-}

module Block1Task2Test (
                          test
                        ) where
import Block1Task2 (Nat (..), toInt)
import Test.Tasty (TestTree)
import Test.Tasty.Hspec (describe, it, shouldBe, testSpec)

test :: IO TestTree
test =
  testSpec "Natural number" $ do
    describe "to integer" $ do
      it "Z -> 0" $ toInt Z `shouldBe` 0
      it "S Z -> 1" $ toInt (S Z) `shouldBe` 1
      it "S (S Z) -> 2" $ toInt (S (S Z)) `shouldBe` 2
      it "S (S (S Z)) -> 3" $ toInt (S (S (S Z))) `shouldBe` 3

    describe "from Integer" $ do
      it "for 0" $ 0 `shouldBe` Z
      it "for 1" $ 1 `shouldBe` S Z
      it "for 3" $ 3 `shouldBe` S (S (S Z))
      it "for 5" $ 5 `shouldBe` S (S (S (S (S Z))))

    describe "sum" $ do
      it "test 1" $ generalSumTest 1 2 `shouldBe` 3
      it "test 2" $ generalSumTest 0 5 `shouldBe` 5
      it "test 3" $ generalSumTest 14 0 `shouldBe` 14
      it "test 4" $ generalSumTest 14 50 `shouldBe` 64
      it "test 5" $ generalSumTest 1400 530 `shouldBe` (1400 + 530)

    describe "multiply" $ do
      it "test 1" $ generalMulTest 1 2 `shouldBe` 2
      it "test 2" $ generalMulTest 0 5 `shouldBe` 0
      it "test 3" $ generalMulTest 14 0 `shouldBe` 0
      it "test 4" $ generalMulTest 14 50 `shouldBe` (14 * 50)
      it "test 5" $ generalMulTest 1400 530 `shouldBe` (1400 * 530)

    describe "subtract" $ do
      it "test 1" $ generalSubTest 2 1 `shouldBe` 1
      it "test 2" $ generalSubTest 5 0 `shouldBe` 5
      it "test 3" $ generalSubTest 14 5 `shouldBe` 9
      it "test 4" $ generalSubTest 50 14 `shouldBe` (50 - 14)
      it "test 5" $ generalSubTest 1400 530 `shouldBe` (1400 - 530)

generalOPTest :: (Nat -> Nat -> Nat) -> Integer -> Integer -> Integer
generalOPTest op a b = toInt (op (fromInteger a::Nat) (fromInteger b::Nat))

generalSumTest :: Integer -> Integer -> Integer
generalSumTest = generalOPTest (+)

generalMulTest :: Integer -> Integer -> Integer
generalMulTest = generalOPTest (*)

generalSubTest :: Integer -> Integer -> Integer
generalSubTest = generalOPTest (-)
