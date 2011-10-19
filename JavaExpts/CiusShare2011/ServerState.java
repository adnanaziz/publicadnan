public ServerState {

  static Map<String,String> userToPasswd;
  static Map<String,Integer> userToId;
  static Map<String,List<String>> groupNameToUsers;
  static Map<String,List<String>> userToFiles;
  static Map<String,List<String>> fileToHistory; // embed history in fileName
  static String fileNameToUIName( String aFileName );
  String toString() {
    // return the Server as a String;
    return null;
  }

  public ServerState( String textualView ) {
    // build the servers from the text string
    // get the text string from the file the server's been persisted to
  }

  public static addUser(String user, String pswd) {
  }

  public static addFile(String user, String File) {
  }

  public static addSharer(String file, String user);
  }
  // many more functions for updates, returning lists of files
  //
  // will have to create a real user class, instead of passing strings
  // back and forth instead of users

}
