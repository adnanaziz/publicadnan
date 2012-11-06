export CLASSPATH=.:/Users/adnanaziz/tmp/guava-10.0.1.jar:gson-2.2.2.jar
#javac -cp .:guava-13.0.1.jar:gson-2.2.2.jar MicroBlogServer.java Posting.java ClientMessage.java ServerMessage.java
#java -cp .:guava-13.0.1.jar:gson-2.2.2.jar MicroBlogServer
javac MicroBlogServer.java Posting.java ClientMessage.java ServerMessage.java
java MicroBlogServer
