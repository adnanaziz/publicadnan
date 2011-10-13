// Example 88 from page 63 of Java Precisely second edition (The MIT Press 2005)
// Author: Peter Sestoft (sestoft@itu.dk)


class Example88 {
  public static void main(String[] args) {
    if (args.length != 3) 
      System.out.println("Usage: Example88 yyyy mm dd\n");
    else {
      try { 
        MyDate d = new MyDate(Integer.parseInt(args[0]), 
                              Month.toMonth(Integer.parseInt(args[1])),
                              Integer.parseInt(args[2]));
        System.out.println(d + " is " + d.weekday() + " in week " + d.weekNumber());
      } catch (Exception e) {
        System.out.println(e);
      }
    }    
  }
}

enum Day {
  Mon, Tue, Wed, Thu, Fri, Sat, Sun;

  private final static Day[] day = values();        // Cache the array

  public static Day toDay(int n) { 
    return day[n];    
  }

  public int toInt() { 
    return ordinal();    
  }
}

enum Month {
  Jan(31), Feb(28), Mar(31), Apr(30), May(31), Jun(30), 
  Jul(31), Aug(31), Sep(30), Oct(31), Nov(30), Dec(31);

  private final int days;

  private Month(int days) { 
    this.days = days; 
  }

  private final static Month[] month = values();    // Cache the array

  public int days(int year) { 
    return this == Feb && MyDate.leapYear(year) ? 29 : days;
  }
  
  public static Month toMonth(int n) { 
    return month[n-1];    
  }

  public int toInt() { 
    return ordinal()+1;    
  }

  public Month succ() { 
    return toMonth(toInt()+1);
  }
}

class MyDate {
  final int yy /* 0-9999 */, dd /* 1-31 */;
  final Month mm;

  public MyDate(int yy, Month mm, int dd) throws Exception {
    if (ok(yy, mm, dd)) {
      this.yy = yy; this.mm = mm; this.dd = dd;
    } else
      throw new Exception("Illegal date ("+yy+","+mm+","+dd+")");
  }

  public static boolean leapYear(int y) {
    return y % 4 == 0 && y % 100 != 0 || y % 400 == 0;
  }

  public boolean leapYear() {
    return leapYear(yy);
  }

  // An alternative to method days(int) in enum type Month:

  public static int monthDays(int y, Month m) {
    switch (m) {
    case Apr: case Jun: case Sep: case Nov:
      return 30;
    case Feb: 
      return leapYear(y) ? 29 : 28;
    default:
      return 31;
    }
  }

  public int monthDays() {
    return mm.days(yy);
  }

  public static int yearDays(int y) {
    return leapYear(y) ? 366 : 365;
  }

  public int yearDays() {
    return yearDays(yy);
  }

  public static boolean ok(int y, Month m, int d) {
    return 1 <= d && d <= m.days(y);
  }

  // ISO week numbers: the week is from Monday to Sunday.  Week 1 is
  // the first week having a Thursday.

  public static int weekNumber(int y, Month m, int d) {
    int yday = dayInYear(y, m, d);
    int wday = weekday(y, m, d).toInt();
    int week = (yday - wday + 10)/7;
    if (week == 0) 
      return (yday + yearDays(y-1) - wday + 10)/7;
    else
      return week;
  }

  public int weekNumber() {
    return weekNumber(yy, mm, dd);
  }

  // Translated from Emacs's calendar.el:
  // Reingold: Number of the day within the year: 

  public static int dayInYear(int y, Month m, int d) {
    int monthno = m.toInt() - 1;
    int monthadjust = 
      monthno > 1 ? (27 + 4 * monthno) / 10 - (leapYear(y) ? 1 : 0) : 0;
    return d - 1 + 31 * monthno - monthadjust;
  }

  public int dayInYear() {
    return dayInYear(yy, mm, dd);
  }

  // Reingold: Find the number of days elapsed from the (imagined)
  // Gregorian date Sunday, December 31, 1 BC to the given date.
        
  public static int toDaynumber(int y, Month m, int d) {
    int prioryears = y - 1;
    return 
      dayInYear(y, m, d) 
      + 1 + 365 * prioryears
      + prioryears / 4 - prioryears / 100 + prioryears / 400;
  }

  public int toDaynumber() {  
    return toDaynumber(yy, mm, dd);
  }

  // Reingold et al: from absolute day number to year, month, date: 

  public static MyDate fromDaynumber(int n) { 
    try { 
      int d0 = n - 1;
      int n400 = d0 / 146097;
      int d1 = d0 % 146097;
      int n100 = d1 / 36524;
      int d2 = d1 % 36524;
      int n4 = d2 / 1461;
      int d3 = d2 % 1461;
      int n1 = d3 / 365;
      int d = 1 + d3 % 365;
      int y = 400 * n400 + 100 * n100 + n4 * 4 + n1 + 1;
      if (n100 == 4 || n1 == 4) {
        return new MyDate(y-1, Month.Dec, 31);
      } else {
        Month m = Month.Jan;
        int mdays;
        while ((mdays = m.days(y)) < d) {
          d -= mdays;
          m = m.succ();
        }
        return new MyDate(y, m, d);
      }
    } catch (Exception e) {
      throw new RuntimeException("MyDate.fromDaynumber: impossible");
    }
  }
  
  // Day of the week: 0=Mon, 1=Tue, ..., 6=Sun

  public static Day weekday(int y, Month m, int d) {
    return Day.toDay((toDaynumber(y, m, d)+6) % 7);
  }

  public Day weekday() {
    return weekday(yy, mm, dd);
  }

  public String toString() { // ISO format such as 2004-07-11
    return String.format("%4d-%02d-%02d", yy, mm.toInt(), dd);
  }
}

