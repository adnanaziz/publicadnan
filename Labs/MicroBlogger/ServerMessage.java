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

  /**
   * Serialize to json string
   * @return serialized ServerMessage object
   */
  public String toJson() {
    String result = null;
	//TODOBEGIN(EE422C)

	//TODOEND(EE422C)
    return result;
  }

  /**
   *  create from a json string
   * @param s
   * 	input json string
   * @return
   * 	ServerMessage object
   */
  public static ServerMessage fromJson( String s ) {
    ServerMessage result = null;
	//TODOBEGIN(EE422C)

	//TODOEND(EE422C)
    return result;
  }
}
