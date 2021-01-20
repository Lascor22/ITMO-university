{-# LANGUAGE BlockArguments #-}

module Block1Task1Test
                     (
                       test
                     ) where
import Block1Task1
import Test.Tasty (TestTree)
import Test.Tasty.Hspec (describe, it, shouldBe, testSpec)

test :: IO TestTree
test =
  testSpec "Days of Week" $ do
    describe "nextDay" $ do
      it "Monday -> Tuesday" $ nextDay Monday `shouldBe` Tuesday
      it "Wednesday -> Thursday" $ nextDay Wednesday `shouldBe` Thursday
      it "Sunday -> Monday" $ nextDay Sunday `shouldBe` Monday

    describe "afterDays" $ do
      it "Friday is Monday after 4 days" $ Monday `afterDays` 4 `shouldBe` Friday
      it "Friday is Friday after 0 days" $ Friday `afterDays` 0 `shouldBe` Friday
      it "Saturday is Tuesday after 10 days" $ Saturday `afterDays` 10 `shouldBe` Tuesday

    describe "isWeekend" $ do
      it "Sunday is weekend" $ isWeekend Sunday `shouldBe` True
      it "Wednesday isn't weekend" $ isWeekend Wednesday `shouldBe` False
      it "Saturday is weekend" $ isWeekend Saturday `shouldBe` True

    describe "days to Friday" $ do
      it "from Friday to Friday 0 days" $ daysToParty Friday `shouldBe` 0
      it "from Saturday to Friday 6 days" $ daysToParty Saturday `shouldBe` 6
      it "from Wednesday to Fri 2 days" $ daysToParty Wednesday `shouldBe` 2
