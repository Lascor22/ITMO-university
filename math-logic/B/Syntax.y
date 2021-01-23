{
module Syntax where
import Lexer
}

%name synt
%tokentype { Token }
%error { parseError }

%token
  "->"        { TImpl }
  "|-"        { TTur }
  var         { TVar $$ }
  '&'         { TSym '&' }
  '|'         { TSym '|' }
  '!'         { TSym '!' }
  '('         { TSym '(' }
  ')'         { TSym ')' }
  ','         { TSym ',' }

%right "->"
%left '&' '|'

%%

Base: 
  LE "|-" Expr                  { Prf $1 $3 }
  | "|-" Expr                   { Prf [] $2 }
  | Expr                        { Expr $1 }

LE:
  Expr ',' LE                   { ($1:$3) }
  | Expr                        { [$1] }
Expr:
  Tm "->" Expr                  { Iml $1 $3 }
  | Tm                          { Tm $1 }

Tm:
  Tm '|' Cl                     { Ds $1 $3 }
  | Cl                          { Cl $1 }

Cl:
  Cl '&' Ng                     { Cn $1 $3 }
  | Ng                          { Ng $1 }

Ng: 
  '!' Ng                        { Neg $2 }
  | Fc                          { Fc $1 }

Fc:
  var                           { Vr $1 }
  | '(' Expr ')'                { Br $2 }

{
parseError :: [Token] -> a
parseError _ = error "Parse error"

data Base = Prf [Expr] Expr | Expr Expr 
data Expr = Iml Tm Expr | Tm Tm  
data Tm   = Ds Tm Cl | Cl Cl 
data Cl   = Cn Cl Ng  | Ng Ng 
data Ng   = Neg Ng       | Fc Fc 
data Fc   = Vr String    | Br Expr 

instance Show Fc where
  show (Vr a) = a
  show (Br a) = show a

instance Show Ng where
  show (Neg a) = "!" ++ (show a)
  show (Fc a) = show a

instance Show Tm where
  show (Ds a b) = "(" ++ (show a) ++ " | " ++ (show b) ++ ")"
  show (Cl a)   = show a

instance Show Cl where
  show (Cn a b) = "(" ++ (show a) ++ " & " ++ (show b) ++ ")"
  show (Ng a)   = show a

instance Show Expr where
  show (Iml a b) = "(" ++ (show a) ++ " -> " ++ (show b) ++ ")"
  show (Tm a)   = show a

instance Show Base where
  show (Prf a b) = (g (map show a)) ++ "|- " ++ (show b) where
    g [] = []
    g xs = f xs
    f = foldr (\a b -> a ++ (if (null b) then " " else ", ") ++ b) []
  show (Expr a)   = show a

data Exp = Impl Exp Exp | Var String | Con Exp Exp | Dis Exp Exp | Not Exp deriving (Eq, Ord)

instance Show Exp where
  show (Impl a b) = "(" ++ (show a) ++ " -> " ++ (show b) ++ ")"
  show (Con a b) = "(" ++ (show a) ++ " & " ++ (show b) ++ ")"
  show (Dis a b) = "(" ++ (show a) ++ " | " ++ (show b) ++ ")"
  show (Not a) = "!" ++ (show a)
  show (Var a) = a
  

-- convert to my type Exp
normalize :: Base -> Exp
normalize (Expr expr) = nl0 expr where
      nl0 (Iml a b) = Impl (nl1 a) (nl0 b)
      nl0 (Tm a) = nl1 a

      nl1 (Ds a b) = Dis (nl1 a) (nl2 b)
      nl1 (Cl a) = nl2 a

      nl2 (Cn a b) = Con (nl2 a) (nl3 b)
      nl2 (Ng a) = nl3 a

      nl3 (Neg a) = Not (nl3 a)
      nl3 (Fc a) = nl4 a

      nl4 (Vr a) = Var a
      nl4 (Br a) = nl0 a

}