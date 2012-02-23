#!/bin/sh

CLASSPATH=/home/adnan/adnanpublic/JavaExpts/Guava/guava-10.0.1.jar:selenium-server-standalone-2.19.0.jar:.

# poor man's make
# if [ HtmlUnitTest.java -nt HtmlUnitTest.class ]; then
#   echo Recompiling
  javac -cp $CLASSPATH Option.java OptionParse.java
# fi
  java -cp $CLASSPATH OptionParse
