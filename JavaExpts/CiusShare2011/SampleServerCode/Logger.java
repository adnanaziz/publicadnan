public class Logger {
  private boolean on = true;
  public void turnOn() { on = true; }
  public void turnOff() { on = false; }
  public enum Level { 
    Detailed(0), Note(1), Warn(2), Alert(3), Error(4); 
    private final int val;
    Level(int val) {
      this.val = val;
    }
    public int check( Level b ) {
      if ( this.val < b.val ) {
        return 1;
      } else if ( this.val == b.val ) {
	 return 0;
      }
      return -1;
    }
  }

  private static Level currentLevel = Level.Alert;
  public void setLevel( Logger.Level level ) { currentLevel = level; }
  public Level getLevel( ) { return currentLevel; }

  public String label;

  public Logger( String s ) {
    label = s;
  }

  public Logger( String s, Level level ) {
    label = s;
    currentLevel = level;
  }

  public void warn( String s ) {
    if ( on && currentLevel.check( Level.Warn) >= 0 ) {
      System.out.println( label + " [warn]:" + s );
    }
  }

  public void note( String s ) {
    if ( on && currentLevel.check( Level.Note) >= 0 ) {
      System.out.println( label + " [note]:" + s );
    }
  }

  public void alert( String s ) {
    if ( on && currentLevel.check( Level.Alert) >= 0 ) {
      System.out.println( label + " [alert]:" + s );
    }
  }
}
