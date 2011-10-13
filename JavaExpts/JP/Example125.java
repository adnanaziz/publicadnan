// Example 125 from page 99 of Java Precisely second edition (The MIT Press 2005)
// Author: Peter Sestoft (sestoft@itu.dk)

import java.util.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

class Example125 {
  public static void main(String[] args) throws SQLException { 
    String url = "jdbc:postgresql://localhost/messages";
    String usr = "nobody";
    String pwd = "";

    System.out.println("To run this example you need a data base server, a");
    System.out.println("database, and a suitable JDBC driver.");
    
    try { Class.forName("postgresql.Driver"); } 
    catch (ClassNotFoundException e) 
      { System.out.println("Cannot find the Postgresql driver"); }
    
    Connection conn = DriverManager.getConnection(url, usr, pwd); 
    printNameAndMsg(getRows(conn, "select * from message"));
  }

  // Return the query result as an ArrayList of Map from String to Object
  static ArrayList<Map<String,Object>> getRows(Connection conn, String query)
    throws SQLException 
  {
    Statement stmt = conn.createStatement();
    ResultSet rset = stmt.executeQuery(query); 
    ResultSetMetaData rsmd = rset.getMetaData();
    int columncount = rsmd.getColumnCount();
    ArrayList<Map<String,Object>> queryResult 
      = new ArrayList<Map<String,Object>>();
    while (rset.next()) {
      Map<String,Object> row = new HashMap<String,Object>();
      for (int i=1; i<=columncount; i++) 
        row.put(rsmd.getColumnName(i), rset.getObject(i));
      queryResult.add(row);
    }
    return queryResult;
  }

  static void printNameAndMsg(Collection<Map<String,Object>> coll) {
    for (Map<String,Object> row : coll)
      System.out.println(row.get("name") + ": " + row.get("msg"));
  }
}

