module Integral (
                sampleFunction
              , integrateMonteCarloPar
              , integrateMonteCarloSeq
                ) where

import Control.Monad.Par (InclusiveRange (InclusiveRange), parMapReduceRangeThresh, runPar)
import Data.Vector ((!))
import qualified Data.Vector as V hiding (foldr)
import System.Random (Random (randomRs), StdGen, getStdGen)

sampleFunction :: Double -> Double
sampleFunction x = foldr f 0.0 [1..1000] where
    f acc i = acc + tan (exp ((-x) ** i)) ** tan (exp ((-x) ** (i + 1)))

integrateMonteCarloPar :: (Double -> Double) -> Double ->  Double -> Int -> IO Double
integrateMonteCarloPar f l r n = do
    g <- getStdGen
    let points = V.fromList (getPoints n l r g)
    let s = runPar $ parMapReduceRangeThresh 50 (InclusiveRange 0 (n - 1))
                                                 (\x -> return (f $ points ! x))
                                                 (\x y -> return $ x + y) 0
    return $ (l - r) / fromIntegral n * s

integrateMonteCarloSeq :: (Double -> Double) -> Double -> Double -> Int -> IO Double
integrateMonteCarloSeq f l r n = do
    g <- getStdGen
    let points = getPoints n l r g
    let s = foldr (\x y -> x + f y) 0 points
    return $ (l - r) / fromIntegral n * s

getPoints :: Int -> Double -> Double -> StdGen -> [Double]
getPoints n l r g = take n (randomRs (l, r) g)
