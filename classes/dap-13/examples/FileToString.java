import java.io.*;

public class FileToString {

  private static String readFile( String file ) throws IOException {

      BufferedReader reader = new BufferedReader( new FileReader (file));

      String         line;
      String result = "";

      // make more robust using StringBuilder and line.seperator
      // via String ls = System.getProperty("line.separator");
  
      while( ( line = reader.readLine() ) != null ) {
          result += line + "\n";
      }
      reader.close();
  
      return result;
  }
  

  public static void main(String[] args) {
    String s1=null;
    try {
      s1 = readFile("FileToString.java");
    } catch (IOException e) {
      e.printStackTrace();
    }
    System.out.println(s1);
  }
}
