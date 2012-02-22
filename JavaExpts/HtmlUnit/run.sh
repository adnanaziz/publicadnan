#!/bin/sh

HTMLUNIT=/home/adnan/htmlunit/htmlunit-2.9

CLASSPATH=$HTMLUNIT/lib/commons-codec-1.4.jar:$HTMLUNIT/lib/commons-collections-3.2.1.jar:$HTMLUNIT/lib/commons-io-2.0.1.jar:$HTMLUNIT/lib/commons-lang-2.6.jar:$HTMLUNIT/lib/commons-logging-1.1.1.jar:$HTMLUNIT/lib/cssparser-0.9.5.jar:$HTMLUNIT/lib/htmlunit-2.9.jar:$HTMLUNIT/lib/htmlunit-core-js-2.9.jar:$HTMLUNIT/lib/httpclient-4.1.2.jar:$HTMLUNIT/lib/httpcore-4.1.2.jar:$HTMLUNIT/lib/httpmime-4.1.2.jar:$HTMLUNIT/lib/junit-4.10.jar:$HTMLUNIT/lib/nekohtml-1.9.15.jar:$HTMLUNIT/lib/sac-1.3.jar:$HTMLUNIT/lib/serializer-2.7.1.jar:$HTMLUNIT/lib/xalan-2.7.1.jar:$HTMLUNIT/lib/xercesImpl-2.9.1.jar:$HTMLUNIT/lib/xml-apis-1.3.04.jar:junit-4.10.jar:.

# poor man's make
# if [ HtmlUnitTest.java -nt HtmlUnitTest.class ]; then
#   echo Recompiling
  javac -cp $CLASSPATH YahooFinance.java
# fi
java -cp $CLASSPATH YahooFinance
