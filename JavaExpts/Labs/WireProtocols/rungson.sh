rm -f *.class
javac -cp .:guava-13.0.1.jar:gson-2.2.2.jar:junit-4.10.jar TestMicroBlogger.java Posting.java ClientMessage.java ServerMessage.java
java -cp .:guava-13.0.1.jar:gson-2.2.2.jar:junit-4.10.jar org.junit.runner.JUnitCore TestMicroBlogger

