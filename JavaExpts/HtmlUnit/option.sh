#!/bin/sh

CLASSPATH=guava-10.0.1.jar:selenium-server-standalone-2.19.0.jar:.

# poor man's make
# if [ HtmlUnitTest.java -nt HtmlUnitTest.class ]; then
#   echo Recompiling
  javac -cp $CLASSPATH Option.java OptionParse.java OptionScraper.java OptionAnalyzer.java OptionValue.java OptionPair.java
# fi
  # java -cp $CLASSPATH OptionParse Option
  java -cp $CLASSPATH OptionAnalyzer
