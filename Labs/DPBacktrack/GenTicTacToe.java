import java.util.HashMap;
import java.util.Map;

public class GenTicTacToe {

	/**
	 * Example: When winnable("X", "X,O,E", "E,E,O", "E,O,X"); is called, the
	 * corresponding board position is E O X E E O X O E You can assume that the
	 * number of entries per row equals the number of rows
	 * 
	 * @param L
	 *            the length of the line that X or O have to make to win
	 * @param S
	 *            is either "X" or "O" indicating which player is to play next
	 * @param rows
	 *            a set of rows, each of which is a string
	 * @return if the player specified by S can win
	 */
	public static boolean winnable(int L, String S, String... rows) {

		// TODO(EE422C): implement this function
		Board b = new Board(rows);
		SquareState player = SquareState.fromString(S);
		// System.out.println(b.toString());
		// boolean result = b.playerBestOutcome(player)==Outcome.WIN;

		return b.playerBestOutcome(player) == Outcome.WIN;
	}

	/**
	 * Identical to function above, with K specifying the number of moves player
	 * S are allowed to make
	 * 
	 * @param L
	 *            the length of the line that X or O have to make to win
	 * @param S
	 *            is either "X" or "O" indicating which player is to play next
	 * @param K
	 *            the number of moves allowed for player s
	 * @param rows
	 *            a set of rows, each of which is a string
	 * @return if the player specified by S can win
	 */
	public static boolean winnable(int L, String S, int K, String... rows) {
		// TODO(EE422C): implement this function
		Board b = new Board(rows);
		SquareState player = SquareState.fromString(S);
		// System.out.println(b.toString());
		boolean result = b.playerBestOutcome(player) == Outcome.WIN;
		int _step = b.getWinStep(player);
		System.out.println("step lap " + (_step));
		if (result == true && _step <= K)
			return true;
		else
			return false;

	}
}

enum Outcome {
	WIN, LOSE, DRAW;
}

enum SquareState {
	E, X, O;

	void checkNotEmpty() {
		if (this == E) {
			System.out.println("Eoor: empty square");
			assert (false);
		}
	}

	SquareState opposite() {
		this.checkNotEmpty();
		if (this == X) {
			return O;
		}
		return X;
	}

	static SquareState fromString(String s) {
		if (s.equals("E"))
			return E;
		else if (s.equals("X"))
			return X;
		else if (s.equals("O"))
			return O;
		else {
			System.out.println("Bad argument to SquareState constructor:" + s);
			assert (false);
			return null;
		}
	}
}

class Board {
	public int steps = 0;
	SquareState boardstate[][];
	int n; // dimension
	public Map<SquareState[][], Outcome> cache = new HashMap<SquareState[][], Outcome>();

	boolean isFull() {
		int i, j;
		for (i = 0; i < n; i++) {
			for (j = 0; j < n; j++) {
				if (boardstate[i][j] == SquareState.E) {
					return false;
				}
			}
		}
		return true;
	}

	public SquareState[][] getBoardstate() {
		return boardstate;
	}

	void set(int x, int y, SquareState s) {
		assert (0 <= x && x < n && 0 <= y && y < n);
		boardstate[x][y] = s;
	}

	Board(int n) {
		this.boardstate = new SquareState[n][];
		this.n = n;
		int i;
		for (i = 0; i < n; i++)
			boardstate[i] = new SquareState[n];
	}

	Board(String... rows) {
		n = rows.length;
		boardstate = new SquareState[n][];
		for (int i = 0; i < n; i++) {
			boardstate[i] = new SquareState[n];
			String[] entries = (rows[i]).split(",");
			if (entries.length != n) {
				System.out.println("bad input");
				assert (false);
			}
			for (int j = 0; j < entries.length; j++) {
				boardstate[i][j] = SquareState.fromString(entries[j]);
			}
		}
	}

	static public void p(String s) {
		System.out.println(s);
	}

	int numEmpty() {
		int result = 0;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (boardstate[i][j] == SquareState.E) {
					result++;
				}
			}
		}
		return result;
	}

	boolean winFor(SquareState s) {
		boolean result = false;
		int i, j;
		for (i = 0; i < n; i++) {
			result = true;
			for (j = 0; j < n; j++) {
				if (boardstate[i][j] != s) {
					result = false;
					break;
				}
			}
			if (result) {
				return true;
			}
		}

		for (j = 0; j < n; j++) {
			result = true;
			for (i = 0; i < n; i++) {
				if (boardstate[i][j] != s) {
					result = false;
					break;
				}
			}
			if (result) {
				return true;
			}
		}

		result = true;
		for (i = 0; i < n; i++) {
			if (boardstate[i][i] != s) {
				result = false;
				break;
			}
		}
		if (result)
			return true;

		result = true;
		for (i = 0; i < n; i++) {
			if (boardstate[i][(n - 1) - i] != s) {
				result = false;
				break;
			}
		}
		return result;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				sb.append(boardstate[i][j]);
			}
			sb.append("\n");
		}
		return sb.toString();
	}

	static long numCalls;

	static SquareState[][] deepcopy(SquareState[][] A) {
		SquareState[][] result = new SquareState[A.length][];
		for (int i = 0; i < A.length; i++) {
			result[i] = A[i].clone();
		}

		return result;
	}

	Outcome playerBestOutcome(SquareState s) {

		if ((numCalls++ % 1000000) == 0) {
			p(numCalls + " calls, state is:\n" + this.toString());
			System.out.flush();
		}

		Outcome cachedresult = cache.get(boardstate);

		if (cachedresult != null) {
			return cachedresult;
		}
		s.checkNotEmpty();
		if (this.isFull()) {
			return Outcome.DRAW;
		}
		if (this.winFor(s)) {

			return Outcome.WIN;
		}
		if (this.winFor(s.opposite())) {
			return Outcome.LOSE;
		}

		int i, j;
		boolean canDraw = false;
		for (i = 0; i < n; i++) {
			for (j = 0; j < n; j++) {
				if (boardstate[i][j] == SquareState.E) {
					boardstate[i][j] = s;
					Outcome outcome = this.playerBestOutcome(s.opposite());
					if (outcome == Outcome.LOSE) {

						boardstate[i][j] = SquareState.E;

						cache.put(deepcopy(boardstate), Outcome.WIN);

						return Outcome.WIN;
					}
					if (outcome == Outcome.DRAW) {
						canDraw = true;
					}
					boardstate[i][j] = SquareState.E;
				}
			}
		}
		if (canDraw) {
			cache.put(deepcopy(boardstate), Outcome.DRAW);
			return Outcome.DRAW;
		}
		return Outcome.LOSE;
	}

	public int getSteps(SquareState boardstate[][], SquareState s) {
		int num = 0;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (boardstate[i][j] == s)
					num++;
			}

		}

		return num;
	}

	private int getStepsFromMap(SquareState s) {
		int _step = n*n;

		for (int i = 0; i < cache.size(); i++) {

			SquareState[][] key = (SquareState[][]) cache.keySet().toArray()[i];

			Outcome val = (Outcome) cache.values().toArray()[i];

			if (val == Outcome.WIN) {
				int temp = getSteps(key, s);
				if (temp != getSteps(boardstate, s) && temp < _step)
					_step = temp;
				
//				for (int a=0;a<n;a++) {
//					for (int b=0;b<n;b++)
//				System.out.print(key[a][b]);
//					System.out.println();
//				}
			}

		}
		return _step;
	}

	public int getWinStep(SquareState s) {
		int init_step = getSteps(boardstate, s);
		int _step = getStepsFromMap(s);
		int result =  _step - init_step;
		System.out.println("init_step "+ init_step + "_step "+_step);
		
		return result;
	}
}
