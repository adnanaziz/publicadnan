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
}

class LogProcessorSlow implements LogProcessor {
  private int W;
  private List<LogItem> liQueue;

  public LogProcessorSlow(int W) {
    this.W = W;
    this.liQueue = new LinkedList<LogItem>();
  }

  public void add(String url, int time) {
    LogItem anItem = new LogItem(url, time);
    liQueue.add( anItem );
    int mostRecentTime = -1;
    for ( LogItem li : liQueue ) {
      if (li.getTime() > mostRecentTime ) {
        mostRecentTime = li.getTime();
      }
    }
    for ( LogItem li : liQueue ) {
      if (mostRecentTime - li.getTime() > W ) { 
        liQueue.remove(li);
      }
    }
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
          return urlCountMap.get( s0 ) - urlCountMap.get( s1 );
        }
      });
    List<String> result = new ArrayList<String>();
    for ( int i = countArray.size()  - 1; i >= max(countArray.size() - W, 0 ); i-- ) {
      result.add( countArray.get(i) + ":" + urlCountMap.get( countArray.get(i) ) );
    }
    return result;
  }

  public static void main(String[] args) {
    LogProcessor lp = new LogProcessorSlow(3);
    lp.add( "foo", 1 );
    lp.add( "bar", 2 );
    lp.add( "foo", 3 );
    lp.add( "widget", 4 );
    List<String> r0 = lp.getOrderedUrlsInWindow(2);
    for ( String s : r0 ) {
      System.out.println(s);
    }
  }

}
