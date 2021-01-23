@ECHO off

SET ROOT=%cd%
cd %~dp0

SET TEST_PATH=..\..\..\..\..\..\..\java-advanced-2020
SET PROJECT_PATH=..\..\..\..\..\
SET mod_name=ru.ifmo.rain.karimov.implementor
SET mod_path=ru\ifmo\rain\karimov\implementor
SET res_name=info.kgeorgiy.java.advanced
SET res_path=%TEST_PATH%\modules\%res_name%.implementor\info\kgeorgiy\java\advanced\implementor
SET src=%wd%\src\modules\%mod_name%
SET out=_build
SET req=%cd%\%TEST_PATH%\lib;%cd%\%TEST_PATH%\artifacts;

mkdir _build


javac --module-path %req% %PROJECT_PATH%\module-info.java *.java -d %out%

cd %out%

@ECHO on

jar -c --file=..\Implementor.jar --main-class=%mod_name%.Implementor --module-path=%req% module-info.class %mod_path%\*.class

rmdir /S /Q _build

cd %ROOT%



