package handlers.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import util.Debugger;

import java.io.InputStream;

public class SubscriptionActiveHandler extends LambdaHandler {
    public String handleRequest(InputStream webHookRequest, Context context) {
        System.out.println("New subscription active");

        Debugger.logInputStream(webHookRequest);

        return "{statusCode: 200}";
    }
}
