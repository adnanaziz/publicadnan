import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.collect.ImmutableList;
import com.google.gson.*;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import com.google.gson.reflect.TypeToken;

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
    int N = 10000000;
    System.out.println("Start: " + N + "" + new Date());
    for ( int i = 0; i < N; i++ ) {
      s = gson.toJson(obj);  
      // System.out.println(s);
      BagOfPrimitives obj2 = gson.fromJson(s, BagOfPrimitives.class);  
      // System.out.println(obj2.toString());
    }
    System.out.println("End: " + new Date());

    gson = new GsonBuilder().setPrettyPrinting().create();
    // Collection<Integer> ints = Lists.ImmutableList(1,2,3,4,5);
    Collection<Integer> ints = new ArrayList<Integer>();
    ints.add(1);
    ints.add(2);
    ints.add(3);
    String json = gson.toJson(ints);
    System.out.println("pretty :" + json);
    Type collectionType = new TypeToken<Collection<Integer>>(){}.getType();
    Collection<Integer> ints2 = gson.fromJson(json, collectionType);
    for (Integer i : ints2) {
      System.out.print(i + ",");
    }
    System.out.println();

    ClientToServer c = ClientToServer.buildUpdate( 123, "adnan", "hello world", "this is my first msg", 12.3, -45.6);
    s = gson.toJson(c);
    System.out.println("s string = " + s);
    ClientToServer cdes = gson.fromJson(s, ClientToServer.class);
    System.out.println("cdes object = " + cdes.toString());

    P pobj = new P("p");
    Q qobj = new Q("p", "q");
    R robj = new R(123, "p");

    String sp = gson.toJson(pobj);
    String sq = gson.toJson(qobj);
    String sr = gson.toJson(robj);
    System.out.println("sp = " + sp);
    System.out.println("sq = " + sq);
    System.out.println("sr = " + sr);

    robj = gson.fromJson(sp, R.class);
    sr = gson.toJson(robj);
    System.out.println("sr = " + sr);
    robj = gson.fromJson(sq, R.class);
    sr = gson.toJson(robj);
    System.out.println("sr = " + sr);


  }
}

class P {
  String p;
  P() {}
  P(String p) {this.p = p;}
}

class Q extends P {
  String q;
  Q() {}
  Q(String r, String q) {super(r); this.q = q;}
}

class R extends P {
  int r;
  R() {}
  R(int r, String p) {super(p); this.r = r;}
}


class ClientToServer {
  static enum msgType {
    QUERY, UPDATE;
  }
  msgType type;
  long id;
  long time;
  long startTime;
  long endTime;
  double lat;
  double longitude;
  String sender;
  String subject;
  String body;

  List<R> aList = new ArrayList<R>();

  ClientToServer() {
    // no arg constructor
  }

  @Override public String toString() {
    Joiner joiner = Joiner.on(":");
    return joiner.join( "" , type ,  id ,  time ,  startTime ,  endTime ,  lat ,  longitude ,  sender );
  }

  public static ClientToServer  buildUpdate( long time, String sender, String subject, String body, double lat, double longitude) {
    ClientToServer result = new ClientToServer();
    result.aList.add( new R(123,"tmp1") );
    result.aList.add( new R(456,"tmp2") );
    result.time = time;
    result.sender = sender;
    result.subject = subject;
    result.body = body;
    result.lat = lat;
    result.longitude = longitude;
    result.type = msgType.UPDATE;
    return result;
  }
}




class InstanceCreatorForB implements InstanceCreator<A.B> {
  private final A a;
  public InstanceCreatorForB(A a)  {
    this.a = a;
  }
  public A.B createInstance(Type t) {
    return a.new B();
  }
}

class A { 
  public String a; 
  class B { 
    public String b; 
    public B(String x) {
      b = x;
    }
    public B() {
      // No args constructor for B
    }
  } 
}

