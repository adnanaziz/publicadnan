rm -f *.class
javac -cp junit-4.10.jar:. TestSudoku.java SudokuSolver.java SudokuServer.java SudokuClient.java
java -cp junit-4.10.jar:. org.junit.runner.JUnitCore TestSudoku
