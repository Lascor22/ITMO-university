module Block2.Task1
                (
                    Expr(..),
                    eval,
                    ArithmeticError(..)
                ) where

import Data.Either (fromRight)

data Expr
    = Pow Expr Expr
    | Div Expr Expr
    | Mul Expr Expr
    | Sub Expr Expr
    | Sum Expr Expr
    | Const Int
    deriving Show

data ArithmeticError
    = DivisionByZero
    | NegativePower
    deriving (Show, Eq)

eval :: Expr -> Either ArithmeticError Int
eval (Mul l r) = (*) <$> eval l <*> eval r
eval (Sub l r) = (-) <$> eval l <*> eval r
eval (Sum l r) = (+) <$> eval l <*> eval r
eval (Const x) = Right x
eval (Div l r) = res where
    b = fromRight 0 $ eval r
    res = if b == 0
        then Left DivisionByZero
        else div <$> eval l <*> pure b
eval (Pow l r) = res where
    b = fromRight (-1) $ eval r
    res = if b < 0
        then Left NegativePower
        else (^) <$> eval l <*> pure b
