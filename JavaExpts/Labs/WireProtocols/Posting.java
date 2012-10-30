public class Posting {
  private long id;
  private String author;
  private String subject;
  private String body;
  private long authorLikes;
  private long postingVotes;
  public Posting() {
    // no argument constructor
  }
  public Posting( long id, String author, String subject, String body, long authorLikes, long postingVotes ) {
    this.id = id;
    this.author = author;
    this.subject = subject;
    this.body = body;
    this.authorLikes = authorLikes;
    this.postingVotes = postingVotes;
  }

  public Posting setAuthor(String author) { this.author = author; return this; }

  @Override
  public boolean equals(Object o) {
    if ( !(o instanceof Posting) ) {
      return false;
    }
    Posting po = (Posting) o;
    return ( po.id == id && po.author.equals(author) && po.subject.equals(subject) 
                   && po.body.equals(body) && po.authorLikes == authorLikes && po.postingVotes == postingVotes );
  }

  @Override public int hashCode() {
    return 42; // override in meaningful way if used in hashset or as hashmap key
  }
}
