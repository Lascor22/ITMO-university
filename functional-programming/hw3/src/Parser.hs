module Parser (
                parse,
                execCmd
                ) where

import Control.Monad.Reader (MonadIO (liftIO), ReaderT)
import Data.IORef (IORef)
import Data.List.Extra (sort, splitOn)
import Data.Maybe (fromMaybe)
import FSActions


data Command
    = CD FilePath
    | LS FilePath
    | MKDIR FilePath
    | TOUCH FilePath
    | WRITEFILE FilePath String
    | CAT FilePath
    | REMOVE FilePath
    | FIND String
    | INFO FilePath
    | DIR
    | HELP
    deriving (Eq, Show)

tokinizeInput :: String -> [String]
tokinizeInput cmd = filter (not . null) $ splitOn " " cmd

parse :: String -> Maybe Command
parse = createCmd . tokinizeInput where
    createCmd :: [String] -> Maybe Command
    createCmd ("cd":path:_)            = Just $ CD path
    createCmd ("ls":path:_)            = Just $ LS path
    createCmd ("create-folder":path:_) = Just $ MKDIR path
    createCmd ("create-file":path:_)   = Just $ TOUCH path
    createCmd ("write-file":path:xs)   = Just $ WRITEFILE path (unwords xs)
    createCmd ("cat":path:_)           = Just $ CAT path
    createCmd ("remove":path:_)        = Just $ REMOVE path
    createCmd ("find-file":path:_)     = Just $ FIND path
    createCmd ("information":path:_)   = Just $ INFO path
    createCmd ("dir":_)                = Just DIR
    createCmd ("help":_)               = Just HELP
    createCmd _                        = Nothing

putFolders :: [FilePath] -> IO ()
putFolders dirs = do
    putStrLn $ unlines $ sort dirs


execCmd :: Command -> ReaderT (IORef FilePath) IO ()
execCmd (CD path)       = cd path
execCmd (LS path)       =  do {dirs <- ls path; liftIO $ putFolders dirs}
execCmd (MKDIR dirName) = mkdir dirName
execCmd (TOUCH fileName) = touch fileName
execCmd (WRITEFILE path content) = writeToFile path content
execCmd (CAT path)      = do {content <- cat path; liftIO $ putStrLn content }
execCmd (REMOVE path)   = remove path
execCmd (FIND file)     = do { result <- searchFile file; liftIO $ putStrLn $ fromMaybe "File not found" result }
execCmd (INFO path)     = do { info <- information path;  liftIO $ putStrLn info }
execCmd DIR             = do {dirs <- dir; liftIO $ putFolders dirs}
execCmd HELP            = do { content <- help; liftIO $ putStrLn content }

