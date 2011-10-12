javac -cp . NodeType.java Node.java EvalQuant.java
java -cp antlrworks.jar org.antlr.Tool BooleanExp.g 
javac -cp .:antlrworks.jar BooleanExpDemo.java 
java -ea -cp .:antlrworks.jar BooleanExpDemo
