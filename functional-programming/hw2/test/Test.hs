module Main where
import Test.Tasty (defaultMain, testGroup)

import qualified Block1.Task1Test (test)
import qualified Block2.Task1Test (test)
import qualified Block2.Task2Test (test)
import qualified Block3.Task2Test (test)
import qualified Block3.Task3Test (test)

main :: IO ()
main = do
  testBlock1Task1 <- Block1.Task1Test.test
  testBlock2Task1 <- Block2.Task1Test.test
  testBlock2Task2 <- Block2.Task2Test.test
  testBlock3Task2 <- Block3.Task2Test.test
  testBlock3Task3 <- Block3.Task3Test.test
  defaultMain $ testGroup "Tests" [
                          testGroup "Block1" [testBlock1Task1],
                          testGroup "Block2" [testBlock2Task1,
                                              testBlock2Task2
                                             ],
                          testGroup "Block3" [testBlock3Task2,
                                              testBlock3Task3
                                            ]
                                  ]
