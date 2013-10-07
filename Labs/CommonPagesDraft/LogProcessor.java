import java.lang.StringBuffer;
import java.util.Iterator;
import java.util.Comparator;
import java.util.Queue;
import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Collections;
import static java.lang.Math.max;

interface LogProcessor {
  public void add(String url, int time);
  public List<String> getOrderedUrlsInWindow(int K);
}

class LogItem {
  private String url;
  private int time;
  public LogItem(String url, int time) {
    this.url = url;
    this.time = time;
  }
  public String getUrl() {
    return url;
  }
  public int getTime() {
    return time;
  }
  public int compareTo(LogItem li) {
    return time - li.getTime();
  }
  @Override
  public String toString() {
    return url + ", time = " + time;
  }
}

class LogProcessorSlow implements LogProcessor {
  private int W;
  private List<LogItem> liQueue;

  public LogProcessorSlow(int W) {
    this.W = W;
    this.liQueue = new LinkedList<LogItem>();
  }

  @Override
  public String toString() {
    StringBuffer result = new StringBuffer("window size = " + W + "\n");
    for ( LogItem li : liQueue ) {
      result.append( li.toString() + "\n" );
    }
    return result.toString();
  }

  public void add(String url, int time) {
    // System.out.println("before add:" + this.toString() );
    LogItem anItem = new LogItem(url, time);
    liQueue.add( anItem );
    int mostRecentTime = -1;
    for ( LogItem li : liQueue ) {
      if (li.getTime() > mostRecentTime ) {
        mostRecentTime = li.getTime();
      }
    }
    Iterator<LogItem> liIter = liQueue.iterator();
    while ( liIter.hasNext() ) {
      if (mostRecentTime - liIter.next().getTime() > W ) { 
        liIter.remove();
      }
    }
    // System.out.println("after add:" + this.toString() );
  }

  public List<String> getOrderedUrlsInWindow(int K) {
    final Map<String,Integer> urlCountMap = new HashMap<String,Integer>();
    for ( LogItem li : liQueue ) {
      if ( urlCountMap.containsKey(li.getUrl())) {
        int count = urlCountMap.get(li.getUrl());
        urlCountMap.put(li.getUrl(), count + 1);
      } else {
        urlCountMap.put(li.getUrl(), 1);
      }
    }
    List<String> countArray = new ArrayList<String>(urlCountMap.keySet());
    Collections.sort( countArray, 
      new Comparator<String>() {
        @Override
        public int compare(String s0, String s1) {
          int tmp = urlCountMap.get( s0 ) - urlCountMap.get( s1 );
          if ( tmp != 0 ) {
            return tmp;
          } else {
            // want lexicographically first strings to appear first when tied
            return -s0.compareTo(s1);
          }
        }
      });
    List<String> result = new ArrayList<String>();
    for ( int i = countArray.size()  - 1; i >= max(countArray.size() - K, 0 ); i-- ) {
      result.add( countArray.get(i) + ":" + urlCountMap.get( countArray.get(i) ) );
    }
    return result;
  }

}
