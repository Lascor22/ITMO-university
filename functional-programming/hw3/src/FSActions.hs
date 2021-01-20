{-# LANGUAGE FlexibleInstances #-}
module FSActions where

import Control.Monad.Reader (MonadIO (liftIO), MonadReader (ask), ReaderT)

import Control.Monad.State (MonadState (get, put), State)

import Data.IORef (IORef, readIORef, writeIORef)

import Data.List (find, isSuffixOf)

import IOActions (createFileInfo, generalIOAction, getFullPath)

import Resources (helpContent)

import StateActions (FS, addDirectory, addFile, findFile, generalStateAction,
                     generalStateActionWithContent, getDirContent, getFileContent, getFullPathS,
                     removePath)

import System.Directory (createDirectory, getDirectoryContents, getFileSize, getModificationTime,
                         getPermissions, removePathForcibly)

import System.Directory.Extra (listFilesRecursive)

import System.FilePath (pathSeparator)

class (Monad m) => FSActions m where
    -- change directory
    cd :: FilePath -> m ()

    -- show current directory contents
    dir :: m [FilePath]
    dir = ls "."

    -- show given directory contents
    ls :: FilePath -> m [FilePath]

    -- create new folder
    mkdir :: FilePath -> m ()

    -- create new empty file
    touch :: FilePath -> m ()
    touch path = writeToFile path ""

    -- write text to file
    writeToFile :: FilePath -> String -> m ()

    -- show file content
    cat :: FilePath -> m String

    -- remove file or directory
    remove :: FilePath -> m ()

    -- search file in current directory and subdirectories
    searchFile :: String -> m (Maybe FilePath)

    -- get information about file or directory
    information :: FilePath -> m String

    -- get current directory
    pwd :: m FilePath

    -- show helpful information about application
    help :: m String
    help = return helpContent


instance FSActions (ReaderT (IORef FilePath) IO) where
    cd path = do
        curDirRef <- ask
        newPath <- getFullPath path
        liftIO $ writeIORef curDirRef newPath

    pwd = do
        ref <- ask
        liftIO $ readIORef ref

    ls = generalIOAction getDirectoryContents

    writeToFile path content = generalIOAction (`writeFile` content) path

    remove = generalIOAction removePathForcibly

    mkdir = generalIOAction createDirectory

    cat = generalIOAction readFile

    searchFile fileName = do
                    currDir <- getFullPath "."
                    files <- liftIO $ listFilesRecursive currDir
                    let result = find (isSuffixOf $ pathSeparator:fileName) files
                    return result

    information path = do
                    fullPath <- getFullPath path
                    size <- liftIO $ getFileSize fullPath
                    perm <- liftIO $ getPermissions fullPath
                    lastModified <- liftIO $ getModificationTime fullPath
                    return $ createFileInfo perm lastModified size fullPath


instance FSActions (State (FilePath, FS)) where
    cd path = do
        (_, fs) <- get
        newCurDir <- getFullPathS path
        put (newCurDir, fs)

    pwd = do
        (curDir, _) <- get
        return curDir

    mkdir name = generalStateAction $ addDirectory name

    ls = generalStateActionWithContent getDirContent

    cat = generalStateActionWithContent getFileContent

    remove name = generalStateAction $ removePath name

    writeToFile path content = generalStateAction $ addFile path content

    information = getFullPathS

    searchFile name = do
        (_, fs) <- get
        return $ findFile name fs
