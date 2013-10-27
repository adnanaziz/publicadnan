import java.util.Iterator;
import java.util.Map;

import com.google.common.collect.Multimap;
import com.google.common.collect.HashMultimap;

public class MinHashTest {
  int K, L, P, S;
  char[] alphabet;
  GenerateTestData td;

  public MinHashTest(int K, int L, int P, int S, char[] alphabet) {
    this.K = K; 
    this.L = L;
    this.P = P;
    this.S = S;
    this.alphabet = alphabet;
    this.td = new GenerateTestData(K,L, alphabet);
  }
  
  public String[] getFiles(int N) {
    String[] result = new String[N];
    for ( int i = 0 ; i < N; i++ ) {
      result[i] = td.randomString(P, i, S);
    }
    return result;
  }
  
  public static void main(String[] args) {
    int N = 2000;
    int K = 2;
    int L = 10000;
    int P = 1000;
    int S = 1000;
    MinHashTest mht1 = new MinHashTest(K, L, P, S, GenerateTestData.DNA);
    Signature sig = new Signature(1,80);
    String[] sa1 = mht1.getFiles(N);
    int i = 0;
    Multimap<Integer,Integer> minHashToStringId = HashMultimap.create();
    for ( String s : sa1 ) {
      minHashToStringId.put( sig.minHash(s), i++ );
    }

    Iterator<Map.Entry<Integer,Integer>> iter = minHashToStringId.entries().iterator();
    while ( iter.hasNext() ) {
      Map.Entry<Integer,Integer> e = iter.next();
      System.out.println("hash, string id " + e.getKey() + " , " + e.getValue());
    }
  }
}
