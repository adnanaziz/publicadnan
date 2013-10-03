#!/bin/bash
CP=/usr/share/java/junit.jar:.
javac -cp $CP PPP.java PPPTest.java Poker5Card.java Poker5CardTest.java 
java -cp $CP org.junit.runner.JUnitCore Poker5CardTest
java -cp $CP org.junit.runner.JUnitCore PPPTest
