@ECHO off
rem RUN SERVER
SET ROOT=%cd%
cd %~dp0
cd ..\..\..\..\..\..\
@ECHO on

java ru.ifmo.rain.karimov.bank.Server

@ECHO off
cd %ROOT%
@ECHO on




