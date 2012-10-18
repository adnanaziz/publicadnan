import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.util.List;
import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

// import the collections/libraries you are using
	//TODOBEGIN(EE422C)

	//TODOEND(EE422C)
@Target(TYPE)
@Retention(RUNTIME)
//TODO(EE422C): update these to your name and eid
@interface Author {
  public String name() default  "John Snow" ;
  public String uteid() default  "js123" ;
}

@Author(name="Barack H Obama", uteid="HBO")
public class LogProcessor {
  // EE422C: define the appropriate collections here
	//TODOBEGIN(EE422C)

	//TODOEND(EE422C)
  // single argument constructor, which is the 
  // window size. seealso add method
  public LogProcessor(int windowSize) {
	//TODOBEGIN(EE422C)

	//TODOEND(EE422C)
  }
	//TODOBEGIN(EE422C)

	//TODOEND(EE422C)
  // add this URL and timestamp to the window 
  // remove all url-timestamp pairs from
  // the window for which the separation of timestamp
  // and maximum timestamp seen so far exceeds
  // the window size
  public void add( String url, long timeStamp ) {
	//TODOBEGIN(EE422C)

	//TODOEND(EE422C)
  }
	//TODOBEGIN(EE422C)

	//TODOEND(EE422C)
  public int getWindowSize() {
	return 0;
	//TODOBEGIN(EE422C)

	//TODOEND(EE422C)
  }
	//TODOBEGIN(EE422C)

	//TODOEND(EE422C)
  // returns the list of urls sorted by most common
  // to least common. Break ties lexicographically,
  // i.e., if abc.com  and xy.com both appear 10 times
  // in the window, put abc.com first
  public List<String> getOrderedUrlsInWindow() {
	return null;
	//TODOBEGIN(EE422C)

	//TODOEND(EE422C)
  }
	//TODOBEGIN(EE422C)

	//TODOEND(EE422C)
}
