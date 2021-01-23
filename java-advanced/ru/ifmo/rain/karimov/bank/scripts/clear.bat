@ECHO off
rem CLEAR ALL .class files
SET ROOT=%cd%
cd %~dp0
cd ..
@ECHO on

del *.class

@ECHO off
cd %ROOT%
@ECHO on

