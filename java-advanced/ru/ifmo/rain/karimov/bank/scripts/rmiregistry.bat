@ECHO off
rem COMPILE AND RUN RMIREGISTRY
SET ROOT=%cd%
cd %~dp0
cd ..\..\..\..\..\..\
SET LIB=./lib
@ECHO on

javac -cp .;%LIB%/* ru/ifmo/rain/karimov/bank/*.java
rmiregistry.exe
@ECHO off
cd %ROOT%
@ECHO on



