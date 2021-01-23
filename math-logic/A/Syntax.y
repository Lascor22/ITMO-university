{
module Syntax where
import Lexer
}

%name synt
%tokentype { Token }
%error { parseError }

%token
  "->"        { TImpl }
  var         { TVar $$ }
  '&'         { TSym '&' }
  '|'         { TSym '|' }
  '!'         { TSym '!' }
  '('         { TSym '(' }
  ')'         { TSym ')' }

%%
Exp:
  Term "->" Exp                 { Impl $1 $3 }
  | Term                        { Term $1 }

Term:
  Term '|' Clos                 { Dis $1 $3 }
  | Clos                        { Clos $1 }

Clos:
  Clos '&' Inv                 { Con $1 $3 }
  | Inv                        { Inv $1 }

Inv: 
  '!' Inv                      { Neg $2 }
  | Factor                     { Factor $1 }

Factor:
  var                           { Var $1 }
  | '(' Exp ')'                 { Brack $2 }

{
parseError :: [Token] -> a
parseError _ = error "Parse error"

data Exp    = Impl Term Exp | Term Term 
data Term   = Dis Term Clos | Clos Clos
data Clos   = Con Clos Inv  | Inv Inv
data Inv    = Neg Inv       | Factor Factor 
data Factor = Var String    | Brack Exp

instance Show Factor where
  show (Var a) = a
  show (Brack a) = show a

instance Show Inv where
  show (Neg a) = "(!" ++ (show a) ++ ")"
  show (Factor a) = show a

instance Show Term where
  show (Dis a b) = "(|," ++ (show a) ++ "," ++ (show b) ++ ")"
  show (Clos a)   = show a

instance Show Clos where
  show (Con a b) = "(&," ++ (show a) ++ "," ++ (show b) ++ ")"
  show (Inv a)   = show a

instance Show Exp where
  show (Impl a b) = "(->," ++ (show a) ++ "," ++ (show b) ++ ")"
  show (Term a)   = show a
}