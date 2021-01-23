module Main where
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
				                                   [((Var "A"), Nothing)] where 
		checkList = ([(isHyp hypMap expr), (isAxi expr), (isMP beginMap implMap expr)])
		checkCurr = if (any (/=Nothing) checkList) then 
						(head (filter (/=Nothing) checkList)) 
					else 
						Nothing
	private n _ _ [] = []

minimize :: Map.Map Exp Int -> [Exp] -> Exp -> [Maybe [Int]] -> [String]
minimize hypMap proof res checks = myFilter 1 1 proof checks Map.empty where
	getIndMap n ((Just (1:a:[])):es) mapa = (getIndMap $! (n + 1)) es (Map.insert n [] mapa)
	getIndMap n ((Just (2:a:[])):es) mapa = (getIndMap $! (n + 1)) es (Map.insert n [] mapa)
	getIndMap n ((Just (3:a:b:[])):es) mapa = (getIndMap $! (n + 1)) es (Map.insert n [a,b] mapa)
	getIndMap n [] mapa = ((n - 1), mapa)
	(lastInd, indMap) = getIndMap 1 checks Map.empty
	getAt n = indMap Map.! n
	dfs (f:s:ls) set = (dfs (getAt s)) $ ((dfs (getAt f)) $ (Set.insert f (Set.insert s set)))
	dfs [] set = set
	index = (Set.insert lastInd $! (dfs (getAt lastInd) Set.empty))
	-- toStr p (Just (1:a:[])) n mapa = (show p)
	-- toStr p (Just (2:a:[])) n mapa = (show p)
	-- toStr p (Just (3:a:b:ls)) n mapa = (show p)
	toStr p (Just (1:a:[])) n mapa = ("[" ++ (show n) ++ ". Ax. sch. " ++ (show a) ++ "] " ++ (show p))
	toStr p (Just (2:a:[])) n mapa = ("[" ++ (show n) ++ ". Hypothesis " ++ (show a) ++ "] " ++ (show p))
	toStr p (Just (3:a:b:ls)) n mapa = ("[" ++ (show n) ++ ". M.P. " ++ (show (mapa Map.! a)) ++ ", " ++ (show (mapa Map.! b)) ++ "] " ++ (show p))
	myFilter n newN (p:ps) (c:cs) mapa | Set.member n index = (toStr p c newN mapa):(((myFilter $! (n + 1)) $! (newN + 1)) ps cs (Map.insert n newN mapa))
	                                   | otherwise = (myFilter $! (n + 1)) newN ps cs mapa
	myFilter _ _ [] [] _ = []

-- solve :: [String] -> [String]--(if (last proof) == res then "1" else "0") ++ " " ++ findIncorrect 1 newProof check ++ "\n" ++ unlines (map show newProof)
-- solve (expr:prf) = if isCorrect then ((parseAndShow expr):(minimize hypMap (take resInd newProof) res (take resInd check))) else [(if (last proof) == res then "1" else "0") ++ " " ++ findIncorrect 1 newProof check ++ "\n" ++ unlines (map show newProof)] where
solve (expr:prf) = if isCorrect then ["is correct"] else ["Proof is incorrect"] where
	(hyps, res) = parseProof expr
	proof = map parse prf
	hypMap = getIndexOfMap hyps
	(newProof, check) = unzip $ checkProof hypMap proof
	isCorrect = (all (/= Nothing) check) && ((last proof) == res)
	resInd = getResInd 1 res newProof

findIncorrect n (p:ps) (Nothing:cs) = show n 
findIncorrect n (p:ps) (_:cs) = findIncorrect (n + 1) ps cs
findIncorrect _ [] [] = show (Var "H")

main :: IO ()
main = interact (unlines . solve . lines) 

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
