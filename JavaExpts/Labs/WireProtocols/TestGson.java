import com.google.gson.*;

class BagOfPrimitives {
  private int value1 = 1;
  private String value2 = "abc";
  private transient int value3 = 3;
  @Override public String toString() {
    return "" + value1 + ":" + value2 + ":" + value3;
  }
  BagOfPrimitives() {
    // no-args constructor
  }
}

public class TestGson {
  public static void main(String [] args) {
    Gson gson = new Gson();
    String s = gson.toJson(1);
    System.out.println(s);
    s = gson.toJson("abcd"); 
    System.out.println(s);
    s = gson.toJson(new Long(10));
    System.out.println(s);
    int[] values = { 1 };
    s = gson.toJson(values);  
    System.out.println(s);

    int one = gson.fromJson("1", int.class);
    Integer Ione = gson.fromJson("1", Integer.class);
    Long Lone = gson.fromJson("1", Long.class);
    Boolean bfalse = gson.fromJson("false", Boolean.class);
    String str = gson.fromJson("\"abc\"", String.class);
    String [] anotherStrArray = gson.fromJson("[\"abc\",\"xyz\"]", String[].class);
    for ( String tmp : anotherStrArray ) {
      System.out.println("from array:" + tmp);
    }

    BagOfPrimitives obj = new BagOfPrimitives();
    gson = new Gson();
    s = gson.toJson(obj);  
    System.out.println(s);
    BagOfPrimitives obj2 = gson.fromJson(s, BagOfPrimitives.class);  
    System.out.println(obj2.toString());
   
  }
}
