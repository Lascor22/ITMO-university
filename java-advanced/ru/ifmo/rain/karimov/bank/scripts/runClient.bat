@ECHO off
rem RUN CLIENT
SET ROOT=%cd%
cd %~dp0
cd ..\..\..\..\..\..\
@ECHO on

java ru.ifmo.rain.karimov.bank.Client %*

@ECHO off
cd %ROOT%
@ECHO on



