module Comonad19 (
                CovidType(..),
                CovidCell(..),
                SimulationParameters(..),
                initSimulation,
                showSimulationResult,
                stepSimulation,
                nStepSimulation
                ) where

import Control.Comonad (Comonad (..))
import Control.Monad (liftM2)
import Data.List (intercalate)
import Grid
import System.Random (StdGen, mkStdGen, randomR, split)

data CovidType
    = Normal
    | Incubation
    | Ill
    | Recovered

instance Show CovidType where
    show Normal     = " "
    show Incubation = "#"
    show Ill        = "#"
    show Recovered  = "@"

data CovidCell = CovidCell
    { rand   :: StdGen
    , status :: CovidType
    , days   :: Int
    }

data SimulationParameters = Params
    { prob         :: Double
    , incubDays    :: Int
    , illDays      :: Int
    , immunityDays :: Int
    , size         :: Int
    }

instance Show CovidCell where
    show = show . status

initSimulation :: Grid CovidCell
initSimulation = setFirstIll $ Grid $ mkZipper leftList rightList element where
    leftList = (fmap rightSplit . listLeft) . listRight
    rightList = (fmap leftSplit . listRight) . listLeft
    element = mkZipper leftSplit rightSplit defaultCell
    leftSplit :: CovidCell -> CovidCell
    leftSplit  c@CovidCell {rand = r} = c {rand = fst $ split r}

    rightSplit :: CovidCell -> CovidCell
    rightSplit c@CovidCell {rand = r} = c {rand = snd $ split r}

    setFirstIll :: Grid CovidCell -> Grid CovidCell
    setFirstIll g = gridWrite (gridRead g){status = Incubation} g


showSimulationResult :: SimulationParameters -> Grid CovidCell -> String
showSimulationResult param g = "|" <> intercalate "|"
                (intercalate ["\n"] (map (`toList` size param) (toList zipper (size param)))) <> "|"
            where
                (Grid zipper) = fmap show g


nStepSimulation :: Int -> SimulationParameters -> Grid CovidCell -> Grid CovidCell
nStepSimulation n param = repeatN n (stepSimulation param) where
    repeatN :: Int -> (a -> a) -> a -> a
    repeatN 0 _ res = res
    repeatN a f res = repeatN (a - 1) f (f res)

stepSimulation :: SimulationParameters -> Grid CovidCell -> Grid CovidCell
stepSimulation param = extend infect where
    infect ::Grid CovidCell -> CovidCell
    infect g = let cell = extract g in
        case status cell of
            Normal -> probabilityIll param g
            Incubation -> if days cell == incubDays param
                        then
                            cell {days = 0, status = Ill}
                        else
                            addDay cell
            Ill -> if days cell == illDays param
                then
                    cell {days = 0, status = Recovered}
                else
                    addDay cell
            Recovered -> if days cell == immunityDays param
                        then
                            cell {days = 0, status = Normal}
                        else
                            addDay cell


defaultCell :: CovidCell
defaultCell = CovidCell
    {
        rand = mkStdGen 1,
        status = Normal,
        days = 0
    }

addDay :: CovidCell -> CovidCell
addDay c@CovidCell {days = d} = c {days = d + 1}

probabilityIll :: SimulationParameters -> Grid CovidCell -> CovidCell
probabilityIll params g = if p > r then
        CovidCell {days = 0, status = Incubation, rand = n}
    else
        CovidCell {days = 0, status = Normal, rand = n}
    where
        p = 1 - (1 - prob params) ^ countIll
        (r, n) = randomR (0 :: Double, 1 :: Double) (rand $ extract g)
        countIll = (length . filter isIll) (fmap (\direction -> extract $ direction g) neighbours)

        isIll :: CovidCell -> Bool
        isIll CovidCell {status = Incubation} = True
        isIll CovidCell {status = Ill}        = True
        isIll _                               = False

        neighbours :: [Grid a -> Grid a]
        neighbours = [left, right] ++ [up, down] ++ liftM2 (.) [left, right] [up, down]

