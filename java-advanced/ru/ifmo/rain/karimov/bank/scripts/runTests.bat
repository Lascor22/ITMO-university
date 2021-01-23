@ECHO off
rem CALL COMPILE RUN TESTS CALL CLEAR
SET ROOT=%cd%
cd %~dp0
call compile.bat
cd ..\..\..\..\..\..\
SET LIB=./lib
@ECHO on

java -cp %LIB%\*;. org.junit.runner.JUnitCore  ru.ifmo.rain.karimov.bank.BankTest

@ECHO off
cd %~dp0
call clear.bat
cd %ROOT%
@ECHO on







