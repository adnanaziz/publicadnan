public class TestMsg {
  public static void main( String [] args ) {
    long x = 43;
    Integer i = 33;
    try {  
      MsgToClient msg = new MsgToClient( x, 100, i );
      String sendString = Base64.encodeObject( msg );
    } catch (Exception e) { e.printStackTrace(); } 
  }
}
