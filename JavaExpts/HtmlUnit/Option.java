import com.google.common.base.CharMatcher;

public class Option {

  public final double stockPrice;
  public final String quoteString;
  public final double change;
  public final double strike;
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
    public Builder change( String change ) { this.change = new Double( change ); return this; }
    public Builder bid( String bid ) { this.bid = new Double( bid ); return this; }
    public Builder ask( String ask ) { this.ask = new Double( ask ); return this; }
    public Builder last( String last ) { this.last = new Double( last ); return this; }
    public Builder vol( String vol ) { 
      if ( !vol.equals( "N/A" ) ) {
        this.vol = new Integer( vol.replace(",","") ); 
      }
      else {
        this.vol = -1;
      }
      return this; 
    }
    public Builder open( String open ) { 
      if ( !open.equals( "N/A" ) ) {
        this.open = new Integer( open.replace(",","") ); 
      }
      else {
        this.open = -1;
      }
      return this; 
    }
 
    public Option build() {
      return new Option( this );
    }
  }
 
  private Option( Builder builder ) {
    this.stockPrice = builder.stockPrice;
    this.quoteString = builder.quoteString;
    this.change = builder.change;
    this.strike = builder.strike;
    this.bid = builder.bid;
    this.ask = builder.ask;
    this.last = builder.last;
    this.vol = builder.vol;
    this.open = builder.open;
    this.isCall = builder.isCall;
    this.isPut = builder.isPut;
  }

  public String toString() {
    return quoteString;
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

  public static void main(String[] args) {
    isCall("SPY120317P00167000");
  }
}
