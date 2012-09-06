rm -f PostfixSolution.class TestPostfix.class
javac -cp junit-4.10.jar:. TestPostfix.java PostfixSolution.java
java -cp junit-4.10.jar:. org.junit.runner.JUnitCore TestPostfix
