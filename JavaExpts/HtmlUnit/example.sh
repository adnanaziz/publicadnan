#!/bin/sh

SELENIUM=/home/adnan/adnanpublic/JavaExpts/HtmlUnit/selenium-2.19.0

# CLASSPATH=$SELENIUM/selenium-java-2.19.0.jar:.
CLASSPATH=selenium-server-standalone-2.19.0.jar:.

# poor man's make
# if [ HtmlUnitTest.java -nt HtmlUnitTest.class ]; then
#   echo Recompiling
  javac -cp $CLASSPATH Example.java
# fi
  java -cp $CLASSPATH Example
