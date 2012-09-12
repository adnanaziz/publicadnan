import java.util.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.text.DecimalFormat;
import com.google.common.base.CharMatcher;
import com.google.common.base.Joiner;

public class Option {

  public final double stockPrice;
  public final String quoteString;
  public final double change;
  public final double strike;
  public final long expiryDate;
  public final double bid;
  public final double ask;
  public final double last;
  public final int vol;
  public final int open;
  public final boolean isCall;
  public final boolean isPut;

  public static class Builder {

    private String quoteString;
    private double stockPrice;
    private double change;
    private double strike;
    private long expiryDate;
    private double bid;
    private double ask;
    private double last;
    private int vol;
    private int open;
    private boolean isCall;
    private boolean isPut;

    public Builder(String quoteString, double stockPrice ) {
      this.quoteString = quoteString;
      this.stockPrice = stockPrice;

      this.expiryDate = computeExpiryDate( quoteString );

      this.isCall =  isCall( quoteString );
      this.isPut = !this.isCall;
      this.strike = -1.0;


      this.change = -1.0;
      this.bid = -1.0;
      this.ask = -1.0;
      this.last = -1.0;
      this.vol = -1;
      this.open = -1;
    }

    public Builder strike( String strike ) { this.strike = new Double( strike ); return this; }
    public Builder strike( double strike ) { this.strike = strike; return this; }

    public Builder change( String change ) { this.change = new Double( change ); return this; }
    public Builder change( double change ) { this.change = change; return this; }

    public Builder bid( String bid ) { this.bid = new Double( bid ); return this; }
    public Builder bid( double bid ) { this.bid = bid; return this; }

    public Builder ask( String ask ) { this.ask = new Double( ask ); return this; }
    public Builder ask( double ask ) { this.ask = ask; return this; }

    public Builder last( String last ) { this.last = new Double( last ); return this; }
    public Builder last( double last ) { this.last = last; return this; }

    public Builder vol( String vol ) { 
      if ( !vol.equals( "N/A" ) ) {
        this.vol = new Integer( vol.replace(",","") ); 
      }
      else {
        this.vol = -1;
      }
      return this; 
    }
    public Builder vol( int vol ) { this.vol = vol; return this; }
    public Builder open( String open ) { 
      if ( !open.equals( "N/A" ) ) {
        this.open = new Integer( open.replace(",","") ); 
      }
      else {
        this.open = -1;
      }
      return this; 
    }
    public Builder open( int open ) { this.open = open; return this; }
 
    public Option build() {
      return new Option( this );
    }
  }
 
  private Option( Builder builder ) {
    this.stockPrice = builder.stockPrice;
    this.quoteString = builder.quoteString;
    this.change = builder.change;
    this.strike = builder.strike;
    this.expiryDate = builder.expiryDate;
    this.bid = builder.bid;
    this.ask = builder.ask;
    this.last = builder.last;
    this.vol = builder.vol;
    this.open = builder.open;
    this.isCall = builder.isCall;
    this.isPut = builder.isPut;
  }

  public static final DecimalFormat df = new DecimalFormat("#.##");

  public static String fields() {
    Joiner joiner = Joiner.on(",").skipNulls();
    return joiner.join("Quote String", 
                "Underlying Price", "Days to Expiry", "Change", 
                "Strike", "Bid", "Ask", "Last", "Volume", "Open", "Type" );
  }

  public double cost() {
    return 0.5 * ( bid + ask );
  }

  public String toString() {
    Date today = new Date();
    long millisToExpire = expiryDate - today.getTime();
    long daysToExpire = millisToExpire / ( 1000 * 60 * 60 * 24 );
    String daysToExpireString = "expiry:" + new Double( daysToExpire ).toString();
    Joiner joiner = Joiner.on(",").skipNulls();

    return joiner.join(quoteString, 
    			"stockprice:" + df.format( stockPrice ),
          daysToExpireString,
    			"change:" + df.format( change ),
    			"strike:" + df.format( strike ),
    			"bid:" + df.format( bid ),
    			"ask:" + df.format( ask ),
    			"last:" + df.format( last ),
    			"vol:" + new Integer( vol ),
    			"open:"  + new Integer( open ),
			(isCall ? "call" : "put") );

  }

  public static boolean isCall( String quoteString ) {
    // SPY120317P00167000
    String reverse = new StringBuffer(quoteString).reverse().toString();
    String tmp = CharMatcher.DIGIT.removeFrom(reverse);
    if ( tmp.charAt(0) == 'C' ) {
      return true;
    } else if ( tmp.charAt(0) == 'P' ) {
      return false;
    }
    System.out.println("Error, invalid option type");
    return false;
  }

  public static long computeExpiryDate( String quoteString ) {
    String tmp = CharMatcher.DIGIT.retainFrom(quoteString);
    String year = tmp.substring(0,2);
    String month = tmp.substring(2,4);
    String date = tmp.substring(4,6);

    Calendar c = new GregorianCalendar();
    c.set( 2000 + new Integer( year ), new Integer( month ) - 1, new Integer( date ) );
    return c.getTimeInMillis();
  }

  public static void main(String[] args) {
    // isCall("SPY120317P00167000");
    long expTime = computeExpiryDate("SPY120317P00167000");
    Calendar c = new GregorianCalendar( );
    c.setTimeInMillis( expTime );
    System.out.println("Date is " + c.toString() );
  }
}
