public class OptionValue implements Comparable {
  Option opt;
  double value;

  public int compareTo( Object ovObject ) {
    OptionValue ov = (OptionValue) ovObject;
    if ( value < ov.value ) {
      return 1;
    } else if ( value > ov.value ) {
      return -1;
    } else {
      return 0;
    }
  }

  public OptionValue( Option op, double value ) {
    this.opt = op;
    this.value = value;
  }

  public String toString() {
    return ( Option.df.format( value ) + "," + opt.toString() );
  }
}
