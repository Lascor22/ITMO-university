@ECHO off

SET ROOT=%cd%
cd %~dp0
cd ..

SET req=lib;artifacts

javac -cp .;lib\jsoup-1.8.1.jar info\kgeorgiy\java\advanced\crawler\*.java
javac ru\ifmo\rain\karimov\crawler\*.java

@ECHO on

java -cp . -p . --module-path %req% -m info.kgeorgiy.java.advanced.crawler hard ru.ifmo.rain.karimov.crawler.WebCrawler

@ECHO off

del info\kgeorgiy\java\advanced\crawler\*.class
del ru\ifmo\rain\karimov\crawler\*.class

cd %ROOT%

@ECHO on

