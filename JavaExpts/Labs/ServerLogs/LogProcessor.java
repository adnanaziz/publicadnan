import java.util.Collections;
import java.util.Comparator;
import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;
import java.util.Map;
import java.util.TreeMap;
import java.lang.ClassCastException;


import com.google.common.collect.Multiset;
import com.google.common.collect.TreeMultiset;

class Record implements Comparable {
  private String url;
  private long time;
  Record( String url, long time ) {
    this.url = url;
    this.time = time;
  }
  public String getUrl() { return url; }
  public long getTime() { return time; }
  @Override 
  public String toString() {
    return url + ":" + time;
  }
  public int compareTo( Object o2 ) {
    Record r1 = (Record) this;
    Record r2 = (Record) o2;
    long tmp = r2.getTime() - r1.getTime();
    if ( tmp < 0 ) {
      return 1;
    } else if ( tmp > 0 ) {
      return -1;
    } else {
      return r1.getUrl().compareTo( r2.getUrl() );
    }
  }
  @Override 
  public boolean equals( Object o2 ) {
    Record r1 = (Record) this;
    Record r2 = (Record) o2;
    return (r1.getUrl().equals( r2.getUrl() ) && r1.getTime() == r2.getTime() );
  }
}
 

class Frequency implements Comparable {

  private long frequency;
  private String url;

  @Override 
  public String toString() {
    return url + ":" + frequency;
  }

  Frequency( String url, long frequency ) {
    this.url = url;
    this.frequency = frequency;
  }

  public String getUrl() { return url; }
  public long getFrequency() { return frequency; }
  public void incrementFreq() { frequency++; return; }
  public void decrementFreq() { frequency--; return; }

  public int compareTo( Object o ) {
    if ( o.getClass() != Frequency.class ) {
      throw new ClassCastException("cannot compare " + o.getClass());
    }
    Frequency of = (Frequency) o;
    //long tmp = this.getFrequency() - of.getFrequency();
    //if ( tmp < 0 ) {
    //  return -1;
    //} else if ( tmp > 0 ) {
    //  return 1;
    //} else {
      return this.getUrl().compareTo(of.getUrl());
    //}
  }

  //@Override
  //public boolean equals( Object o ) {
  //  if ( o.getClass() != Frequency.class ) {
  //    throw new ClassCastException("cannot do equals on " + o.getClass());
  //  }
  //  Frequency of = (Frequency) o;
  //  return (this.getUrl().equals(of.getUrl())) && (this.getFrequency() == of.getFrequency());
  //}
}

public class LogProcessor {
  Multiset<Record> timeOrderedRecords;
  Set<Frequency> frequencyUrlSet;
  Map<String,Frequency> urlToFrequencyEntry;

  final int WINDOWSIZE;
  int getWindowSize() { return WINDOWSIZE; }
  long newestTime = 0;

  LogProcessor(int windowSize) {
    this.WINDOWSIZE = windowSize;
    timeOrderedRecords = TreeMultiset.create(); 
    frequencyUrlSet = new TreeSet<Frequency>();
    urlToFrequencyEntry = new TreeMap<String,Frequency>();
  }

  void add(Record r) {
    timeOrderedRecords.add( r );
    if ( newestTime < r.getTime() ) {
      newestTime = r.getTime();
    }
    Frequency f = urlToFrequencyEntry.get( r.getUrl());
    if ( f == null ) {
      f = new Frequency( r.getUrl(), 1 );
      urlToFrequencyEntry.put( r.getUrl(),  f );
      frequencyUrlSet.add( f );
    } else {
      f.incrementFreq();
      // assert(false);
    }
    Iterator<Record> iter = timeOrderedRecords.iterator();
    List<Record> delList = new ArrayList<Record>();
    while ( iter.hasNext() ) {
      Record tmp = iter.next();
      if ( newestTime - tmp.getTime() <= WINDOWSIZE ) {
        break;
      }
      delList.add( tmp );
    }
    this.removeRecords( delList );
    // print();
  }

  List<Frequency> getWindow() {
    long startTime = -1;
    Set<String> processedUrls = new TreeSet<String>();
    List<Frequency> windowListRecords = new ArrayList<Frequency>();
    for (Record r : timeOrderedRecords) {
      if ( !processedUrls.contains(r.getUrl()) ) {
        Frequency f = urlToFrequencyEntry.get(r.getUrl());
        windowListRecords.add( f );
        processedUrls.add( r.getUrl() );
      }
    }
    Collections.sort( windowListRecords, new Comparator() {
      @Override 
      public int compare(Object o1, Object o2) {
        Frequency r1 = (Frequency) o1;
        Frequency r2 = (Frequency) o2;
        long tmp = r1.getFrequency() - r2.getFrequency();
        int tmpCast = (int) tmp;
        return tmpCast;
      }
    }
    );
    return windowListRecords;
  }

  void removeRecords( List<Record> removeList ) {
    for (Record delRecord : removeList ) {
      timeOrderedRecords.remove( delRecord );
      String url = delRecord.getUrl();
      Frequency f = urlToFrequencyEntry.get(url);
      frequencyUrlSet.remove( f );
      f.decrementFreq();
      if ( f.getFrequency() == 0 ) {
        urlToFrequencyEntry.remove( f.getUrl() );
      } else {
        frequencyUrlSet.add( f );
      }
    }
  }

  public void printWindow() {
     List<Frequency> window = this.getWindow();
     System.out.println("---begin urls in current window sorted by increasing frequency");
     for ( Frequency f : window ) 
       System.out.print(f + "," );
     System.out.println("\n---end");
  }

  @Override
  public String toString() {
    // Set<Record> timeOrderedRecords;
    // Set<Frequency> frequencyUrlSet;
    // Map<String,Frequency> urlToFrequencyEntry;
    StringBuilder sb = new StringBuilder();
    sb.append("window size = " + this.WINDOWSIZE );
    sb.append("\n|timeOrderedRecords| = " + timeOrderedRecords.size() + "\n" );
    for ( Record r : timeOrderedRecords )
      sb.append( r + ",");
    sb.append("\n|frequencyUrlSet| = " + frequencyUrlSet.size() + "\n" );
    for ( Frequency f : frequencyUrlSet )
      sb.append( f + ",");
    sb.append("\n|urlToFrequencyEntry| = " + urlToFrequencyEntry.size() + "\n" );
    Set<String> keySet = urlToFrequencyEntry.keySet();
    for ( String url : keySet )
      sb.append( url + ":" + urlToFrequencyEntry.get( url ) + "," );
    sb.append("\n");
    return sb.toString();
  }

  public void print() {
    System.out.println("Current LogProcessor is " + this.toString() );
  }

  private static String[] sites = {
    "google.com", "google.com", "yahoo.com", "facebook.com", 
    "cnn.com", "cricinfo.org", "rediff.com", "abcnews.com"
  };

  private static Random r = new Random();
  private static long timeStamp = 0;

  private static Record randomRecord() {
    Record result = new Record( sites[ r.nextInt( sites.length )], timeStamp );
    timeStamp += r.nextInt(2);
    return result;
  }

  public static void test2() {
    LogProcessor lp = new LogProcessor(1000);
    for ( int i = 0; i < 100000000; i++ ) {
       lp.add( randomRecord() );
       //if ( i % 100 == 0 ) {
         // lp.printWindow();
         // lp.print();
       //}
    }
    lp.print();
    lp.printWindow();
  }

  public static void main(String [] args) {
    test2();
  }

  public static void test3() {
     LogProcessor lp = new LogProcessor(5);
     lp.add( new Record( "google.com", 0 ) );
     lp.add( new Record( "yahoo.com", 1 ) );
     lp.add( new Record( "google.com", 2 ) );
     lp.add( new Record( "google.com", 10 ) );
     lp.add( new Record( "yahoo.com", 11 ) );
     // lp.printWindow();
     lp.add( new Record( "google.com", 100 ) );
     lp.add( new Record( "yahoo.com", 101 ) );
     lp.add( new Record( "google.com", 100 ) );
     lp.add( new Record( "google.com", 100 ) );
     lp.add( new Record( "google.com", 100 ) );
     lp.add( new Record( "google.com", 102 ) );
     lp.add( new Record( "yahoo.com", 200 ));
     lp.print();
  }

  public static void test1() {
     LogProcessor lp = new LogProcessor(1000);
     lp.add( new Record( "google.com", 0 ) );
     lp.add( new Record( "yahoo.com", 1 ) );
     // lp.printWindow();
     lp.add( new Record( "google.com", 0 ) );
     lp.add( new Record( "yahoo.com", 7 ) );
     lp.add( new Record( "abc.com", 9 ) );
     lp.add( new Record( "cbs.com", 8 ) );
      //lp.print();
     // lp.printWindow();
     // lp.printWindow();
     lp.add( new Record( "cricket.com", 20 ) );
     lp.print();
     lp.printWindow();
     //lp.print();
  }
}
