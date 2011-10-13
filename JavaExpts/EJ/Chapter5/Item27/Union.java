// Generic union method and program to exercise it - pages 129 - 130

import java.util.*;

public class Union {

    // Generic method
    public static <E> Set<E> union(Set<E> s1, Set<E> s2) {
        Set<E> result = new HashSet<E>(s1);
        result.addAll(s2);
        return result;
    }

    // Simple program to exercise generic method
    public static void main(String[] args) {
        Set<String> guys = new HashSet<String>(
            Arrays.asList("Tom", "Dick", "Harry"));
        Set<String> stooges = new HashSet<String>(
            Arrays.asList("Larry", "Moe", "Curly"));
        Set<String> aflCio = union(guys, stooges);
        System.out.println(aflCio);
    }
}
