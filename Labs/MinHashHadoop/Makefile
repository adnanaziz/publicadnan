CP=guava-15.0.jar:commons-lang3-3.1.jar:.
JVMOPTIONS=-Xmx2000m -cp $(CP)

minhashtest: MinHashTest.java
	javac -cp $(CP) MinHashTest.java;
	java $(JVMOPTIONS) -cp $(CP) MinHashTest

sigtest: Signature.java
	javac -cp $(CP) Signature.java;
	java $(JVMOPTIONS) -cp $(CP) Signature

datatest: GenerateTestData.java
	javac -cp $(CP) GenerateTestData.java;
	java $(JVMOPTIONS) -cp $(CP) GenerateTestData
