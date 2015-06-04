import java.util.Iterator;
import java.lang.IllegalStateException;

public class MultiMapTest {
    public static void main(String[] args) {
        MultiMap<Integer, String> m1 = new HashMultiMap(); // java 1.7 type inference
        Iterator<String> i1 = m1.valueIterator();
        assert(!i1.hasNext());
        m1.add(42, "foo");
        Iterator<String> i2 = m1.valueIterator();
        assert(i2.next() == "foo");
        assert(!i2.hasNext());
        m1.add(42, "foobar");
        m1.add(42, "foo");
        m1.add(35, "abc");
        m1.add(-1, "adnan");
        Iterator<String> i3 = m1.valueIterator();
        assert(i3.next() == "foo");
        assert(i3.next() == "foobar");
        assert(i3.next() == "foo");
        assert(i3.next() == "abc");
        assert(i3.next() == "adnan");
        assert(!i3.hasNext());
        m1.removeOne(35);
        Iterator<String> i4 = m1.valueIterator();
        assert(i4.next() == "foo");
        assert(i4.next() == "foobar");
        assert(i4.next() == "foo");
        assert(i4.next() == "adnan");
        assert(!i4.hasNext());
        m1.add(-1, "adnan");
        m1.removeAll(42);
        Iterator<String> i5 = m1.valueIterator();
        assert(i5.next() == "adnan");
        assert(i5.next() == "adnan");
        assert(!i5.hasNext());
        boolean gotException = false;
        try {
            i5.next();
        } catch (IllegalStateException e) {
            // expect this
            gotException = true;
        } 
        assert(gotException);
    }
}
