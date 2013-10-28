import java.lang.StringBuffer;
import org.apache.commons.lang3.RandomStringUtils;
import com.google.common.base.Joiner;

public class GenerateTestData {

  String[] fixedStrings;
  char[] characters;

  final public static char[] DNA = new char[]{'a', 'c', 'g', 't'};

  GenerateTestData( int K, int L, char... characters ) {
    fixedStrings = new String[K];
    this.characters = characters;
    for ( int i = 0 ; i < K; i++ ) {
      fixedStrings[i] = RandomStringUtils.random(L, characters);
    }
  }

  public String randomString(int prefixLength, int id, int suffixLength ) {
    StringBuffer sb = new StringBuffer();
    sb.append(RandomStringUtils.random(prefixLength, DNA))
      .append(fixedStrings[id % fixedStrings.length])
      .append(RandomStringUtils.random(suffixLength, DNA));
    return sb.toString();
  }

  @Override
  public String toString() {
    Joiner joiner = Joiner.on("\n").useForNull("NULL");
    StringBuffer sb = new StringBuffer();
    sb.append("K (num fixedStrings) = " + fixedStrings.length + "\n");
    sb.append(joiner.join(fixedStrings));
    sb.append("\n");
    return sb.toString();
  }

  public static void main(String[] args) {
    String t1 = RandomStringUtils.random(8,'a','c','g','t');
    System.out.println("t1 = " + t1);
    GenerateTestData td = new GenerateTestData(2,4, DNA);
    System.out.println("td = \n" + td);
    String rs = td.randomString(10,10,10);
    System.out.println("random string = " + rs);
  }
}
