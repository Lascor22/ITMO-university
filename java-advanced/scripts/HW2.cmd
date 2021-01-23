@ECHO off

SET ROOT=%cd%
cd %~dp0
cd ..

SET req=lib;artifacts

javac ru\ifmo\rain\karimov\arrayset\ArraySet.java

@ECHO on

java -cp . -p . --module-path %req% -m info.kgeorgiy.java.advanced.arrayset NavigableSet ru.ifmo.rain.karimov.arrayset.ArraySet

@ECHO off

del ru\ifmo\rain\karimov\arrayset\*.class

cd %ROOT%

@ECHO on