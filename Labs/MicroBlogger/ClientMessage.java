import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import com.google.gson.*;

@Target(TYPE)
@Retention(RUNTIME)
//TODO(EE422C): update these to your name and eid
@interface Author {
  public String name() default  "Miguel Indurain" ;
  public String uteid() default  "mitdf5" ;
}
@Author(name="Miguel Indurain", uteid="mitdf5")

public class ClientMessage {

  public enum Type {
    CREATE, UPDATE, LIKE, UPVOTE, DELETE, QUERY, QUERYBYID, TRENDING;
  }

  private Type type;
  // TODO: fill in fields needed to support operations
  private String author;
  private long id;
//  private long time;
  private String body;

  ClientMessage() {
    // no argument constructor used by GSON
  }

  /**
   * Serialize this to a json string
   * @return the serialized client message object
   */
  public String toJson() {
    String result = null;
	//TODOBEGIN(EE422C)
    
	//TODOEND(EE422C)
    return result;
  }

  /**
   * Create a ClientMessage object from the Json string
   * @param s
   * @return
   */
  public static ClientMessage fromJson( String s ) {
    ClientMessage result = null;
	//TODOBEGIN(EE422C)
    
	//TODOEND(EE422C)
    return result;
  }

  // TODO: all setter methods should return this so that calls can be chained
  public ClientMessage setType(Type type){ this.type = type; return this; }
  public ClientMessage setAuthor(String author){ this.author = author; return this; }
//  public ClientMessage setTime(long time){ this.time = time; return this; }

  public ClientMessage setDate(long date){ return this; }
  public ClientMessage setDateStart(long date){ return this; }
  public ClientMessage setDateEnd(long date){ return this; }
  public ClientMessage setSubject(String subject){ return this; }
  public ClientMessage setBody(String body){ return this; }
  public ClientMessage setLatitude(double latitude) { return this; }
  public ClientMessage setLongitude(double longitude) { return this; }
  public ClientMessage setDistance(double distance) { return this; }
  public ClientMessage setPageSize(long pageSize) { return this; }
  public ClientMessage setPageOffset(long pageOffset) { return this; }
  public ClientMessage setId(long id){ this.id = id; return this; } // use for updates
  
  // TODO: add getter methods for all fields
  public String getAuthor(){ return author; }
  public Type getType(){ return type; }
  public String getBody(){ return body; }

}
