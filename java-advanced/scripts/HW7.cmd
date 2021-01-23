@ECHO off

SET ROOT=%cd%
cd %~dp0
cd ..

SET req=lib;artifacts

javac info\kgeorgiy\java\advanced\concurrent\*.java
javac ru\ifmo\rain\karimov\concurrent\IterativeParallelism.java

@ECHO on

java -cp . -p . --module-path %req% -m info.kgeorgiy.java.advanced.concurrent list ru.ifmo.rain.karimov.concurrent.IterativeParallelism

@ECHO off

del info\kgeorgiy\java\advanced\concurrent\*.class
del ru\ifmo\rain\karimov\concurrent\IterativeParallelism.class

cd %ROOT%

@ECHO on