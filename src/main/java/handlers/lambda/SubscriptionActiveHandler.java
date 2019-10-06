package handlers.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import handlers.event.SubscriptionActive;
import util.Debugger;
import util.JsonUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class SubscriptionActiveHandler implements RequestHandler<InputStream, String> {
    public String handleRequest(InputStream webHookRequest, Context context) {
        Debugger.logNewSubscriptionActive();

        try {
            new SubscriptionActive().handle(webHookRequest);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "New subscription active";
    }
}
