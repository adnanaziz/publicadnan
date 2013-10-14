#!/bin/bash
CP=/usr/share/java/junit.jar:.
javac -cp $CP MaxSubarray.java MaxSubarrayTest.java 
java -cp $CP org.junit.runner.JUnitCore MaxSubarrayTest
