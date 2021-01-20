module Block1Task2
        (
          Nat(Z, S),
          toInt,
          isEven
        ) where

data Nat
    = Z
    | S Nat
    deriving Show


instance Eq Nat where
  (==) (S a) (S b) = (==) a b
  (==) Z     (S _) = False
  (==) (S _) Z     = False
  (==) Z     Z     = True

instance Ord Nat where
  compare (S a) (S b) = compare a b
  compare Z (S _)     = LT
  compare (S _) Z     = GT
  compare Z Z         = EQ

instance Num Nat where
  (+) a Z     = a
  (+) a (S b) = S a + b

  (*) _ Z     = Z
  (*) a (S b) = a + (a * b)

  (-)  a    Z     = a
  (-) (S a) (S b) =  a - b
  (-)  Z    _     = error "negative value"

  abs a = a

  signum Z = Z
  signum _ = S Z

  fromInteger 0 = Z
  fromInteger x | x > 0     = S (fromInteger (x - 1))
                | otherwise = error "negative value"

toInt :: Nat -> Integer
toInt (S a) = 1 + toInt a
toInt Z     = 0

isEven :: Nat -> Bool
isEven (S (S a)) = isEven a
isEven (S Z)     = False
isEven Z         = True

