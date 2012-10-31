import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import com.google.gson.*;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

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
  private long time;
  private String body;

  ClientMessage() {
    // no argument constructor used by GSON
  }

  // serialize this to a json string
  public String toJson() {
    String result = null;
	//TODOBEGIN(EE422C)

	//TODOEND(EE422C)
    return result;
  }

  // create a ClientMessage object from the Json string
  public static ClientMessage fromJson( String s ) {
    ClientMessage result = null;
	//TODOBEGIN(EE422C)

	//TODOEND(EE422C)
    return result;
  }

  // TODO: all setter methods should return this so that calls can be chained
  public ClientMessage setType(Type type){ this.type = type; return this; }
  public ClientMessage setAuthor(String author){ this.author = author; return this; }
  public ClientMessage setTime(long time){ this.time = time; return this; }

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
