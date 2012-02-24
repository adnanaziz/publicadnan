public class OptionValue implements Comparable {
  double value;
  Option opt;

  public int compareTo( OptionValue ov ) {
    if ( value < ov.value ) {
      return -1;
    } else if ( value > ov.value ) {
      return 1;
    } else {
      return 0;
    }
  }
}
