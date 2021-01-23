@ECHO off
rem COMPILE PROJECT
SET ROOT=%cd%
cd %~dp0
cd ..\..\..\..\..\..\
SET LIB=./lib
@ECHO on

javac -cp .;%LIB%/* ru/ifmo/rain/karimov/bank/*.java

@ECHO off
cd %ROOT%
@ECHO on



