public class Monotone2DArray {
	public static boolean checkMonotone(int[][] a) {
		int i = 0, j = 0;

		for (i = 0; i < a.length; i++) {
			for (j = 0; j < a[i].length - 1; j++) {
				if (a[i][j + 1] <= a[i][j])
					return false;
			}
		}
		for (i = 0; i < a.length - 1; i++) {
			for (j = 0; j < a[i].length; j++) {
				if (a[i + 1][j] <= a[i][j])
					return false;
			}
		}
		return true;
	}
}
