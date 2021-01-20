module FileManager (
                    fileManager
                   ) where

import Control.Exception (catch)
import Control.Exception.Base (SomeException)
import Control.Monad.Reader (MonadIO (liftIO), MonadReader (ask), ReaderT (runReaderT))
import Data.IORef (readIORef, IORef)
import Data.IORef.Extra (newIORef)
import Data.Maybe (fromJust, isJust)
import Parser (execCmd, parse)
import System.Directory.Extra (getCurrentDirectory)
import System.IO (stdout)
import System.IO.Extra (hFlush)

fileManager :: IO ()
fileManager = do
    currDir <- getCurrentDirectory
    ref <- newIORef currDir
    runReaderT fileManager' ref

fileManager' :: ReaderT (IORef FilePath) IO ()
fileManager' = do
    putPrefix
    _     <- liftIO $ hFlush stdout
    input <- liftIO getLine
    if input == "exit" then
        return ()
    else do
        let cmd = parse input
        oldContext <- ask
        liftIO $ catch (if isJust cmd
                        then runReaderT (execCmd $ fromJust cmd) oldContext
                        else putStrLn "Invalid command") handleError
        newContext <- ask
        liftIO $ runReaderT fileManager' newContext

handleError :: SomeException  -> IO ()
handleError e = do
    print e
    return ()

putPrefix :: ReaderT (IORef FilePath) IO ()
putPrefix = do
    cwdRef <- ask
    cwd <- liftIO $ readIORef cwdRef
    let pref = cwd <> ">"
    liftIO $ putStr pref
