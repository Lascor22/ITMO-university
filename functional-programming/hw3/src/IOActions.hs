module IOActions (
                createFileInfo,
                getFullPath,
                generalIOAction
                 ) where

import Control.Monad.Reader (MonadIO (liftIO), MonadReader (ask), ReaderT)
import Data.IORef (IORef, readIORef)
import System.Directory (Permissions (..), canonicalizePath)
import System.FilePath (joinPath)
import System.FilePath.Posix (isAbsolute)

createFileInfo :: (Show a) => Permissions -> a -> Integer -> String -> String
createFileInfo perm lastModified size fullPath =
    unlines $ [pathInfo, sizeInfo, modification] <> permissions where
        permissions = [header, readStr, writeStr, executeStr] where
            header = "permissions:"
            readStr = "\treadble " <> if readable perm then "yes" else "no"
            writeStr = "\twritable " <> if writable perm then "yes" else "no"
            executeStr = "\texecutable " <> if executable perm then "yes" else "no"
        modification = "Last modification time - " <> show lastModified
        sizeInfo = "size " <> show size <> "B"
        pathInfo = "Information about " <> fullPath

getFullPath :: FilePath -> ReaderT (IORef FilePath) IO FilePath
getFullPath path = do
    curDirPath <- ask >>= (liftIO . readIORef)
    let rawPath =  if isAbsolute path then path else joinPath [curDirPath, path]
    liftIO $ canonicalizePath rawPath

generalIOAction :: (FilePath -> IO b) -> FilePath -> ReaderT (IORef FilePath) IO b
generalIOAction action path = do
        fullPath <- getFullPath path
        liftIO $ action fullPath
