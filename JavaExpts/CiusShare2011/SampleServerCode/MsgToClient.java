import java.util.*;
import java.net.*;
import java.io.*;

public class MsgToClient<T extends Serializable> implements Serializable {
  long id;
  int timeout;
  String e64;
  long readId() { return id; }
  public MsgToClient( long id, int timeout, T t ) throws Exception {
    this.id = id;
    this.timeout = timeout;
    this.e64 = Base64.encodeObject( t );
  }
}
