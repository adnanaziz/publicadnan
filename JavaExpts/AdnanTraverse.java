import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.Map;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

class ValueComparator implements Comparator<String> {

  Map <String, Integer> base;

  public ValueComparator (HashMap<String, Integer> b) {
    base = (Map<String,Integer>) b.clone();
  }

  public int compare (String a, String b) {
    if ( (Integer) base.get(a) < (Integer)base.get(b)) {
      return 1;
    } else if ((Integer)base.get(a) > (Integer)base.get(b)) {
      return -1;
    } else {
      return a.compareTo(b);
    }
  }
}

public class AdnanTraverse {

  public static void main(String[] args) {
  
    // Sort Hash Map based on values...descending order
    
    final HashMap<String, Integer> cityMap = new HashMap<String, Integer>();
    
    cityMap.put( "Denver", 2);
    cityMap.put( "Chicago", 6);
    cityMap.put( "Austin", 19);
    cityMap.put( "San Antonio", 2);
    cityMap.put( "Milwakee", 6);
    cityMap.put( "New York", 7);
   
    // approach 1
    ValueComparator vc = new ValueComparator (cityMap);
    Map<String,Integer> sortedCityMap = new TreeMap<String, Integer>(vc);
    sortedCityMap.putAll(cityMap);
    System.out.println("Approach 1:");
    traverse(sortedCityMap);
   
    // better way
    List<String> cities = new ArrayList<String>(cityMap.keySet());
    Collections.sort(cities, 
      new Comparator<String>() {
        public int compare(String c1, String c2) {
      // anonymous class for sorting, can refer to enclosing objects variables,
      // cityMap in this case, but cityMap has to be declared to be final 
      // for this to compile
          return cityMap.get(c2) - cityMap.get(c1);
        }
      });
    System.out.println("Approach 2:");
    for ( String c : cities ) 
      System.out.print(c + ":" + cityMap.get(c) + " ");
    System.out.println();




  }

  static void traverse(Map<String,Integer> coll) {
    for (String key: coll.keySet()) 
      System.out.print(key + ":" + coll.get(key) + " ");
    System.out.println();
  }
}
