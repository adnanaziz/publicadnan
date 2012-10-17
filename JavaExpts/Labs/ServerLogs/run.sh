javac -cp junit-4.10.jar:guava-13.0.1.jar:. ee422C/ReferenceLogProcessor.java TestLogProcessor.java LogProcessor.java
#java -Djava.security.manager -Djava.security.policy=ee422c.policy -cp junit-4.10.jar:guava-13.0.1.jar:. org.junit.runner.JUnitCore TestLogProcessor 
java -cp junit-4.10.jar:guava-13.0.1.jar:. org.junit.runner.JUnitCore TestLogProcessor 
# ReferenceLogProcessor LogProcessor
