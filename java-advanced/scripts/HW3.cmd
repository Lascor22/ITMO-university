@ECHO off

SET ROOT=%cd%
cd %~dp0
cd ..

SET req=lib;artifacts

javac info\kgeorgiy\java\advanced\student\*.java
javac ru\ifmo\rain\karimov\student\StudentDB.java

@ECHO on

java -cp . -p . --module-path %req% -m info.kgeorgiy.java.advanced.student StudentGroupQuery ru.ifmo.rain.karimov.student.StudentDB

@ECHO off

del info\kgeorgiy\java\advanced\student\*.class
del ru\ifmo\rain\karimov\student\StudentDB.class

cd %ROOT%

@ECHO on