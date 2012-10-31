public class Posting {
  private long id;
  private String author;
  private String subject;
  private String body;
  private long authorLikes;
  private long postingUpvotes;
  public Posting() {
    // no argument constructor
  }
  public Posting( long id, String author, String subject, String body, long authorLikes, long postingUpvotes ) {
    this.id = id;
    this.author = author;
    this.subject = subject;
    this.body = body;
    this.authorLikes = authorLikes;
    this.postingUpvotes = postingUpvotes;
  }

  public Posting setAuthor(String author) { this.author = author; return this; }
  public Posting setId(long id) { this.id = id; return this; }

  public String getAuthor() { return author; }
  public long getId() { return id; }
  public long getAuthorLikes() { return authorLikes; }
  public long getUpvotes() { return postingUpvotes; }
  public String getSubject() { return subject; }
  public String getBody() { return body; }

  @Override
  public boolean equals(Object o) {
    if ( !(o instanceof Posting) ) {
      return false;
    }
    Posting po = (Posting) o;
    return ( po.id == id && po.author.equals(author) && po.subject.equals(subject) 
                   && po.body.equals(body) && po.authorLikes == authorLikes && po.postingUpvotes == postingUpvotes );
  }

  @Override public int hashCode() {
    return 42; // override in meaningful way if used in hashset or as hashmap key
  }
}
