module GeometryBenchmark (
                          geometryBenchmark
                         ) where

import Criterion.Main (bench, bgroup, defaultMain, nf)

import Geometry (Point (..), doubleArea, doubleAreaNaive, perimeter, perimeterNaive)

geometryBenchmark :: IO ()
geometryBenchmark = defaultMain [
    bgroup "perimeter" [
      bench "naive" $ nf perimeterNaive points,
      bench "fast" $ nf perimeter points
    ],
    bgroup "area" [
      bench "naive" $ nf doubleAreaNaive points,
      bench "fast" $ nf doubleArea points
    ]
  ] where
      points = fmap (\e -> Point e e) [1..((10::Int)^(7::Int))]
