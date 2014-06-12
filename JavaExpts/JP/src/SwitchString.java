public class SwitchString {
  public static void main(String[] args) {
    String s = "foo";
    int i;
    switch (s) {
    case "bar":
      i = 1;
      break;
    case "foo":
      i = 2;
      break;
    case "widget":
      i = 3;
      break;
    default:
      i = -1;
    }
    System.out.println("i = " + i);
  }
}
