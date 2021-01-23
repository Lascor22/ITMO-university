@ECHO off

SET ROOT=%cd%
cd %~dp0
cd ..

SET req=lib;artifacts

javac ru\ifmo\rain\karimov\walk\*.java

@ECHO on

java -cp . -p . --module-path %req% -m info.kgeorgiy.java.advanced.walk RecursiveWalk ru.ifmo.rain.karimov.walk.RecursiveWalk

@ECHO off

del ru\ifmo\rain\karimov\walk\*.class

rmdir /S /Q __Test__Walk__

cd %ROOT%

@ECHO on