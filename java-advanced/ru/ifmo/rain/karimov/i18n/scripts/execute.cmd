@ECHO off
rem EXECUTE
SET ROOT=%cd%
cd %~dp0
cd ..\..\..\..\..\..\
@ECHO on

java ru.ifmo.rain.karimov.i18n.TextStatistics %*

@ECHO off
cd %ROOT%
@ECHO on




