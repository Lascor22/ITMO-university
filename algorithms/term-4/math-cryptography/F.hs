module Main where

main = do
	a <- getLine
	b <- getLine
	putStrLn $ show ((read a :: Integer) * (read b :: Integer))