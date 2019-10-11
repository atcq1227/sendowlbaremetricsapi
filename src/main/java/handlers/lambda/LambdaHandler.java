package handlers.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import sendowl.Order;
import util.APIConstants;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class LambdaHandler implements RequestHandler<InputStream, String> {
    public String handleRequest(InputStream webHookRequest, Context context) {
        try {
            Order order = new Order(new BufferedReader(new InputStreamReader(webHookRequest)).readLine());

            System.out.println("New order: " + order.getOrderBody());

            if(order.getForSubscription()) {
                if(order.getState().equals(APIConstants.SubscriptionActiveMessage)) {
                    new SubscriptionActiveHandler().handle(order);
                } else if(order.getState().equals(APIConstants.SubscriptionCancelledMessage)) {
                    new SubscriptionCancelledHandler().handle(order);
                } else if(order.getState().equals(APIConstants.WebhookTestMessage)) {
                    System.out.println("Received webhook test");
                } else {
                    System.out.println("Unknown order state: " + order.getState());
                }
            } else if(!order.getForSubscription()) {
                new ChargeHandler().handle(order);
            } else {
                System.out.println("Error in order");
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }
}
