module Main where

import           Comonad19


main :: IO ()
main = do
    let start = initSimulation
    let end = nStepSimulation 50 param start
    putStrLn $ showSimulationResult param end

param :: SimulationParameters
param = Params {
        prob = 0.2,
        incubDays = 3,
        illDays = 4,
        immunityDays = 7,
        size = 15
    }