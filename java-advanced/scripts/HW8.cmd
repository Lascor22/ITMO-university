@ECHO off

SET ROOT=%cd%
cd %~dp0
cd ..

SET req=lib;artifacts

javac info\kgeorgiy\java\advanced\concurrent\*.java
javac info\kgeorgiy\java\advanced\mapper\ParallelMapper.java
javac ru\ifmo\rain\karimov\concurrent\*.java

@ECHO on

java -cp . -p . --module-path %req% -m info.kgeorgiy.java.advanced.mapper list ru.ifmo.rain.karimov.concurrent.IterativeParallelism

@ECHO off

del info\kgeorgiy\java\advanced\concurrent\*.class
del info\kgeorgiy\java\advanced\mapper\ParallelMapper.class
del ru\ifmo\rain\karimov\concurrent\*.class

cd %ROOT%

@ECHO on

