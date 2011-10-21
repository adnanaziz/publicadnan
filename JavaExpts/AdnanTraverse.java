import java.util.HashMap;
import java.util.TreeMap;
import java.util.Map;
import java.util.Collection;
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
    
    HashMap<String, Integer> cityMap = new HashMap<String, Integer>();
    
    cityMap.put( "Denver", 2);
    cityMap.put( "Chicago", 6);
    cityMap.put( "Austin", 19);
    cityMap.put( "San Antonio", 2);
    cityMap.put( "Milwakee", 6);
    cityMap.put( "New York", 7);
    
    ValueComparator vc = new ValueComparator (cityMap);
    Map<String,Integer> sortedCityMap = new TreeMap<String, Integer>(vc);
    
    sortedCityMap.putAll(cityMap);
    System.out.println("sortedCityMap = " + sortedCityMap.toString());
    traverse(sortedCityMap);
    if ( sortedCityMap.containsKey("Austin") ) {
      System.out.println("sortedCityMap contains Austin");
    } else {
      System.out.println("sortedCityMap does not contain Austin");
    }
  }

  static void traverse(Map<String,Integer> coll) {
    for (String key: coll.keySet()) 
      System.out.print(key + ":" + coll.get(key) + " ");
    System.out.println();
  }
}
