module Main where
import Test.Tasty (defaultMain, testGroup)

import qualified CHTTests (test)
import qualified FileSystemTests (test)

main :: IO ()
main = do
  hashTableTest <- CHTTests.test
  fsTest <- FileSystemTests.test
  defaultMain $ testGroup "Tests" [
                          testGroup "Concurrent hashtable" [hashTableTest],
                          testGroup "Filesystem" [fsTest]
                                  ]

