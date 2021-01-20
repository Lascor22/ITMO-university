module Main where

import GeometryBenchmark (geometryBenchmark)
import IntegralBenchmark (integralBenchmark)

main :: IO ()
main = do
    geometryBenchmark
    integralBenchmark
