// Wrapper class - uses composition in place of inheritance - Page 84

import java.util.*;

public class InstrumentedSet<E> extends ForwardingSet<E> {
    private int addCount = 0;

    public InstrumentedSet(Set<E> s) {
        super(s);
    }

    @Override public boolean add(E e) {
        addCount++;
        return super.add(e);
    }
    @Override public boolean addAll(Collection<? extends E> c) {
        addCount += c.size();
        return super.addAll(c);
    }
    public int getAddCount() {
        return addCount;
    }

    public static void main(String[] args) {
        InstrumentedSet<String> s =
            new InstrumentedSet<String>(new HashSet<String>());
        s.addAll(Arrays.asList("Snap", "Crackle", "Pop"));    
        System.out.println(s.getAddCount());
    }
}
