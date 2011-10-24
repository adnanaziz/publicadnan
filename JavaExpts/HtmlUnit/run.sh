#!/bin/sh

CLASSPATH=lib/commons-codec-1.4.jar:lib/commons-collections-3.2.1.jar:lib/commons-io-2.0.1.jar:lib/commons-lang-2.6.jar:lib/commons-logging-1.1.1.jar:lib/cssparser-0.9.5.jar:lib/htmlunit-2.9.jar:lib/htmlunit-core-js-2.9.jar:lib/httpclient-4.1.2.jar:lib/httpcore-4.1.2.jar:lib/httpmime-4.1.2.jar:lib/junit-4.10.jar:lib/nekohtml-1.9.15.jar:lib/sac-1.3.jar:lib/serializer-2.7.1.jar:lib/xalan-2.7.1.jar:lib/xercesImpl-2.9.1.jar:lib/xml-apis-1.3.04.jar:.

# poor man's make
if [ HtmlUnitTest.java -nt HtmlUnitTest.class ]; then
  echo Recompiling
  javac -cp $CLASSPATH HtmlUnitTest.java
fi
java -cp $CLASSPATH HtmlUnitTest
