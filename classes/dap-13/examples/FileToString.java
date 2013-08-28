import java.io.*;

public class FileToString {

  private static String readFile( String file ) throws IOException {

      BufferedReader reader = new BufferedReader( new FileReader (file));

      String         line;
      StringBuilder sb = new StringBuilder();

      // make more robust using StringBuilder and line.seperator
      // via String ls = System.getProperty("line.separator");
      String ls = System.getProperty("line.separator"); 
      
      while( ( line = reader.readLine() ) != null ) {
          sb.append(line + ls);
      }
      reader.close();

      return sb.toString();
  }


  public static void main(String[] args) {
    String s1=null;
    try {
      s1 = readFile("/Users/adnanaziz/Documents/workspace/DAP/src/FileToString.java");
    } catch (IOException e) {
      e.printStackTrace();
    }
    System.out.println(s1);
  }
}

