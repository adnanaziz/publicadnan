import com.google.common.eventbus.*;

class Receiver {
  String name;
  @Subscribe
  public void recieveMessage(String message) {
    System.out.println(name + " received " + message);
  }
  public Receiver( String s ) {
    name = s;
  }
}

public class PublishSubscribe {
  public static void main(String[] args) {
    Receiver r1 = new Receiver("r1");
    Receiver r2 = new Receiver("r2");
    EventBus eb = new EventBus();
    eb.register(r1);
    eb.register(r2);
    eb.post("Hi!");
    eb.unregister(r1);
    eb.post("Bye!");
  }
}
