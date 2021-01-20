module Resources (
                helpContent
                 ) where

helpContent :: String
helpContent =  unlines ["cd <folder> -- move to the directory",
                "dir -- show the contents of the current directory",
                "ls <folder> -- show the contents of the selected directory",
                "create-folder <folder-name> -- create a directory in the current folder",
                "cat <file> -- display file contents",
                "create-file <file-name> -- create an empty file in the current directory",
                "remove <folder | file> -- delete the currently selected directory or file",
                "write-file <file> <text> -- write text to a file",
                "find-file <file-name> -- search for a file in the current directory and subdirectories",
                "information <file> -- show file information",
                "information <folder> -- show directory information",
                "help -- show how to use",
                "exit -- shut down the program"
                ]
