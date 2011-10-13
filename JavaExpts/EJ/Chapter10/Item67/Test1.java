// Simple test of ObservableSet - Page 266

import java.util.*;

public class Test1 {
    public static void main(String[] args) {
        ObservableSet<Integer> set =
            new ObservableSet<Integer>(new HashSet<Integer>());

        set.addObserver(new SetObserver<Integer>() {
                public void added(ObservableSet<Integer> s, Integer e) {
                    System.out.println(e);
            }
        });

        for (int i = 0; i < 100; i++)
            set.add(i);
    }
}
