module StateActions (
                    generalStateAction,
                    generalStateActionWithContent,
                    FS(..),
                    addDirectory,
                    addFile,
                    findFile,
                    getDirContent,
                    getFileContent,
                    getFullPathS,
                    removePath,
                    splitDirectories
                    ) where

import Control.Monad (msum)
import Control.Monad.State (MonadState (put), State, get)
import Data.List (find, intercalate)
import Data.List.Extra (splitOn)

data FS
    = File FilePath String
    | Dir FilePath [FS]
    deriving (Eq)

instance Show FS where
    show (File name _)    = name
    show (Dir name files) = "{" <> name <> ": [" <> intercalate ", " (map show files) <> "]}"

getName :: FS -> String
getName (File name _) = name
getName (Dir name _)  = name

isAbsolute :: FilePath -> Bool
isAbsolute ('/':_) = True
isAbsolute _       = False

generalStateAction :: (FS -> FS) -> State (FilePath, FS) ()
generalStateAction action = do
        (cwd, fs) <- get
        let (_:ps) = splitDirectories cwd
        let changedFS = updateFS fs ps action
        put (cwd, changedFS)

generalStateActionWithContent :: ([FilePath] -> FS -> b) -> FilePath -> State (FilePath, FS) b
generalStateActionWithContent action path = do
        (_, fs) <- get
        fullPath <- getFullPathS path
        let (_:xs) = splitDirectories fullPath
        let result = action xs fs
        return result

updateFS :: FS -> [FilePath] -> (FS -> FS) -> FS
updateFS (File _ _) (_:_) _  = error "Error in the try to change FS, incorrect path"
updateFS fs        []     action  = action fs
updateFS (Dir name files) (x : xs) action = Dir name $ map (\f -> if getName f == x then updateFS f xs action else f) files

addDirectory :: FilePath -> FS -> FS
addDirectory _ (File _ _)                   = error "Error in try to create folder in file, incorrect path"
addDirectory dirName (Dir name files) = Dir name (Dir dirName []:files)

addFile :: FilePath -> String -> FS -> FS
addFile _  _ (File _ _)                        = error "Error in try to create file in file, incorrect path"
addFile fileName content (Dir name files) = Dir name (File fileName content:filter (\f -> getName f /= fileName) files)

removePath :: FilePath -> FS -> FS
removePath _ (File _ _)              = error "Error in try to delete path from file, incorrect path"
removePath pathName (Dir name files) = Dir name (filter (\file -> getName file /= pathName) files)

getDirContent :: [FilePath] -> FS -> [FilePath]
getDirContent (p:ps) (Dir _ files) = case filter (\f -> getName f == p) files of
                                                        []    -> error "Directory not found"
                                                        (x:_) -> getDirContent ps x
getDirContent (_:_) (File _ _) = error "Directory not found"
getDirContent [] (Dir _ files) = map getName files
getDirContent [] (File _ _) = error "Directory not found"


findFile :: String -> FS -> Maybe FilePath
findFile fileName (Dir _ files) = case find (\f -> fileName ==  getName f) files of
                                            Just file -> Just $ getName file
                                            Nothing   -> msum $ map (findFile fileName) files
findFile _ (File _ _) = Nothing

getFileContent :: [FilePath] -> FS -> String
getFileContent (p:ps) (Dir _ files) = case filter (\f -> getName f == p) files of
                                                        []    -> error "File not found"
                                                        (x:_) -> getFileContent ps x
getFileContent (_:_) (File _ _) = error "File not found"
getFileContent [] (Dir _ _) = error "File not found"
getFileContent [] (File _ content) = content

joinPath :: FilePath -> FilePath -> FilePath
joinPath a b = a <> "/" <> b

normalise :: FilePath -> FilePath
normalise path = intercalate "/" $ removeExtraPoints [] "" (splitDirectories $ tail path) where
    removeExtraPoints (b:bs) _ ("..":xs) = removeExtraPoints bs b xs
    removeExtraPoints [] _ ("..":_)      = error "Path does not exist"
    removeExtraPoints beg prev (".":xs)  = removeExtraPoints beg prev xs
    removeExtraPoints beg prev (x:xs)    = removeExtraPoints (prev:beg) x xs
    removeExtraPoints beg prev []        = reverse (prev:beg)

getFullPathS :: FilePath -> State (FilePath, FS) FilePath
getFullPathS path = do
    (currDir, _) <- get
    let fullPath = normalise $ if isAbsolute path then path else joinPath currDir path
    return fullPath

splitDirectories :: FilePath -> [FilePath]
splitDirectories = splitOn "/"
