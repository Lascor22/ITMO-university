@ECHO off
rem COMPILE
SET ROOT=%cd%
cd %~dp0
cd ..\..\..\..\..\..\
SET LIB=../../java-advanced-2020/lib
javac -cp .;%LIB%/* -encoding UTF-8 ru\ifmo\rain\karimov\i18n\bundles\*.java
javac -cp .;%LIB%/* -encoding UTF-8 ru\ifmo\rain\karimov\i18n\stats\*.java
javac -cp .;%LIB%/* -encoding UTF-8 ru\ifmo\rain\karimov\i18n\*.java

cd %ROOT%
@ECHO on




