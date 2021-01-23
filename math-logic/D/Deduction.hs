module Deduction where
import Syntax 
import MinProof (checkProof, getIndexOfMap)

import Data.List
import qualified Data.Map.Strict as Map
import qualified Data.Set as Set

deduction :: [Exp] -> [Exp] -> Exp -> [Exp]
deduction prf hs e = helperDed newProof check e newProof where
	hypMap = getIndexOfMap hs
	(newProof, check) = unzip $ checkProof hypMap prf
	helperDed (p:ps) (Nothing:cs) e fnd = error (show e ++ " " ++ show hs)
	helperDed (p:ps) ((Just (1:_)):cs) e fnd = p:(Impl p (Impl e p)):(Impl e p):(helperDed ps cs e fnd)
	helperDed (p:ps) ((Just (2:_)):cs) e fnd | e == p = (Impl e (Impl e e)):
								 	 				(Impl e (Impl (Impl e e) e)):
								 	 				(Impl (Impl e (Impl e e)) (Impl (Impl e (Impl (Impl e e) e)) (Impl e e))):
								 	 				(Impl (Impl e (Impl (Impl e e) e)) (Impl e e)):
								 	 				(Impl e e):
								 	 				(helperDed ps cs e fnd)
										    | otherwise = p:(Impl p (Impl e p)):(Impl e p):(helperDed ps cs e fnd)
	helperDed (p:ps) ((Just (3:a:b:[])):cs) e fnd = (Impl (Impl e j) (Impl (Impl e (Impl j p)) (Impl e p))):
													(Impl (Impl e (Impl j p)) (Impl e p)):
													(Impl e p):
													(helperDed ps cs e fnd) where				
														j = fnd !! (b - 1)
	helperDed [] [] _ _ = []