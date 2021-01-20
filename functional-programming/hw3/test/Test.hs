module Main where
import Test.Tasty (defaultMain, testGroup)

import FSTests (test)

main :: IO ()
main = do
  tests <- test
  defaultMain $ testGroup "Tests" [testGroup "FileManagerTests" [tests]]
