@ECHO off
rem CALL COMPILE RUN TESTS CALL CLEAR
SET ROOT=%cd%
cd %~dp0
call compile.cmd
cd ..\..\..\..\..\..\
SET LIB=../../java-advanced-2020/lib
@ECHO on

java -cp %LIB%\*;. org.junit.runner.JUnitCore  ru.ifmo.rain.karimov.i18n.StatisticsTest

@ECHO off
cd %~dp0
call clear.cmd
cd %ROOT%
@ECHO on







