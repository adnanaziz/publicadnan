import java.util.HashSet;
import java.util.TreeSet;
import java.util.Random;

public class Benchmark {
    static Random rnd = new Random();
    static int[] intArray;
    static String[] stringArray;

    static int UNITS = 1000000;

    static HashSet<String> hashSetString = new HashSet<>();
    static TreeSet<String> treeSetString = new TreeSet<>();
    static HashSet<Integer> hashSetInteger = new HashSet<>();
    static TreeSet<Integer> treeSetInteger = new TreeSet<>();

    static void initData(int M) {
        intArray = new int[M];
        stringArray = new String[M];
        for(int i = 0; i < M; i++) {
            intArray[i] = rnd.nextInt();
            stringArray[i] = "" + Math.abs(rnd.nextInt());
        }
    }

    static void TestInteger(int N) {
        System.out.println("Start init hash int:\t" + System.nanoTime()/UNITS);
        for (int i = 0; i < intArray.length; i++) {
            hashSetInteger.add(intArray[i]);
        }
        System.out.println("End init hash int:\t" + System.nanoTime()/UNITS);

        System.out.println("Start init tree int:\t" + System.nanoTime()/UNITS);
        for (int i = 0; i < intArray.length; i++) {
            treeSetInteger.add(intArray[i]);
        }
        System.out.println("End init tree int:\t" + System.nanoTime()/UNITS);

        System.out.println("Start lookup hash int:\t" + System.nanoTime()/UNITS);
        int L = 0;
        for (int i = 0; i < intArray.length; i++) {
            L += hashSetInteger.contains(intArray[i]) ? 1: 0;
        }
        System.out.println("End lookup hash int:\t" + System.nanoTime()/UNITS + " " + L);

        System.out.println("Start lookup tree int:\t" + System.nanoTime()/UNITS);
        L = 0;
        for (int i = 0; i < intArray.length; i++) {
            L += treeSetInteger.contains(intArray[i]) ? 1: 0;
        }
        System.out.println("End lookup tree int:\t" + System.nanoTime()/UNITS + " " + L);
    }

    static void TestString(int N) {
        System.out.println("Start init hash:\t" + System.nanoTime()/UNITS);
        for (int i = 0; i < stringArray.length; i++) {
            hashSetString.add(stringArray[i]);
        }
        System.out.println("End init hash:\t" + System.nanoTime()/UNITS);

        System.out.println("Start init tree:\t" + System.nanoTime()/UNITS);
        for (int i = 0; i < stringArray.length; i++) {
            treeSetString.add(stringArray[i]);
        }
        System.out.println("End init tree:\t" + System.nanoTime()/UNITS);

        System.out.println("Start lookup hash:\t" + System.nanoTime()/UNITS);
        int L = 0;
        for (int i = 0; i < stringArray.length; i++) {
            L += hashSetString.contains(stringArray[i]) ? 1: 0;
        }
        System.out.println("End lookup hash:\t" + System.nanoTime()/UNITS + " " + L);

        System.out.println("Start lookup tree:\t" + System.nanoTime()/UNITS);
        L = 0;
        for (int i = 0; i < stringArray.length; i++) {
            L += treeSetString.contains(stringArray[i]) ? 1: 0;
        }
        System.out.println("End lookup tree:\t" + System.nanoTime()/UNITS + " " + L);
    }

    public static void main(String[] args) {
        int N = 1000000;
        int M = 1000000;
        if (args.length > 0) {
            N = Integer.parseInt(args[0]);
        }
        if (args.length > 1) {
            M = Integer.parseInt(args[1]);
        }
        initData(M);
        TestString(N);
        TestInteger(N);
        // BSTTest(N);
    }
}
