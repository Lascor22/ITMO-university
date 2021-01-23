module Main where
import Syntax 
import MinProof (parse)
import Deduction (deduction)
import Proofs 

import Data.List
import qualified Data.Map.Strict as Map
import qualified Data.Set as Set
import qualified Data.Maybe as Maybe

eval :: Exp -> Map.Map Exp Bool -> Bool
eval (Impl a b) vals = (not (eval a vals)) || (eval b vals)
eval (Dis a b) vals = (eval a vals) || (eval b vals) 
eval (Con a b) vals = (eval a vals) && (eval b vals)
eval (Not a) vals = not (eval a vals)
eval v@(Var _) vals | Map.member v vals = vals Map.! v
                    | otherwise = True

getVars :: Exp -> [Exp]
getVars expr = Set.toList (getVarsHepler expr Set.empty) where
    getVarsHepler :: Exp -> Set.Set Exp -> Set.Set Exp
    getVarsHepler (Impl a b) set = Set.union (getVarsHepler b set) (getVarsHepler a set)
    getVarsHepler (Con a b) set = Set.union (getVarsHepler b set) (getVarsHepler a set)
    getVarsHepler (Dis a b) set = Set.union (getVarsHepler b set) (getVarsHepler a set)
    getVarsHepler (Not a) set = getVarsHepler a set
    getVarsHepler v@(Var _) set = Set.insert v set

table :: Exp -> [Map.Map Exp Bool]
table expr = helper (length vars) where
    vars = getVars expr
    helper :: Int -> [Map.Map Exp Bool]
    helper 1 = let a = vars !! 0 in [Map.fromList [(a,False)],
                                     Map.fromList [(a,True)]]
    helper 2 = ans where
        a = vars !! 0
        b = vars !! 1
        ans = [Map.fromList [(a,False), (b,False)], 
               Map.fromList [(a,False), (b,True)], 
               Map.fromList [(a,True), (b,False)], 
               Map.fromList [(a,True), (b,True)]]
    helper 3 = ans where
        a = vars !! 0
        b = vars !! 1 
        c = vars !! 2
        ans = [Map.fromList [(a,False), (b,False), (c, False)], 
               Map.fromList [(a,False), (b,False), (c, True)], 
               Map.fromList [(a,False), (b,True), (c, False)], 
               Map.fromList [(a,False), (b,True), (c, True)],
               Map.fromList [(a,True), (b,False), (c, False)], 
               Map.fromList [(a,True), (b,False), (c, True)], 
               Map.fromList [(a,True), (b,True), (c, False)], 
               Map.fromList [(a,True), (b,True), (c, True)]]

findHyps :: Exp -> Maybe ([Exp], [Int])
findHyps expr = helper (length vars) where 
    vars = getVars expr
    t = table expr
    vals = map (eval expr) t
    helper :: Int -> Maybe ([Exp], [Int])
    helper 1 | all (==True) vals      = Just ([], [0, 1])
             | vals !! 1              = Just ([vars !! 0], [1])
             | all (==False) vals     = Just ([], [0, 1])
             | not (vals !! 0)        = Just ([Not (vars !! 0)], [0])
             | otherwise              = Nothing
    
    helper 2 | all (==True) vals                       = Just ([], [0..3])
             | (vals !! 2) && (vals !! 3)              = Just ([vars !! 0], [2, 3])
             | (vals !! 1) && (vals !! 3)              = Just ([vars !! 1], [1, 3])
             | (vals !! 3)                             = Just (vars, [3])
             | all (==False) vals                      = Just ([], [0..3])
             | (not(vals !! 0)) && (not (vals !! 1))   = Just ([Not (vars !! 0)], [0, 1])
             | (not(vals !! 0)) && (not (vals !! 2))   = Just ([Not (vars !! 1)], [0, 2])
             | (not(vals !! 0))                        = Just ([Not (vars !! 0), Not (vars !! 1)], [0])
             | otherwise                               = Nothing

    helper 3 | all (==True) vals                                                                = Just ([], [0..7])
             | (vals !! 4) && (vals !! 5) && (vals !! 6) && (vals !! 7)                         = Just ([vars !! 0], [4..7])
             | (vals !! 2) && (vals !! 3) && (vals !! 6) && (vals !! 7)                         = Just ([vars !! 1], [2,3,6,7])
             | (vals !! 1) && (vals !! 3) && (vals !! 5) && (vals !! 7)                         = Just ([vars !! 2], [1,3,5,7])
             | (vals !! 6) && (vals !! 7)                                                       = Just ([vars !! 0, vars !! 1], [6,7])
             | (vals !! 5) && (vals !! 7)                                                       = Just ([vars !! 0, vars !! 2], [5,7])
             | (vals !! 3) && (vals !! 7)                                                       = Just ([vars !! 1, vars !! 2], [3,7])
             | vals !! 7                                                                        = Just (vars, [7])
             | all (==False) vals                                                               = Just ([], [0..7])
             | (not (vals !! 0)) && (not (vals !! 1)) && (not (vals !! 2)) && (not (vals !! 3)) = Just ([Not (vars !! 0)], [0,1,2,3])
             | (not (vals !! 0)) && (not (vals !! 1)) && (not (vals !! 4)) && (not (vals !! 5)) = Just ([Not (vars !! 1)], [0,1,4,5])
             | (not (vals !! 0)) && (not (vals !! 2)) && (not (vals !! 4)) && (not (vals !! 6)) = Just ([Not (vars !! 2)], [0,2,4,6])
             | not (vals !! 0) && not (vals !! 1)                                               = Just ([Not (vars !! 0), Not (vars !! 1)], [0,1])
             | not (vals !! 0) && not (vals !! 2)                                               = Just ([Not (vars !! 0), Not (vars !! 2)], [0,2])
             | not (vals !! 0) && not (vals !! 4)                                               = Just ([Not (vars !! 1), Not (vars !! 2)], [0,4])
             | not (vals !! 0)                                                                  = Just ((map Not vars), [0])
             | otherwise                                                                        = Nothing


getProofs :: Exp -> Maybe ([Exp], [(Map.Map Exp Bool, [Exp])])
getProofs expr = if Maybe.isNothing h then Nothing else ans where 
    t = table expr
    h = findHyps expr
    hyps = fst (Maybe.fromJust h)
    proofTable = map (\n -> t !! n) (snd (Maybe.fromJust h))
    ans = Just (hyps, (map (\m -> (m, proof2 expr m)) proofTable))

proof2 expr m = proof expr newM where
    getExps e@(Impl a b) s = Set.insert e (Set.union (getExps a s) (getExps b s))
    getExps e@(Dis a b) s = Set.insert e (Set.union (getExps a s) (getExps b s))
    getExps e@(Con a b) s = Set.insert e (Set.union (getExps a s) (getExps b s))
    getExps e@(Not a) s = Set.insert e (getExps a s)
    getExps e@(Var a) s = Set.insert e s
    exps = Set.toList $ getExps expr Set.empty
    newM = Map.fromList $ map (\e ->  (e, eval e m)) exps


proof :: Exp -> Map.Map Exp Bool -> [Exp]
proof (Impl a b) mapa = (proof a mapa) ++ (proof b mapa) ++ (getProof ImplP a b (mapa Map.! a) (mapa Map.! b))
proof (Dis a b) mapa = (proof a mapa) ++ (proof b mapa) ++ (getProof DisP a b (mapa Map.! a) (mapa Map.! b))
proof (Con a b) mapa = (proof a mapa) ++ (proof b mapa) ++ (getProof ConP a b (mapa Map.! a) (mapa Map.! b))
proof (Not a) mapa = proof a mapa ++ getProof NotP a a (mapa Map.! a) True 
proof a@(Var _) mapa | mapa Map.! a = [a]
                     | otherwise    = [Not a]

deleteHyps :: Exp -> [(Map.Map Exp Bool, [Exp])] -> [Exp] -> [(Map.Map Exp Bool, [Exp])]
deleteHyps expr proofs hyps = if (null vars) then proofs else ans where
    checkVar :: Exp -> Bool
    checkVar v = any (\(m, _) -> Map.member v m) proofs

    invertVal :: Exp -> Map.Map Exp Bool -> Map.Map Exp Bool
    invertVal v m = Map.insert v (not (m Map.! v)) m

    findPair :: [(Map.Map Exp Bool, [Exp])] -> ((Map.Map Exp Bool, [Exp]), (Map.Map Exp Bool, [Exp]))
    findPair ((m, p):ls) | Map.member (invertVal var m) mapProofs = ((m, p), ((invertVal var m), mapProofs Map.! (invertVal var m)))  
                         | otherwise = findPair ls

    vars = filter (\v -> checkVar v && not (elem v hyps) && not (elem (Not v) hyps)) $ getVars expr
    var  = vars !! 0
    mapProofs = Map.fromList proofs
    forVarProofs = filter (\(m, _) -> Map.member var m) proofs
    ((m1, p1), (m2,p2)) = findPair forVarProofs
    firstDed = deduction p1 (getHyps m1) (mapperHyp (var, m1 Map.! var))
    secondDed = deduction p2 (getHyps m2) (mapperHyp (var, m2 Map.! var))
    currExpr = mapperHyp (expr, eval expr m1)
    ans = Map.toList (Map.insert (Map.delete var m1) (firstDed ++ secondDed ++ getRemove var currExpr) (Map.delete m2 (Map.delete m1 mapProofs)))

solve :: String -> [String]
solve str = if Maybe.isJust proofsM then ans else [":("] where 
    expr = parse str
    proofsM = getProofs expr
    (hyps, proofs) = Maybe.fromJust proofsM
    
    helper :: [(Map.Map Exp Bool, [Exp])] -> (Map.Map Exp Bool, [Exp])
    helper ps | length ps == 1 = (ps !! 0)
              | otherwise = helper (deleteHyps expr ps hyps)
    
    (m, resProof) = helper proofs
    finalExpr = mapperHyp (expr, eval expr m)
    first = myShow hyps ++ "|- " ++ show finalExpr
    other = map show resProof
    ans = filter (not . null) (first:other)

myShow :: [Exp] -> String
myShow (h:hs) = show h ++ helper hs where
    helper (x:xs) = ", " ++ show x ++ helper xs
    helper [] = ""
myShow [] = ""

mapperHyp :: (Exp, Bool) -> Exp
mapperHyp (v, True) = v
mapperHyp (v, False) = Not v

getHyps :: Map.Map Exp Bool -> [Exp]
getHyps = (map mapperHyp) . Map.toList
    
main :: IO ()
main = interact (unlines . solve)