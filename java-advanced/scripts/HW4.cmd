@ECHO off

SET ROOT=%cd%
cd %~dp0
cd ..

SET req=lib;artifacts

javac info\kgeorgiy\java\advanced\implementor\*.java
javac ru\ifmo\rain\karimov\implementor\*.java

@ECHO on

java -cp . -p . --module-path %req% -m info.kgeorgiy.java.advanced.implementor class ru.ifmo.rain.karimov.implementor.Implementor

@ECHO off

del info\kgeorgiy\java\advanced\implementor\*.class
del ru\ifmo\rain\karimov\implementor\*.class

cd %ROOT%

@ECHO on

