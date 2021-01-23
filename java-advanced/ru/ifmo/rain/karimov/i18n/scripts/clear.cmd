@ECHO off
rem CLEAR
SET ROOT=%cd%
cd %~dp0
cd ..
del *.class bundles\*.class stats\*.class
cd %ROOT%
@ECHO on




