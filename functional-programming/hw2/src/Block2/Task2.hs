module Block2.Task2
                (
                    moving
                ) where

import Control.Monad.State (MonadState (get, put), State, evalState)

moving :: (Fractional a) =>  Int -> [a] -> [a]
moving w dots = evalState (reduceState dots) (0, 0, dots) where
    reduceState :: Fractional a => [a] -> State (Int, a, [a]) [a]
    reduceState (x:xs) = do
        (i, s, y) <- get
        let (s', y', w') = if w <= i
                            then (s + x - head y, tail y, w)
                            else (s + x, y, i + 1)
        put (i + 1, s', y')
        t <- reduceState xs
        return $ (s' / fromIntegral w'):t
    reduceState [] = return []
