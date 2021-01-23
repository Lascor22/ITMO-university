module Main where
import Lexer	
import Syntax

main = do
       s <- getLine
       putStrLn $ (show . synt . alexScanTokens) s