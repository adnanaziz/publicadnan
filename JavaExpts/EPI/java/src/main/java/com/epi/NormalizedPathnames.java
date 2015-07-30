package com.epi;

import java.util.Iterator;
import java.util.LinkedList;

public class NormalizedPathnames {

  // @include
  public static String shortestEquivalentPath(String path) {
    LinkedList<String> pathNames = new LinkedList<>();
    // Special case: starts with "/", which is an absolute path.
    if (path.startsWith("/")) {
      pathNames.push("/");
    }

    for (String token : path.split("/")) {
      System.out.println(token);
      if (token.equals("..")) {
        if (pathNames.isEmpty() || pathNames.peek().equals("..")) {
          pathNames.push(token);
        } else {
          if (pathNames.peek().equals("/")) {
            throw new IllegalArgumentException(
                "Path error, trying to go up root " + path);
          }
          pathNames.pop();
        }
      } else if (!token.equals(".") && !token.isEmpty()) { // Must be a name.
        pathNames.push(token);
      }
    }

    StringBuilder result = new StringBuilder();
    if (!pathNames.isEmpty()) {
      Iterator<String> it = pathNames.descendingIterator();
      String prev = it.next();
      result.append(prev);
      while (it.hasNext()) {
        if (!prev.equals("/")) {
          result.append("/");
        }
        prev = it.next();
        result.append(prev);
      }
    }
    return result.toString();
  }
  // @exclude

  public static void main(String[] args) {
    assert (shortestEquivalentPath("123/456").equals("123/456"));
    assert (shortestEquivalentPath("/123/456").equals("/123/456"));
    assert (shortestEquivalentPath("usr/lib/../bin/gcc").equals("usr/bin/gcc"));
    assert (shortestEquivalentPath("./../").equals(".."));
    assert (shortestEquivalentPath("../../local").equals("../../local"));
    assert (shortestEquivalentPath("./.././../local").equals("../../local"));
    assert (shortestEquivalentPath("/foo/../foo/./../").equals("/"));
    try {
      shortestEquivalentPath("/..");
    } catch (IllegalArgumentException e) {
      System.out.println(e.getMessage());
    }
    try {
      shortestEquivalentPath("/cpp_name/bin/");
    } catch (Exception e) {
      System.out.println(e.getMessage());
      assert (false);
    }
    assert (shortestEquivalentPath("scripts//./../scripts/awkscripts/././")
                .equals("scripts/awkscripts"));
    if (args.length == 1) {
      System.out.println(shortestEquivalentPath(args[0]));
    }
  }
}
