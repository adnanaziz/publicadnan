// Example 117 from page 87 of Java Precisely second edition (The MIT Press 2005)
// Author: Peter Sestoft (sestoft@itu.dk)

import java.io.*; 
import java.util.*;

/*
   This generic interface represents a function from A to R; in C# one
   would use a generic delegate instead.

   In MyLinkedList method equals(Object), the cast to MyList<T> is
   unchecked and may fail at runtime.
*/

interface Mapper<A,R> {
  R call(A x);
}

// A generic linked-list class with elements of type T

interface MyList<T> extends Iterable<T> {
  int getCount();                       // Number of elements
  T get(int i);                         // Get element at index i
  void set(int i, T item);              // Set element at index i
  void add(T item);                     // Add element at end
  void insert(int i, T item);           // Insert element at index i
  void removeAt(int i);                 // Remove element at index i
  <U> MyList<U> map(Mapper<T,U> f);     // Map f over all elements
}

class MyLinkedList<T> implements MyList<T> {
  protected int size;               // Number of elements in the list
  protected Node<T> first, last;    // Invariant: first==null iff last==null

  protected static class Node<U> {
    public Node<U> prev, next;
    public U item;

    public Node(U item) {
      this.item = item; 
    }

    public Node(U item, Node<U> prev, Node<U> next) {
      this.item = item; this.prev = prev; this.next = next; 
    }
  }

  public MyLinkedList() {
    first = last = null;
    size = 0;
  }

  public MyLinkedList(T... arr) {
    this();
    for (T x : arr) 
      add(x);
  }

  public int getCount() {
    return size; 
  }

  public T get(int i) {
    return getItem(i).item; 
  }      

  public void set(int i, T item) {
    getItem(i).item = item;
  }      

  private Node<T> getItem(int n) {
    if (n < 0 || n >= size)
      throw new IndexOutOfBoundsException();
    else if (n < size/2) {              // Closer to front
      Node<T> node = first;
      for (int i=0; i<n; i++)
        node = node.next;
      return node;
    } else {                            // Closer to end
      Node<T> node = last;
      for (int i=size-1; i>n; i--)
        node = node.prev;
      return node;
    }
  }

  public void add(T item) { 
    insert(size, item); 
  }

  public void insert(int i, T item) { 
    if (i == 0) {
      if (first == null) // and thus last == null
        first = last = new Node<T>(item);
      else {
        Node<T> tmp = new Node<T>(item, null, first);
        first.prev = tmp;
        first = tmp;
      }
      size++;
    } else if (i == size) {
      if (last == null) // and thus first = null
        first = last = new Node<T>(item);
      else {
        Node<T> tmp = new Node<T>(item, last, null);
        last.next = tmp;
        last = tmp;
      }
      size++; 
    } else {
      Node<T> node = getItem(i);
      // assert node.prev != null;
      Node<T> newnode = new Node<T>(item, node.prev, node);
      node.prev.next = newnode;
      node.prev = newnode;
      size++;
    }
  }

  public void removeAt(int i) {
    Node<T> node = getItem(i);
    if (node.prev == null) 
      first = node.next;
    else
      node.prev.next = node.next;
    if (node.next == null) 
      last = node.prev;
    else
      node.next.prev = node.prev;       
    size--;
  }

  public boolean equals(Object that) {
    return equals((MyList<T>)that);             // Unchecked cast
  }

  public boolean equals(MyList<T> that) {
    if (that != null && this.size == that.getCount()) {
      Node<T> thisnode = this.first;
      Iterator<T> thatiter = that.iterator();
      while (thisnode != null) {
        if (!thatiter.hasNext())
          throw new RuntimeException("Impossible: MyLinkedList<T>.equals");
        // assert next() will succeed (because of the above size test)
        if (!thisnode.item.equals(thatiter.next()))
          return false;
        thisnode = thisnode.next; 
      }
      // assert !hasNext(); // because of the size test
      return true;
    } else
      return false;
  }

  public <U> MyList<U> map(Mapper<T,U> f) {
    MyLinkedList<U> res = new MyLinkedList<U>();
    for (T x : this) 
      res.add(f.call(x));
    return res;
  }

  public Iterator<T> iterator() {
    return new MyLinkedListIterator();
  }

  private class MyLinkedListIterator implements Iterator<T> {
    Node<T> next;                  // Node holding current element, or null

    public MyLinkedListIterator() {
      next = first;
    }
    
    public T next() {
      if (next != null) {
        T res = next.item;
        next = next.next;
        return res;
      } else 
        throw new NoSuchElementException();
    }
    
    public boolean hasNext() {
      return next != null;
    }

    public void remove() {
      throw new UnsupportedOperationException();
    }
  }
}

class SortedList<T extends Comparable<T>> extends MyLinkedList<T> {
  // Sorted insertion
  public void insert(T x) { 
    Node<T> node = first;
    while (node != null && x.compareTo(node.item) > 0) 
      node = node.next;
    if (node == null)           // x > all elements; insert at end
      add(x);
    else {                      // x <= node.item; insert before node
      Node<T> newnode = new Node<T>(x);
      if (node.prev == null)    // insert as first element
        first = newnode;
      else 
        node.prev.next = newnode;
      newnode.next = node;
      newnode.prev = node.prev;
      node.prev = newnode;
    }
  }
}

interface Printable {
  void print(PrintWriter fs);
}

class PrintableMyLinkedList<T extends Printable> 
  extends MyLinkedList<T> implements Printable 
{
  public void print(PrintWriter fs) {
    boolean firstElement = true;
    for (T x : this) {
      x.print(fs);
      if (firstElement) 
        firstElement = false;
      else
        fs.print(", ");
    }
  }
}

class MyString implements Comparable<MyString> {
  public final String s;
  public MyString(String s) {
    if (s == null) 
      throw new RuntimeException("null string");
    else
      this.s = s;
  }
  public int compareTo(MyString that) {
    return s.compareTo(that.s);       
  }
}

class Example117 {
  public static void main(String[] args) {
    MyLinkedList<Double> dLst 
      = new MyLinkedList<Double>(7.0, 9.0, 13.0, 0.0);
    for (double d : dLst)
      System.out.print(d + " ");
    System.out.println();
    MyList<Integer> iLst = 
      dLst.map(new Mapper<Double, Integer>() {
        public Integer call(Double d) { 
          return d < 0 ? -1 : d > 0 ? +1 : 0;
        }
      });
    for (int i : iLst)
      System.out.print(i + " ");
    System.out.println();
    MyList<String> sLst = 
      dLst.map(new Mapper<Double, String>() {
        public String call(Double d) { 
          return "s" + d; 
        }
      });
    for (String s : sLst)
      System.out.print(s + " ");
    System.out.println();
    // Testing SortedList<MyString>
    SortedList<MyString> sortedLst = new SortedList<MyString>();
    sortedLst.insert(new MyString("New York"));
    sortedLst.insert(new MyString("Rome"));
    sortedLst.insert(new MyString("Dublin"));
    sortedLst.insert(new MyString("Riyadh"));
    sortedLst.insert(new MyString("Tokyo"));
    for (MyString s : sortedLst)
      System.out.print(s.s + "   ");
    System.out.println();
  }
}


