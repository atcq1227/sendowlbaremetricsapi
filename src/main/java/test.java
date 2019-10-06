import handlers.SendOwlGetHandler;
import handlers.event.SubscriptionActive;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Date;

public class test {
    public static void main(String[] args)  {
        Long timestamp = new Date().getTime();

        Date date = new Date(timestamp);

        System.out.println(timestamp);
        System.out.println(date);
    }
}
