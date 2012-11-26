package utweeter;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.google.common.base.Joiner;
import com.google.gson.Gson;

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
  private String subject;
  private String body;
  private long id;
  private long time;
  private double latitude;
  private double longitude;
  //The date format is "MM/DD/YYYY", say "1/1/2000"
private String date;
  @Override 
  public String toString() {
    Joiner joiner = Joiner.on(" : ").useForNull("null");
    return joiner.join( type.toString(), author, subject, body, String.valueOf(id), String.valueOf(time), 
                         String.valueOf(latitude), String.valueOf(longitude) );
  }

  ClientMessage() {
    // no argument constructor used by GSON
  }

  // serialize this to a json string
  public String toJson() {
    String result = null;
	//TODOBEGIN(EE422C)
    result = new Gson().toJson( this );
	//TODOEND(EE422C)
    return result;
  }

  // create a ClientMessage object from the Json string
  public static ClientMessage fromJson( String s ) {
    ClientMessage result = null;
	//TODOBEGIN(EE422C)
    result = new Gson().fromJson( s, ClientMessage.class );
	//TODOEND(EE422C)
    return result;
  }

  // TODO: all setter methods should return this so that calls can be chained
  public ClientMessage setType(Type type){ this.type = type; return this; }
  public ClientMessage setAuthor(String author){ this.author = author; return this; }
  public ClientMessage setSubject(String author){ this.subject = subject; return this; }
  public ClientMessage setBody(String author){ this.body = body; return this; }
  public ClientMessage setTime(long time){ this.time = time; return this; }

  public ClientMessage setDate(String date){ this.date = date;return this; }
  public ClientMessage setDateStart(String date){ return this; }
  public ClientMessage setDateEnd(String date){ return this; }
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
  public String getDate() {return date;}

}
