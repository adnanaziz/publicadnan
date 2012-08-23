rm -f RPN.class TestRPN.class
javac -cp junit-4.10.jar:. TestRPN.java RPN.java
java -cp junit-4.10.jar:. org.junit.runner.JUnitCore TestRPN
