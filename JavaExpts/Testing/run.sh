javac -cp junit-4.10.jar:easymock-3.0.jar:. TestJukebox.java Jukebox.java
java -cp junit-4.10.jar:.:easymock-3.0.jar:objenesis-tck-1.2.jar:cglib-nodep-2.2.2.jar org.junit.runner.JUnitCore TestJukebox 
