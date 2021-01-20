module FileSystemTests (
                        test
                        ) where

import Test.Tasty (TestTree)
import Test.Tasty.Hspec (describe, it, shouldBe, testSpec)

import FileSystem (FS (Dir, File), cd, file, ls)
import Lens.Micro ((^..), (^?))

test :: IO TestTree
test = do
    testSpec "Filesystem tests" $ do
        describe "tests" $ do
            it "file found" $ fs ^? file "c" `shouldBe` Just "c"
            it "file not found" $ fs ^? file "a" `shouldBe` Nothing
            it "cd test 1" $ fs ^?  cd "a" . cd "b" . file "c" `shouldBe` Just "c"
            it "cd test 2" $ fs ^?  cd "b" . file "c" `shouldBe` Nothing
            it "ls test 1" $ fs ^.. cd "a" . cd "b" . ls `shouldBe` ["c"]
            it "ls test 2" $ fs ^.. cd "b" . ls `shouldBe` ["m", "a"]

fs :: FS
fs = Dir "/" [
        Dir "a" [
            Dir "b" [
                File "c"
            ]
        ],
        Dir "d" [],
        File "program.hs",
        File "c",
        Dir "b" [
            File "m",
            Dir "a" []
        ]
    ]
