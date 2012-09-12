public class OptionPair implements Comparable {
  public Option o1;
  public Option o2;
  public double value;

  public OptionPair( Option o1, Option o2 ) {
    this.o1 = o1;
    this.o2 = o2;
    // if ( o1 == o2 || ( o1.strike > o2.strike ) || ( o1.strike < o1.stockPrice ) || ( o2.strike < o2.stockPrice ) ) {
    // if ( ( o1.strike < o1.stockPrice ) || ( o2.strike < o2.stockPrice ) ) {
    //   value = Double.MIN_VALUE;
    // } else {
    this.value = (o1.cost() - o2.cost()) 
                      - 0.25 * ((o2.strike - o1.strike) * (o2.strike - o1.strike))
                            / (o2.strike - o1.stockPrice);
    // }
  }

  public String toString() {
    String result = (new Double(value)).toString() + ", o1=" + o1.toString() + ", o2=" + o2.toString();
    return result;
  }

  public int compareTo( Object opObject ) {
    OptionPair op = (OptionPair) opObject;
    if ( value < op.value ) {
      return -1;
    } else if ( value > op.value ) {
      return 1;
    } else {
      return 0;
    }
  }

}

