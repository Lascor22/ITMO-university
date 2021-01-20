{-# LANGUAGE BlockArguments #-}

module Block3Task2Test
                     (
                         test
                     ) where
import Block3Task2 (NonEmpty ((:|)), ThisOrThat (..))
import Test.Tasty (TestTree)
import Test.Tasty.Hspec (describe, it, shouldBe, testSpec)

test :: IO TestTree
test =
  testSpec "Semigroup" $ do
    describe "<> tests" $ do
      it "<> test 1" $ ("a" :| ["b"] <> "c" :| ["d"]) `shouldBe` "a" :| ["b", "c", "d"]
      it "<> test 2" $ ("a" :| [] <> []:|[]) `shouldBe` "a" :| [""]
      it "<> test 3" $ ([] :| [] <> ["a"]:|[]) `shouldBe` [] :| [["a"]]

    describe "ThisOrThat tests" $ do
      it "ThisOrThat test 1" $ getThis "abc" <> getThis "def" `shouldBe` getThis "abcdef"
      it "ThisOrThat test 2" $ getThat "bcd" <> getThat "efd" `shouldBe` getThat "bcdefd"
      it "ThisOrThat test 3" $ getThis "bcd" <> getThat "efd" `shouldBe` getBoth "bcd" "efd"
      it "ThisOrThat test 4" $ getThat "bcd" <> getThis "efd" `shouldBe` getBoth "efd" "bcd"
      it "ThisOrThat test 5" $ getThat "bcd" <> getBoth "efd" "xyz" `shouldBe` getBoth "efd" "bcdxyz"
      it "ThisOrThat test 6" $ getThis "bcd" <> getBoth "efd" "xyz" `shouldBe` getBoth "bcdefd" "xyz"
      it "ThisOrThat test 7" $ getBoth "bcd" "xyz" <> getThis "efd" `shouldBe` getBoth "bcdefd" "xyz"
      it "ThisOrThat test 8" $ getBoth "bcd" "xyz" <> getThat "efd" `shouldBe` getBoth "bcd" "xyzefd"
      it "ThisOrThat test 9" $ getBoth "bcd" "xyz" <> getBoth "efd" "ctf" `shouldBe` getBoth "bcdefd" "xyzctf"

getThis :: String -> ThisOrThat String String
getThis s = This s :: ThisOrThat String String

getThat :: String -> ThisOrThat String String
getThat s = That s :: ThisOrThat String String

getBoth :: String -> String -> ThisOrThat String String
getBoth s t = Both s t :: ThisOrThat String String
