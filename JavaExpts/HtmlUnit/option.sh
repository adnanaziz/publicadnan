#!/bin/sh

CLASSPATH=selenium-server-standalone-2.19.0.jar:.

# poor man's make
# if [ HtmlUnitTest.java -nt HtmlUnitTest.class ]; then
#   echo Recompiling
  javac -cp $CLASSPATH OptionScraper.java
# fi
  java -cp $CLASSPATH OptionScraper
