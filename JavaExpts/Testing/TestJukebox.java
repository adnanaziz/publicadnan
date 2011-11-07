import junit.framework.*;
import static org.easymock.EasyMock.*;

public class TestJukebox extends TestCase {
  private Jukebox mockJukebox;
  private Jukebox mockJukebox_control;

  @Override
  protected void setUp() {
    // Create a control handle to the Mock object
    mockJukebox_control = createMock(Jukebox.class);

    // And create the mock object itself
    mockJukebox = mockJukebox_control;
  }

  public void testEasyMockDemo() {
    expect(mockJukebox.getCurrentSong()).andReturn("King Crimson -- Epitaph");
    // set up the mock object by calling methods you want to exist
    replay( mockJukebox_control );

    // now it's ready to use
    assertEquals("King Crimson -- Epitaph", mockJukebox.getCurrentSong());
  }
}
