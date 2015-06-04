import java.util.LinkedList;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;
import java.util.Iterator;
import java.util.LinkedHashMap;

import java.lang.IllegalStateException;
import java.lang.UnsupportedOperationException;

public interface MultiMap<K,V> {
    void add(K key, V obj);
    boolean containsKey(K key);
    V removeOne(K key);
    LinkedList<V> removeAll(K key);
    LinkedList<V> getAll(K key);
    Iterator<K> keyIterator();
    Iterator<V> valueIterator();
};

class HashMultiMap<K,V> implements MultiMap<K,V> {
    // need final for anonymos class to work
    final private Map<K,LinkedList<V>> keyToValues;

    // Constructor
    public HashMultiMap() {
        keyToValues = new LinkedHashMap<K, LinkedList<V>>();
    }

    public boolean containsKey(K key) {
        return keyToValues.keySet().contains(key);
    }

    public void add(K key, V obj) {
        LinkedList<V> valuesForKey;
        if ( keyToValues.keySet().contains(key) )  {
            valuesForKey = keyToValues.get(key);
        } else {
            valuesForKey = new LinkedList<V>();
            keyToValues.put(key, valuesForKey);
        }
        valuesForKey.add(obj);
    }

    public V removeOne(K key) {
        if (!keyToValues.keySet().contains(key)) {
            return null;
        }
        LinkedList<V> valuesForKey = keyToValues.get(key);
        V result = valuesForKey.removeLast();
        if (valuesForKey.size() == 0) {
            keyToValues.remove(key);
        }
        return result;
    }
    public LinkedList<V> removeAll(K key) {
        return keyToValues.remove(key);
    }
    public LinkedList<V> getAll(K key) {
        return keyToValues.get(key);
    }
    public Iterator<K> keyIterator() {
        return keyToValues.keySet().iterator();
    }
    public Iterator<V> valueIterator() {
        return new Iterator<V>() {
            Iterator<K> keyValueIterator = keyToValues.keySet().iterator();
            Iterator<V> valuesForKeyIterator = (keyValueIterator.hasNext()) 
                                                    ? keyToValues.get( keyValueIterator.next() ).iterator() 
                                                    : new LinkedList<V>().iterator();
            public boolean hasNext() {
                return valuesForKeyIterator.hasNext() || keyValueIterator.hasNext();
            }
            public void remove() {
                throw new UnsupportedOperationException();
            
            }
            public V next() {
                if (valuesForKeyIterator.hasNext()) {
                    return valuesForKeyIterator.next();
                } else if (keyValueIterator.hasNext()) {
                    valuesForKeyIterator = keyToValues.get( keyValueIterator.next() ).iterator();
                    return valuesForKeyIterator.next();
                } else {
                    throw new IllegalStateException("ran out of entries! use hasNext() to check first."); 
                }
            }
        };
    }
};



