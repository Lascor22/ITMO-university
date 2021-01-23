{
module Lexer where
}

%wrapper "basic"

$digit = 0-9
$alpha = [a-zA-Z]

tokens :-

  $white                                       ;
  "->"									    { \s -> TImpl }
  "|-"										{ \s -> TTur }
  [\&\|\!\(\)\,]                           { \s -> TSym (head s)}
  $alpha [$alpha $digit \']*  				{ \s -> TVar s}

{
data Token = TImpl | TTur | TSym Char | TVar String deriving (Eq, Show)
}