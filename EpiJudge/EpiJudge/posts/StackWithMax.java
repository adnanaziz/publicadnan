package com.epi;

/*
    @slug
    stack-with-max

    @title
    Stack with max operation

    @problem
    Design a stack that includes  a max() operation, in addition to push() and
   pop().
    The max method should return the maximum value stored in the stack.

    @hint
    Use additional storage to track the maximum value.
 */

import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;

public class StackWithMax {
  // @include
  private static class ElementWithCachedMax {
    public Integer element;
    public Integer max;

    public ElementWithCachedMax(Integer element, Integer max) {
      this.element = element;
      this.max = max;
    }
  }

  public static class Stack {
    // Stores (element, cached maximum) pair.
    private Deque<ElementWithCachedMax> elementWithCachedMax
        = new LinkedList<>();

    public boolean empty() { return elementWithCachedMax.isEmpty(); }

    // @judge-include-display
    public Integer max() {
      // @judge-exclude-display
      if (empty()) {
        throw new IllegalStateException("max(): empty stack");
      }
      return elementWithCachedMax.peek().max;
      // @judge-include-display
    }
    // @judge-exclude-display

    // @judge-include-display
    public Integer pop() {
      // @judge-exclude-display
      if (empty()) {
        throw new IllegalStateException("pop(): empty stack");
      }
      return elementWithCachedMax.removeFirst().element;
      // @judge-include-display
    }
    // @judge-exclude-display

    // @judge-include-display
    public void push(Integer x) {
      // @judge-exclude-display
      elementWithCachedMax.addFirst(
          new ElementWithCachedMax(x, Math.max(x, empty() ? x : max())));
      // @judge-include-display
    }
    // @judge-exclude-display
  }
  // @exclude

  public static void main(String[] args) {
    Stack s = new Stack();
    s.push(1);
    s.push(2);
    assert(s.max() == 2);
    System.out.println(s.max()); // 2
    System.out.println(s.pop()); // 2
    assert(s.max() == 1);
    System.out.println(s.max()); // 1
    s.push(3);
    s.push(2);
    assert(s.max() == 3);
    System.out.println(s.max()); // 3
    s.pop();
    assert(s.max() == 3);
    System.out.println(s.max()); // 3
    s.pop();
    assert(s.max() == 1);
    System.out.println(s.max()); // 1
    s.pop();
    try {
      s.max();
      s.pop();
      s.pop();
      s.pop();
      s.pop();
    } catch (RuntimeException e) {
      System.out.println(e.getMessage());
    }
  }
}
