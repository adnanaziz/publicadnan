// Example 23 from page 19 of Java Precisely second edition (The MIT Press 2005)
// Author: Peter Sestoft (sestoft@itu.dk)


class Example23 {
  public static void main(String[] args) {
    String ecoliK12MG1655  =
       "AGCTTTTCATTCTGACTGCAACGGGCAATATGTCTCTGTGTGGATTAAAAAAAGAGTGTCTGATAGCAGC"
      +"TTCTGAACTGGTTACCTGCCGTGAGTAAATTAAAATTTTATTGACTTAGGTCACTAAATACTTTAACCAA"
      +"TATAGGCATAGCGCACAGACAGATAAAAATTACAGAGTACACAACATCCATGAAACGCATTAGCACCACC"
      +"ATTACCACCACCATCACCATTACCACAGGTAACGGTGCGGGCTGACGCGTACAGGAAACACAGAAAAAAG"
      +"CCCGCACCTGACAGTGCGGGCTTTTTTTTTCGACCAAAGGTAACGAGGTAACAACCATGCGAGTGTTGAA"
      +"GTTCGGCGGTACATCAGTGGCAAATGCAGAACGTTTTCTGCGTGTTGCCGATATTCTGGAAAGCAATGCC"
      +"AGGCAGGGGCAGGTGGCCACCGTCCTCTCTGCCCCCGCCAAAATCACCAACCACCTGGTGGCGATGATTG"
      +"AAAAAACCATTAGCGGCCAGGATGCTTTACCCAATATCAGCGATGCCGAACGTATTTTTGCCGAACTTTT";
    codonfreq(ecoliK12MG1655);
  }

  static void codonfreq(String s) {
    // The translation from nucleotide ACGTacgt to index 0123
    int[] fromNuc = new int[128];
    for (int i=0; i<fromNuc.length; i++)
      fromNuc[i] = -1;
    fromNuc['a'] = fromNuc['A'] = 0; fromNuc['c'] = fromNuc['C'] = 1;
    fromNuc['g'] = fromNuc['G'] = 2; fromNuc['t'] = fromNuc['T'] = 3;
    // Count frequencies of codons (triples of nucleotides)
    int[][][] freq = new int[4][4][4];
    for (int i=0; i+2<s.length(); i+=3) {
      int nuc1 = fromNuc[s.charAt(i)];
      int nuc2 = fromNuc[s.charAt(i+1)];
      int nuc3 = fromNuc[s.charAt(i+2)];
      freq[nuc1][nuc2][nuc3] += 1;
    }
    // The translation from index 0123 to nucleotide ACGT
    final char[] toNuc = { 'A', 'C', 'G', 'T' };
    for (int i=0; i<4; i++) 
      for (int j=0; j<4; j++) {
        for (int k=0; k<4; k++)
          System.out.print(" "+toNuc[i]+toNuc[j]+toNuc[k] 
                           +": " + freq[i][j][k]);
        System.out.println();
      }
  }
}

