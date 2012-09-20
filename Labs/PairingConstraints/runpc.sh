rm -f TestPairingConstraints.class PairingConstraints.class
javac -cp junit-4.10.jar:. TestPairingConstraints.java PairingConstraints.java
java -cp junit-4.10.jar:. org.junit.runner.JUnitCore TestPairingConstraints
