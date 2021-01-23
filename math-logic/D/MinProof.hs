module MinProof where
import Lexer (alexScanTokens)	
import Syntax 

import Data.List
import qualified Data.Map.Strict as Map
import qualified Data.Set as Set

-- read expression, parse and show
parseAndShow :: String -> String
parseAndShow = (show . synt . alexScanTokens)

-- parse expression to my type Exp
parse :: String -> Exp
parse = normalize . synt . alexScanTokens 

-- parse expression and convert to cortege of list Exp and Exp
parseProof :: String -> ([Exp], Exp)
parseProof = fromSyntToExp . synt . alexScanTokens where
    fromSyntToExp (Prf ls x) = ((map (normalize . Expr) ls), (normalize . Expr) x)
    fromSyntToExp _          = error "Illegal arguments"

-- check if expression is a axiom
isAxi :: Exp -> Maybe [Int]
isAxi (Impl a (Impl b a1)) | a == a1 = Just [1, 1]
isAxi (Impl (Impl a1 b1) (Impl (Impl a2 (Impl b2 g1)) (Impl a3 g2))) | a1 == a2 && a1 == a3 && b1 == b2 && g1 == g2 = Just [1, 2]
isAxi (Impl a1 (Impl b1 (Con a2 b2))) | a1 == a2 && b1 == b2 = Just [1, 3]
isAxi (Impl (Con a1 b) a2) | a1 == a2 = Just [1, 4]
isAxi (Impl (Con a1 b1) b2) | b1 == b2 = Just [1, 5]
isAxi (Impl a1 (Dis a2 b)) | a1 == a2 = Just [1, 6]
isAxi (Impl b1 (Dis a b2)) | b1 == b2 = Just [1, 7]
isAxi (Impl (Impl a1 g1) (Impl (Impl b1 g2) (Impl (Dis a2 b2) g3))) | a1 == a2 && b1 == b2 && g1 == g2 && g1 == g3 = Just [1, 8]
isAxi (Impl (Impl a1 b1) (Impl (Impl a2 (Not b2)) (Not a3))) | a1 == a2 && a1 == a3 && b1 == b2 = Just [1, 9]
isAxi (Impl (Not (Not a1)) a2) | a1 == a2 = Just [1, 10]
isAxi _ = Nothing

-- check if expression is a hypothesis
isHyp :: Map.Map Exp Int -> Exp -> Maybe [Int]
isHyp hypMap expr | Map.member expr hypMap = Just [2, (hypMap Map.! expr)]
				  | otherwise           = Nothing


-- check if expression is a Modus Ponens
isMP :: Map.Map Exp Int -> Map.Map Exp [(Exp, Int)] -> Exp -> Maybe [Int]
isMP beginMap implMap expr = if (Map.member expr implMap) then
							     case (myFind (implMap Map.! expr) beginMap) of
							     	Just (x, n) -> Just [3, n, (beginMap Map.! x)]
							     	Nothing     -> Nothing
    	  					 else 
    	  					     Nothing


checkProof :: Map.Map Exp Int -> [Exp] -> [(Exp, Maybe [Int])] 
checkProof hypMap proof = private 1 Map.empty Map.empty proof where
	private n implMap beginMap (expr:es) = if (checkCurr /= Nothing) then 															
										       if not (Map.member expr beginMap) then 
				   							       (expr,checkCurr):((private $! (n + 1)) (addImpl n expr implMap) (Map.insert expr n beginMap) es)
											   else
												   private n implMap beginMap es
				                           else 
				                                   undefined where 
		checkList = ([(isHyp hypMap expr), (isAxi expr), (isMP beginMap implMap expr)])
		checkCurr = if (any (/=Nothing) checkList) then 
						(head (filter (/=Nothing) checkList)) 
					else 
						Nothing
	private n _ _ [] = []

-- utilites

addImpl n (Impl a b) mapa | Map.member b mapa = Map.insert b ((a, n):(mapa Map.! b)) mapa
						  | otherwise         = Map.insert b [(a, n)] mapa
addImpl n _          mapa = mapa

myFind :: [(Exp, Int)] -> Map.Map Exp Int -> Maybe (Exp, Int)
myFind ((x, n):xs) beginMap | Map.member x beginMap = Just (x, n)
						    | otherwise             = myFind xs beginMap
myFind [] _ 										= Nothing

getResInd n res (p:ps) | p == res  = n
					   | otherwise = (getResInd $! (n + 1)) res ps 
getResInd n res [] = -1

getIndexOfMap :: (Ord a) => [a] -> Map.Map a Int
getIndexOfMap proof = private 1 proof Map.empty where
	private n (e:es) mapa = (private $! (n + 1)) es (Map.insert e n mapa)
	private n []     mapa = mapa
