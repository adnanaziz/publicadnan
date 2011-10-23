import junit.framework.*;
// import static org.easymock.MockControl.*;
import static org.easymock.EasyMock.*;

public class TestJukebox extends TestCase {
  private Jukebox mockJukebox;
  // private MockControl mockJukebox_control;
  private Jukebox mockJukebox_control;

  @Override
  protected void setUp() {
    // Create a control handle to the Mock object
    // mockJukebox_control = MockControl.createControl(Jukebox.class);
    mockJukebox_control = createMock(Jukebox.class);
    System.out.println("createMock returned " + mockJukebox_control.toString() );

    // And create the mock object itself
    // mockJukebox = (Jukebox) mockJukebox_control.getMock();
    mockJukebox = mockJukebox_control;
  }

  public void testEasyMockDemo() {
    expect(mockJukebox.getCurrentSong()).andReturn("King Crimson -- Epitaph");
    // set up the mock object by calling methods you
    // want to exist
    // mockJukebox.getCurrentSong();
    // mockJukebox_control.setReturnValue("King Crimson -- Epitaph");
    // expect("King Crimson -- Epitaph");

    // you don't have to worry about the other dozen methods 
    // defined in Jukebox

    // switch from record to playback
    // mockJukebox_control.replay();
    replay( mockJukebox_control );

    // now it's ready to use
    assertEquals("King Crimson -- Epitaph", mockJukebox.getCurrentSong());
  }
}
