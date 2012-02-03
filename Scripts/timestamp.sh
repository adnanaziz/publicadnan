#!/bin/sh

CLASSPATH=lib/commons-codec-1.4.jar:.

if [ HtmlUnitTest.java -nt HtmlUnitTest.class ]; 
  then
    echo Recompiling
    javac -cp $CLASSPATH HtmlUnitTest.java
  else 
    echo Not recompiling
fi
java -cp $CLASSPATH HtmlUnitTest
