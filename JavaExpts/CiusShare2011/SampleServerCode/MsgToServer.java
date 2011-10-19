import java.io.*;

class MsgToServer<R extends Serializable> implements Serializable {
  long id;
  String e64;
  long readId() { return id; }
  public MsgToServer( long id, R r ) throws Exception {
    this.id = id;
    this.e64 = Base64.encodeObject( r );
  }
}
