module FSTests (
              test
               ) where

import Control.Exception (evaluate)

import Control.Monad.State (evalState, execState, runState)

import FSActions

import Resources (helpContent)

import StateActions (FS (..))

import Test.Tasty (TestTree)

import Test.Tasty.Hspec (describe, errorCall, it, shouldBe, shouldThrow, testSpec)

test :: IO TestTree
test =
  testSpec "File Manager" $ do

    describe "help test" $ do
      it "show help" $ evalState help ("", testFS1) `shouldBe` helpContent

    describe "information test" $ do
      it "test abs path" $ evalState (information "/a/b") ("/bin", testFS1) `shouldBe` "/a/b"
      it "test relative path" $ evalState (information "./../a/b") ("/bin", testFS1) `shouldBe` "/a/b"
      it "test empty path" $ evalState (information "") ("", testFS1) `shouldBe` "/"

    describe "cat test" $ do
      it "simple test" $ evalState  (cat "b") ("/a", testFS1) `shouldBe` "Content for file B"
      it "absolute path test" $ evalState (cat "/bin/bash") ("/a/e", testFS1) `shouldBe` "EXECUTE"
      it "relative path test" $ evalState (cat "../bin/bash") ("/a", testFS1) `shouldBe` "EXECUTE"
      it "test non existing path" $ evaluate (evalState (cat "non-existed-file") ("", testFS1))
                                            `shouldThrow` errorCall "File not found"

    describe "dir test" $ do
      it "test 1" $ evalState dir ("", testFS1) `shouldBe` ["a", "bin"]
      it "test 2" $ evalState dir ("/a", testFS1) `shouldBe` ["b", "e"]
      it "test 3" $ evalState dir ("/bin", testFS1) `shouldBe` ["bash"]
      it "test 4" $ evalState dir ("/a/e", testFS1) `shouldBe` ["w"]
      it "test non existing path" $ evaluate (evalState dir ("/a/b/c/d/e/f", testFS1))
                                      `shouldThrow` errorCall "Directory not found"
      it "test incorrect cwd" $ evaluate (evalState  dir ("/a/b", testFS1))
                                      `shouldThrow` errorCall "Directory not found"

    describe "pwd test" $ do
      it "test 1" $ evalState pwd ("", testFS2) `shouldBe` ""
      it "test 2" $ evalState pwd ("/a", testFS2) `shouldBe` "/a"
      it "test 3" $ evalState pwd ("/bin", testFS2) `shouldBe` "/bin"
      it "test 4" $ evalState pwd ("/usr/include", testFS2) `shouldBe` "/usr/include"

    describe "ls test" $ do
      it "test 1" $ evalState (ls "/a/e") ("/usr", testFS2) `shouldBe` ["w", "r"]
      it "test 2" $ evalState (ls "../bin") ("/usr", testFS2) `shouldBe` ["bash"]
      it "test 3" $ evalState (ls "./../../usr") ("/a/e", testFS2) `shouldBe` ["include", "conf"]
      it "test 4" $ evaluate (evalState (ls "./../../../../../../../../../usr") ("/a/e", testFS2))
                                        `shouldThrow` errorCall "Path does not exist"

    describe "cd test" $ do
      it "test 1" $ execState (cd "bin") ("", testFS2) `shouldBe` ("/bin", testFS2)
      it "test 2" $ execState (cd "../usr") ("/bin", testFS2) `shouldBe` ("/usr", testFS2)
      it "test 3" $ execState (cd "/a/e/r") ("/usr/include", testFS2) `shouldBe` ("/a/e/r", testFS2)

    describe "create-folder test" $ do
      it "test 1" $ execState (mkdir "newDir") ("", Dir "" []) `shouldBe` ("", Dir "" [Dir "newDir" []])
      it "test 2" $ execState (mkdir "d") ("/a/b/c", Dir "" [Dir "a" [Dir "b" [Dir "c" []]]])
                        `shouldBe` ("/a/b/c", Dir "" [Dir "a" [Dir "b" [Dir "c" [Dir "d" []]]]])

    describe "create-file test" $ do
      it "test 1" $ execState (touch "a") ("", Dir "" []) `shouldBe` ("", Dir "" [File "a" ""])
      it "test 2" $ execState (touch "test") ("/b/c", Dir "" [Dir "b" [Dir "c" []]])
                            `shouldBe` ("/b/c", Dir "" [Dir "b" [Dir "c" [File "test" ""]]])

    describe "write-file test " $ do
      it "test 1" $ execState (writeToFile "a" "content") ("", Dir "" [])
                              `shouldBe` ("", Dir "" [File "a" "content"])
      it "test 2" $ execState (writeToFile "f" "content") ("/a/b", Dir "" [Dir "a" [Dir "b" [File "f" ""]]])
                                    `shouldBe` ("/a/b", Dir "" [Dir "a" [Dir "b" [File "f" "content"]]])
      it "test 3" $ execState (writeToFile "w" "") ("", Dir "" []) `shouldBe` ("", Dir "" [File "w" ""])

    describe "remove path test" $ do
      it "test 1" $ execState (remove "a") ("", Dir "" [Dir "a" []]) `shouldBe` ("", Dir "" [])
      it "test 2" $ execState (remove "a") ("", Dir "" [File "a" ""]) `shouldBe` ("", Dir "" [])
      it "test 3" $ execState (remove "a") ("", testFS2) `shouldBe` ("", Dir "" [Dir "bin" [File "bash" "EXECUTE"],
                                                                                 Dir "usr" [Dir "include" [],
                                                                                            File "conf" "config"]])
    describe "find-file test" $ do
      it "test 1" $ evalState (searchFile "w") ("/a/e", testFS2) `shouldBe` Just "w"
      it "test 2" $ evalState (searchFile "w") ("/a", testFS2) `shouldBe` Just "w"
      it "test 3" $ evalState (searchFile "w") ("", testFS2) `shouldBe` Just "w"
      it "test 4" $ evalState (searchFile "r") ("", testFS2) `shouldBe` Just "r"
      it "test 5" $ evalState (searchFile "none") ("", testFS2) `shouldBe` Nothing
      it "test 6" $ evalState (searchFile "conf") ("", testFS2) `shouldBe` Just "conf"

    describe "combinating test" $ do
      it "test 1" $ runState (do
                      mkdir "test"
                      cd "test"
                      touch "file"
                      writeToFile "file" "someContent"
                      cat "file"
                      ) ("", Dir "" []) `shouldBe`
                      ("someContent", ("/test", Dir "" [Dir "test" [File "file" "someContent"]]))
      it "test 2" $ execState (do
                      mkdir "test"
                      cd "test"
                      touch "file1"
                      touch "file2"
                      touch "file3"
                      touch "file4"
                      touch "file5"
                      cd ".."
                      remove "test"
                      ) ("", Dir "" []) `shouldBe` ("", Dir "" [])
      it "test 3" $ execState (do
                              mkdir "a"
                              cd "a"
                              mkdir "b"
                              cd "b"
                              mkdir "c"
                              cd "c"
                              mkdir "d"
                              cd "d"
                              ) ("", Dir "" []) `shouldBe`
                              ("/a/b/c/d", Dir "" [Dir "a" [Dir "b" [Dir "c" [Dir "d" []]]]])

testFS1 :: FS
testFS1 = Dir "" [Dir "a" [File "b" "Content for file B",
                           Dir "e" [File "w" "NULL"]],
                  Dir "bin" [File "bash" "EXECUTE"]]

testFS2 :: FS
testFS2 = Dir "" [Dir "a" [File "b" "Content for file B",
                           File "c" "Content for file C",
                           Dir "e" [File "w" "NULL",
                                    Dir "r" []]],
                  Dir "bin" [File "bash" "EXECUTE"],
                  Dir "usr" [Dir "include" [],
                            File "conf" "config"]]

