import com.google.common.base.Strings;
import com.google.common.base.Splitter;
import com.google.common.base.Joiner;
import com.google.common.base.Function;
import com.google.common.collect.Iterables;

import java.util.Arrays;

// Goal - show how to use one line of guava to transform 
// ",a,,b," to [null, "a", null, "b", null]

public class Split {

  private final static String s = ",a,,b,";

  public static void main(String[] args) {

    // first, we show in multiple steps:

    // split to iterable
    Iterable<String> split = Splitter.on(',').split(s);

    // transform iterable, replacing "" by null
    Function<String,String> F = new Function<String,String>() { 
      public String apply(String x) { return x.equals("") ? null : x; }
    };
    Iterable<String> splitWithNull = Iterables.transform( split,  F);

    // convert iterable to regular array
    String[] splitArray = Iterables.toArray(splitWithNull, String.class);

    // now we show in one line 
    String[] splitArrayAlt = Iterables.toArray( 
        Iterables.transform(
          Splitter.on(',').split(s), 
          // Anonymous class in place of F above, does the same thing
          new Function<String,String>() { 
            public String apply(String x) { 
              // originally:
              // return x.equals("") ? null : x; 
              // better approach, courtesy zirui wang
              return Strings.emptyToNull(x); 
            } 
          } ), 
        String.class );

    // confirm result visually
    System.out.println(Joiner.on(",").useForNull("null").join(splitArrayAlt));

    // confirm result programmatically
    assert( Arrays.equals(splitArrayAlt, new String[]{null, "a", null, "b", null}));

  }
}
