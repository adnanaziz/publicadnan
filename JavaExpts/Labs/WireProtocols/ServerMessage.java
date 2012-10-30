import java.util.List;
import com.google.gson.*;

public class ServerMessage {

  enum Type {
    NEWPOSTINGID, QUERYRESULT
  }

  long id;
  List<Posting> postings;

  public ServerMessage() {
    // no argument constructor
  }

  public ServerMessage setId(long id) { this.id = id; return this; }
  public ServerMessage setPostings(List<Posting> postings) { this.postings = postings; return this; }

  public long getId() { return id; }
  public List<Posting> getPostings() { return postings; }

  // serialize to json string
  public String toJson() {
    return new Gson().toJson( this  );
  }

  // create from a json string
  public static ServerMessage fromJson( String s ) {
    return new Gson().fromJson( s, ServerMessage.class );
  }
}
