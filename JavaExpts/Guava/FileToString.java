import java.io.File;
import java.io.IOException;
import com.google.common.base.Charsets;
import com.google.common.io.Files;

public class FileToString {
  public static String fileName = "FileToString.java";
  public static void main(String[] args) {
    try {
      String s = Files.toString(new File( fileName ), Charsets.UTF_8);
      System.out.println(fileName + " :\n" + s);
    } catch (IOException e) {
      System.out.println("IOException:" + e.toString());
    }
  }
}
