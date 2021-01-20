module IntegralBenchmark (
                          integralBenchmark
                         ) where

import Criterion.Main (bench, bgroup, defaultMain, nfIO)

import Integral (integrateMonteCarloPar, integrateMonteCarloSeq, sampleFunction)

integralBenchmark :: IO ()
integralBenchmark = defaultMain [
    bgroup "integral" [
      bench "parallel" $ nfIO $ integrateMonteCarloPar sampleFunction (-2.0) 2.0 1000,
      bench "sequential" $ nfIO $ integrateMonteCarloSeq sampleFunction (-2.0) 2.0 1000
    ]]
