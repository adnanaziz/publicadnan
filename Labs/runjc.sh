rm -f TestJavaConstructs.class JavaConstructs.class
javac -cp junit-4.10.jar:. TestJavaConstructs.java JavaConstructs.java
java -cp junit-4.10.jar:. org.junit.runner.JUnitCore TestJavaConstructs
