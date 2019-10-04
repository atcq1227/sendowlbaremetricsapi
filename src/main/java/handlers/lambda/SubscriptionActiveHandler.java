package handlers.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import handlers.event.SubscriptionActive;
import util.Debugger;

import java.io.InputStream;

public class SubscriptionActiveHandler implements RequestHandler<InputStream, String> {
    public String handleRequest(InputStream webHookRequest, Context context) {
        Debugger.logNewSubscriptionActive();

        new SubscriptionActive().handle(webHookRequest);

        return "New subscription active";
    }
}
