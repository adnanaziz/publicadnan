// Perverse test of ObservableSet - bottom of Page 267

import java.util.*;
import java.util.concurrent.*;

public class Test3 {
    public static void main(String[] args) {
        ObservableSet<Integer> set =
            new ObservableSet<Integer>(new HashSet<Integer>());

        // Observer that uses a background thread needlessly
        set.addObserver(new SetObserver<Integer>() {
            public void added(final ObservableSet<Integer> s, Integer e) {
                System.out.println(e);
                if (e == 23) {
                    ExecutorService executor =
                        Executors.newSingleThreadExecutor();
                    final SetObserver<Integer> observer = this;
                    try {
                        executor.submit(new Runnable() {
                            public void run() {
                                s.removeObserver(observer);
                            }
                        }).get(); 
                    } catch (ExecutionException ex) {
                        throw new AssertionError(ex.getCause());
                    } catch (InterruptedException ex) {
                        throw new AssertionError(ex.getCause());
                    } finally {
                        executor.shutdown();
                    }
                }
            }
        });

        for (int i = 0; i < 100; i++)
            set.add(i);
    }
}
