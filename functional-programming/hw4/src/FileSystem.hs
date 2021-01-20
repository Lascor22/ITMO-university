{-# LANGUAGE Rank2Types      #-}
{-# LANGUAGE RecordWildCards #-}
module FileSystem (
                    FS(..),
                    name,
                    contents,
                    dirs,
                    files,
                    cd,
                    ls,
                    file,
                    getDirectory
                    ) where


import Lens.Micro (Lens', Traversal', filtered, lens, traversed, (^.))
import System.Directory (doesDirectoryExist, listDirectory)

data FS
    = Dir { _name :: FilePath, _contents :: [FS]}
    | File { _name :: FilePath }


name :: Lens' FS FilePath
name = lens _name setName where
    setName :: FS -> FilePath -> FS
    setName fs newName = fs {_name = newName}

contents :: Lens' FS [FS]
contents = lens helper setContents where
    helper :: FS -> [FS]
    helper Dir {_contents = content} = content
    helper File {..}                 = []
    setContents :: FS -> [FS] -> FS
    setContents fs@Dir {..} directories = fs {_contents = directories}
    setContents fs@File {..} _          = fs

dirs :: Traversal' FS FS
dirs = contents.traversed.filtered isDir

files :: Traversal' FS FS
files = contents.traversed.filtered (not . isDir)

cd :: FilePath -> Traversal' FS FS
cd path = dirs.filtered (byName path)

ls :: Traversal' FS FilePath
ls = contents.traversed.name

file :: FilePath -> Traversal' FS FilePath
file path = files.filtered(byName path).name

getDirectory :: FilePath -> IO FS
getDirectory p = do
    isDirEx <- doesDirectoryExist p
    if isDirEx then
        getDirInfo p
    else
        return $ getFileInfo p

getNameByPath :: FilePath -> String
getNameByPath path = reverse $ getFileNameHelper path [] where
    getFileNameHelper :: String -> String -> String
    getFileNameHelper [] res     = res
    getFileNameHelper ('/':xs) _ = getFileNameHelper xs []
    getFileNameHelper (x:xs) res = getFileNameHelper xs (x:res)

getFileInfo :: FilePath -> FS
getFileInfo path = File $ getNameByPath path

getDirInfo :: FilePath -> IO FS
getDirInfo path = do
    let fileName = getNameByPath path
    directories <- listDirectory path
    fss <- subDirs path directories
    return $ Dir fileName fss

subDirs :: FilePath -> [FilePath] -> IO [FS]
subDirs _ []          = return []
subDirs prefix (x:xs) = (:) <$> getDirectory (prefix ++ '/' : x) <*> subDirs prefix xs

isDir :: FS -> Bool
isDir Dir {..} = True
isDir _        = False

byName :: FilePath -> FS -> Bool
byName path fs = path == fs ^. name
