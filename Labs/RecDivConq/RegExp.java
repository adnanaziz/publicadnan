import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.util.List;
import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

@Target(TYPE)
@Retention(RUNTIME)
//TODO(EE422C): update these to your name and eid
@interface Author {
  public String name() default  "John Snow" ;
  public String uteid() default  "js123" ;
}

@Author(name="Sachin Tendulkar", uteid="st100")
public class RegExp {
  //TODO(EE422C): implement this function
  public static boolean match(String regex, String s ) {
    return true;
  }
}
