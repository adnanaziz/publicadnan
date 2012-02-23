public class Option {

  private final String quoteString;
  private final double stockPrice;
  private final double strike;
  private final double bid;
  private final double ask;
  private final double last;
  private final int vol;
  private final int open;

  public static class Builder {

    private String quoteString;
    private double stockPrice;
    private double strike;
    private double bid;
    private double ask;
    private double last;
    private int vol;
    private int open;

    public Builder(String quoteString, double stockPrice ) {
      this.quoteString = quoteString;
      this.stockPrice = stockPrice;
      this.strike = -1.0;
      this.bid = -1.0;
      this.ask = -1.0;
      this.last = -1.0;
      this.vol = -1;
      this.open = -1;
    }

    public Builder strike( double strike ) { this.strike = strike; return this; }
    public Builder bid( double bid ) { this.bid = bid; return this; }
    public Builder ask( double ask ) { this.ask = ask; return this; }
    public Builder last( double last ) { this.last = last; return this; }
    public Builder vol( int vol ) { this.vol = vol; return this; }
    public Builder strike( int open ) { this.open = open; return this; }
 
    public Option build() {
      return new Option( this );
    }
  }
 
  private Option( Builder builder ) {
    this.quoteString = builder.quoteString;
    this.stockPrice = builder.stockPrice;
    this.strike = builder.strike;
    this.bid = builder.bid;
    this.ask = builder.ask;
    this.last = builder.last;
    this.vol = builder.vol;
    this.open = builder.open;
  }
}
