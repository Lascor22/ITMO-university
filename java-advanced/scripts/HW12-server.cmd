@ECHO off

SET ROOT=%cd%
cd %~dp0
cd ..

SET req=lib;artifacts

javac info\kgeorgiy\java\advanced\hello\*.java
javac ru\ifmo\rain\karimov\hello\*.java

@ECHO on

java -cp . -p . --module-path %req% -m info.kgeorgiy.java.advanced.hello server-i18n ru.ifmo.rain.karimov.hello.HelloUDPNonblockingServer

@ECHO off

del info\kgeorgiy\java\advanced\hello\HelloClient.class
del info\kgeorgiy\java\advanced\hello\HelloServer.class
del ru\ifmo\rain\karimov\hello\*.class

cd %ROOT%

@ECHO on

