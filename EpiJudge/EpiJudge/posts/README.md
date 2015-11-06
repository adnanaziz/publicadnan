Every problem is specified using this format. 

The data in the post can be viewed as a dictionary.
The key-value pairs are seperated by "\n--\n". The first string (after trimming) is the key,
rest is value.
You can put HTML in the value.
Linking to additional resources, like images, are fine. 
These resources should be in the folder called public. 

We use swig to fill in post.html, which is templatized by the keys in the dictionary created in this way.
Below is an example of a problem.

<pre>

slug
Parity

--


summary
Compute the parity of a long.  <b>#PrimitiveTypes #BitFiddling</b>

--

description
The parity of a binary word is 1 if the number of 1s in the word is odd; otherwise, it is 0.
<p>
For example, the parity of 1011 is 1, and the parity of 10001000 is 0.
<p>
Parity checks are used to detect single bit errors in data storage and communication.
It is fairly straightforward to write code that computes the parity of a single 64-bit word.
<p>
How would you compute the parity of a very large number of 64-bit words?

--

javaskeleton
import java.util.*;

class Solution {
    public static int parity(long x) {
        return 0;
    }
}

--

javatestcase
class GoldenParity {
  public static short parity(long x) {
    short result = 0;
    while (x != 0) {
      result ^= (x & 1);
      x >>= 1;
    }
    return result;
  }
}

class Parity {
  public static void main(String[] args) {
    if (false && args.length == 1) {
      System.out.println("args = " + Arrays.toString(args));
      System.out.println("args[0] = " + args[0]);
      long x = Long.parseLong(args[0]);
      assert(GoldenParity.parity(x) == Solution.parity(x));
      System.out.println("x = " + x + ", parity = " + GoldenParity.parity(x));
    } else {
      Random r = new Random();
      for (int times = 0; times < 1000; ++times) {
        long x = r.nextInt(Integer.MAX_VALUE);
        assert(GoldenParity.parity(x) == Solution.parity(x));
        System.out.println("x = " + x + ", parity = " + GoldenParity.parity(x));
      }
    }
  }
}

--

cplusplusskeleton

int parity(long x) {
}

--

cplusplustestcase
int main(int argc, char** argv) {
    exit(0);
}

--

args
10

--

hint
Be prepared to mask and shift.

--

hint2
Think about the case of many checks.

--

readmore
CLRS Chapter 10 has some good explanations. We also like Bentley's examples.
This <a href="https://graphics.stanford.edu/~seander/bithacks.html">article</a> has an incredible array of bit-fiddling tricks. The Wikipedia article
is also a good resource.
