rm -f *.class
javac -cp .:/Users/adnanaziz/adnanpublic/JavaExpts/Labs/WireProtocols/guava-13.0.1.jar:/Users/adnanaziz/adnanpublic/JavaExpts/Labs/WireProtocols/gson-2.2.2.jar:/Users/adnanaziz/adnanpublic/JavaExpts/Labs/WireProtocols/junit-4.10.jar TestMicroBlogger.java Posting.java ClientMessage.java ServerMessage.java MicroBlogServer.java MicroBlogClient.java Posting.java 
java -cp .:/Users/adnanaziz/adnanpublic/JavaExpts/Labs/WireProtocols/guava-13.0.1.jar:/Users/adnanaziz/adnanpublic/JavaExpts/Labs/WireProtocols/gson-2.2.2.jar:/Users/adnanaziz/adnanpublic/JavaExpts/Labs/WireProtocols/junit-4.10.jar org.junit.runner.JUnitCore TestMicroBlogger
#java -cp .:guava-13.0.1.jar:gson-2.2.2.jar:junit-4.10.jar TestGson
