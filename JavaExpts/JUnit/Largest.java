public class Largest {

  /** 
   * Return the largest element in a list.
   *
   * @param list A list of integers
   * @return The largest number in the given list
   */

  public static int largest(int[] list) {
    int index, max=Integer.MAX_VALUE;

    if (list.length == 0) {
      throw new RuntimeException("Empty list");
    }

    for (index = 0; index < list.length-1; index++) {
      if (list[index] > max) {
        max = list[index];
      }
    }
    return max;
  }

}



