import java.util.List;

public class ServerMessage {
  enum Type {
    NEWPOSTINGID, QUERYRESULT
  }
  public ServerMessage() {
    // no argument constructor
  }
  public ServerMessage setId(long id) { return this; }
  public ServerMessage setQueryResult(List<Posting> list) { return this; }
  public List<Posting> getResult() { return null; }

  // serialize to json string
  public String toJson() {
    return null;
  }

  // create from a json string
  public static ServerMessage fromDJson( String s ) {
    return null;
  }
}
