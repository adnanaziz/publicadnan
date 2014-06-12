// Example 84 from page 59 of Java Precisely second edition (The MIT Press 2005)
// Author: Peter Sestoft (sestoft@itu.dk)

import java.io.*;
import java.util.*;

class Example84 {
  public static void main(String[] args) throws IOException {
    if (args.length != 2) 
      System.out.println("Usage: java -ea Example84 <textfile> <linewidth>\n");
    else {
      Iterator wordIter = 
        new ReaderIterator(new BufferedReader(new FileReader(args[0])));
      int lineWidth = Integer.parseInt(args[1]);
      format(wordIter, lineWidth, 
             new PrintWriter(new OutputStreamWriter(System.out)));
    }
  }

  // This method formats a sequence of words (Strings obtained from
  // the iterator wordIter) into lines of text, padding the inter-word
  // gaps with extra spaces to obtain lines of length lineWidth, and 
  // thus a straight right margin.
  //
  // There are the following exceptions: 
  //  * if a word is longer than lineWidth, it is put on a line by
  //    itself (producing a line longer than lineWidth)
  //  * a single word may appear on a line by itself (producing a line
  //     shorter than lineWidth) if adding the next word to the line
  //     would make it longer than lineWidth
  //  * the last line of the output is not padded with extra spaces.
  //
  // The algorithm for padding with extra spaces ensures that the
  // spaces are evenly distributed over inter-word gaps, using modulo
  // arithmetics.  An assert statement asserts that the resulting
  // output line has the correct length unless the line contains only
  // a single word or is the last line of the output.

  public static void format(Iterator wordIter, int lineWidth, PrintWriter pw) {
    lineWidth = Math.max(0, lineWidth);
    WordList curLine = new WordList();
    while (wordIter.hasNext()) {
      while (wordIter.hasNext() && curLine.length() < lineWidth) {
        String word = (String)wordIter.next();
        if (!word.equals("")) 
          curLine.addLast(word);
      }
      int wordCount = curLine.size();
      if (wordCount > 0) {
        int extraSpaces = lineWidth - curLine.length();
        if (wordCount > 1 && extraSpaces < 0) { // last word goes on next line
          int lastWordLength = curLine.getLast().length();
          extraSpaces += 1 + lastWordLength;
          wordCount -= 1;
        } else if (!wordIter.hasNext())         // last line, do not pad
          extraSpaces = 0;
        // Pad holes with evenly distributed extra spaces
        int holes = wordCount - 1;
        int spaces = holes/2;
        StringBuilder sbuf = new StringBuilder();
        sbuf.append(curLine.removeFirst());
        for (int i=1; i<wordCount; i++) {
          spaces += extraSpaces;
          appendSpaces(sbuf, 1 + spaces / holes);
          spaces %= holes;
          sbuf.append(curLine.removeFirst());
        }
        String res = sbuf.toString();
        assert res.length()==lineWidth || wordCount==1 || !wordIter.hasNext();
        pw.println(res);
      }
    }
    pw.flush();
  }

  static void appendSpaces(StringBuilder sbuf, int count) {
    for (int i=0; i<count; i++)
      sbuf.append(' ');
  }
}

// A word list with a fast length method, and invariant assertions

class WordList {
  private LinkedList<String> strings = new LinkedList<String>();
  // Invariant: length equals word lengths plus inter-word spaces
  private int length = -1;   

  public int length() { return length; }

  public int size() { return strings.size(); }
  
  public void addLast(String s) {
    strings.addLast(s);
    length += 1 + s.length();
    assert length == computeLength() + strings.size() - 1;
  }

  public String removeFirst() {
    String res = strings.removeFirst();
    length -= 1 + res.length();
    assert length == computeLength() + strings.size() - 1;
    return res;
  }

  public String getLast() {
    return strings.getLast();
  }

  private int computeLength() {  // For checking the invariant only
    int sum = 0;
    for (String s : strings) 
      sum += s.length();
    return sum;
  }
}

// A String-producing Iterator, created from a Reader using StreamTokenizer

class ReaderIterator implements Iterator {
  private StreamTokenizer stok; // equals null if at end or error

  public ReaderIterator(Reader rd) {
    stok = new StreamTokenizer(rd);
    stok.resetSyntax();
    stok.whitespaceChars(' ', ' '); stok.whitespaceChars('\n', '\n'); 
    stok.whitespaceChars('\r', '\r'); stok.whitespaceChars('\t', '\t'); 
    stok.wordChars('!', '~');
    try {
      stok.nextToken();
    } catch (IOException e) {
      stok = null;
    }
  }

  public boolean hasNext() {
    return stok != null && stok.ttype != StreamTokenizer.TT_EOF;
  }
        
  public Object next() {
    String res = null;
    if (hasNext()) 
      try {
        res = stok.sval;
        stok.nextToken();
      } catch (IOException e) { 
        stok = null;
      }
    return res;
  }
  
  public void remove() {
    throw new UnsupportedOperationException();
  }
}

