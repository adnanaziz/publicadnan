import java.util.Arrays;
import java.util.List;
import java.util.Collections;
import com.google.common.primitives.Ints;

interface HashFunction {
	public int nextHash(String s, int currentHash, int start, int end);
}

class ParametrizedHashFunction implements HashFunction {
	int A;
	int B;

	public ParametrizedHashFunction(int A, int B) {
		this.A = A;
		this.B = B;
	}

	public int nextHash(String s, int currentHash, int start, int end) {
		// TODO(AA): make incremental
		int result = B;
		for (int i = start; i < end; i++) {
			result *= A;
			char c = s.charAt(i);
			int cint = (int) c;
			result += cint;
		}
		return result;
	}
}

public class Signature {
	HashFunction[] hashFunctions;
	int N;
	int L;
	static final int DUMMY = 0;

	public Signature(int N, int L) {
		this.N = N;
		this.L = L;
		hashFunctions = new HashFunction[N];
		for (int i = 0; i < N; i++) {
			// TODO(AA): get array of primes
			this.hashFunctions[i] = new ParametrizedHashFunction(2 * i + 1, 0);
		}
	}

	public int minHash(String s) {
		int[] signature = signature(s);
		List<Integer> li = Ints.asList(signature);
		int result = Collections.min(li);
		return result;
	}

	public int[] signature(String s) {
		int[] result = new int[N];
		if (s.length() < L) {
			throw new IllegalArgumentException(
					"String too short, it's length is " + s.length()
							+ " but L = " + L);
		}
		for (int i = 0; i < N; i++) {
			result[i] = Integer.MAX_VALUE;
			for (int j = 0; j < s.length() - L; j++) {
				int subStringHash = hashFunctions[i].nextHash(s, DUMMY, j, j
						+ L);
				result[i] = (result[i] > subStringHash) ? subStringHash
						: result[i];
			}
		}
		return result;
	}

	public static void main(String[] args) {

		Signature sigObj = new Signature(4, 1);
		String test1 = "abdef";
		int[] sig1 = sigObj.signature(test1);
		System.out.println("Codes for " + test1);
		for (int hc : sig1) {
			System.out.print(hc + " ");
		}
		System.out.println();

		String test2 = "erabcd3423fvds";
		int[] sig2 = sigObj.signature(test2);
		System.out.println("Codes for " + test2);
		for (int hc : sig2) {
			System.out.print(hc + " ");
		}
		System.out.println("\nmin hash for " + test2 + " = "
				+ sigObj.minHash(test2));
		System.out.println();
	}

}
