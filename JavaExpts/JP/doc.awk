BEGIN {
  css = "<style type=\"text/css\" media=\"screen\"> body" "{" "  line-height: 1.6em;" "}" "" "#hor-minimalist-a" "{" "  font-family: "Lucida Sans Unicode", "Lucida Grande", Sans-Serif;" "  font-size: 18px;" "  background: #fff;" "  margin: 45px;" "  width: 1480px;" "  border-collapse: collapse;" "  text-align: left;" "}" "#hor-minimalist-a th" "{" "  font-size: 18px;" "  font-weight: normal;" "  color: black;" "  padding: 10px 8px;" "  border-bottom: 2px solid #6678b1;" "}" "#hor-minimalist-a td" "{" "  color: black;" "  padding: 9px 8px 0px 8px;" "}" "#hor-minimalist-a tbody tr:hover td" "{" "  color: black;" "}" "</style>";

  prolog = "<html><head><title>Java Precisely Examples</title>" css "</head><body>";
  epilog = "</body></html>";
  rs = "<tr>";
  re = "</tr>";
  es = "<td>";
  ee = "</td>";
  CW = "25%";
  # styling from http://coding.smashingmagazine.com/2008/08/13/top-10-css-table-designs/
  tableId = "hor-minimalist-a";
  # ts = "<table style=\'table-layout:fixed\' border=\"1\">" "<col width=" CW "> <col width=" CW "> <col width=" CW ">" "<col width=" CW ">"; 
  ts = "<table id = " tableId " style=\'table-layout:fixed\' >" 
  # "<col width=" CW "> <col width=" CW "> <col width=" CW ">" "<col width=" CW ">"  
  te = "</table>";
  FS = ",";
  N = 8; # number of fields per row of table
  urlPrefix = "http://www.assembla.com/code/adnanpublic/subversion/nodes/JavaExpts/JP/";
  print prolog;
  print ts;
  }
{
  if ( count == 0 ) {
    print rs;
  }
  print es "<a href = \n\"" urlPrefix "\">\n" "Example " $1"</a>: " $2 ee;
  if ( count++ == N - 1 ) {
    print re;
    count = 0;
  }
}
END {
  if ( count != 0 ) {
    for ( i = 0 ; i < N - count; i++ ) {
      print es "blank" ee
    }
    print re
  }
  print te;
  print epilog;
}
