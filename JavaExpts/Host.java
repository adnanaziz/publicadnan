import java.util.Arrays;
import java.util.Comparator;
import java.io.Serializable;

public class Host {
  private static class StrLenCmp implements 
            Comparator<String>, Serializable {
    public int compare( String s1, String s2) {
        return s1.length() - s2.length();
    }
  }
  public static final Comparator<String> 
            STRING_LENGTH_COMPARATOR = new StrLenCmp();

  public static void main(String[] args) {
    System.out.println("compare of xxx and aaaaa:" 
        + STRING_LENGTH_COMPARATOR.compare( "xxx", "aaaaa" ) );
    String[] stringArray = {"a", "bb", "aaa", "x"};
    Arrays.sort( stringArray, STRING_LENGTH_COMPARATOR);
    for ( String s : stringArray ) System.out.print( s + " " );
    System.out.println();
  }
}
