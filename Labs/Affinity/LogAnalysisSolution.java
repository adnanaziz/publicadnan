public class LogAnalysis {

  final static int MAX_PERSONS = 1000;
  final static int MAX_PAGES = 5000;

  private String[] persons = new String[MAX_PERSONS];
  private String[] pages = new String[MAX_PAGES];
  private boolean[][] personToPage;
  private int numPersons = 0;
  private int numPages = 0;

  {
    personToPage = new boolean[MAX_PERSONS][];
    for ( int i = 0 ; i < MAX_PERSONS; i++ ) {
      personToPage[i] = new boolean[MAX_PAGES];
    }
  }

  int getPageId( String page ) {
    for ( int i = 0; i < numPages; i++ ) {
      if ( pages[i].equals(page) ) {
        return i;
      }
    }
    pages[numPages++] = page;
    return numPages;
  }

  int getPersonId( String person ) {
    for ( int i = 0; i < numPersons; i++ ) {
      if ( persons[i].equals(person) ) {
        return i;
      }
    }
    persons[numPersons++] = person;
    return numPersons;
  }

  /**
   * Take a string represention of a log file and return the pair
   * of files that have the highest affinity.
   *
   * @return pair of String names ordered by dictionary order, separated by a comma, 
   * e.g., articles/finance/gs-2013.html,reports/oil/ms-9/9/2013.pdf. You can
   * assume there's no commas in file names, and that the logFileString 
   * is valid.
   */
  public String highestAffinityPair(String logFileString) {
    String[] lines = logFileString.split("\n");
    for ( String line : lines ) {
      String[] fields = line.split(",");
      String page = fields[1];
      String person = fields[2];
      int pageId = getPageId(page);
      int personId = getPersonId(person);
      if ( personId >= MAX_PERSONS ) {
        System.out.println("Bad personId" + logFileString);
      }
      if ( pageId >= MAX_PAGES ) {
        System.out.println("Bad pageId" + logFileString);
      }
      personToPage[personId][pageId] = true;
    }

    System.out.println(numPages + "," + numPersons );
    int imax = -1;
    int jmax = -1;
    int countmax = -1;
    for (int i = 0 ; i < numPages; i++ ) {
      for (int j = 0 ; j < numPages; j++ ) {
        if ( i == j ) {
          continue;
        }
        int count = 0;
        for ( int k = 0; k < numPersons; k++ ) {
          if ( personToPage[k][i] == true && personToPage[k][j] == true ) {
            count++;
          }
        }
        System.out.println(i + "," + j + ":" + count );
        if (count > countmax) {
          countmax = count;
          imax = i;
          jmax = j;
        }
      }
    }
    System.out.println("Most common pair = " + pages[imax] + "," + pages[jmax]);
    if ( pages[imax].compareTo(pages[jmax]) < 0 ) {
      return pages[imax] + "," + pages[jmax];
    } else {
      return pages[jmax] + "," + pages[imax];
    }
  }

  static String s1 = "1,foo,adnan\n2,bar,john\n3,bar,adnan\n";

  public static void main(String[] args) {
    String r1 = new LogAnalysis().highestAffinityPair(s1);
    System.out.println("r1 = " + r1);
  }
}
