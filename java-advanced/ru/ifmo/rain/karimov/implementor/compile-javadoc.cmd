@ECHO off

SET ROOT=%cd%
cd %~dp0

SET TEST_PATH=../../../../../../../java-advanced-2020
SET mod_name=ru.ifmo.rain.karimov.implementor
SET mod_path=ru\ifmo\rain\karimov\implementor
SET res_name=info.kgeorgiy.java.advanced
SET res_path=%TEST_PATH%\modules\%res_name%.implementor\info\kgeorgiy\java\advanced\implementor
SET req=%TEST_PATH%\lib;%TEST_PATH%\artifacts;
SET "reference=--module %res_name%.base --module %res_name%.implementor"

@ECHO on

javadoc -d _javadoc -link https://docs.oracle.com/en/java/javase/11/docs/api/ --module-path %req% -private -version -author --module-source-path %TEST_PATH%\modules --module %mod_name% %reference%

cd %ROOT%
