package handlers.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import sendowl.PresentOrder;
import util.APIConstants;
import util.EmailUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class LambdaHandler implements RequestHandler<InputStream, String> {
    public String handleRequest(InputStream webHookRequest, Context context) {
        try {
            PresentOrder presentOrder = new PresentOrder(new BufferedReader(new InputStreamReader(webHookRequest)).readLine());

            System.out.println("New order: " + presentOrder.getOrderBody());

            if(presentOrder.getForSubscription()) {
                if(presentOrder.getState().equals(APIConstants.SubscriptionActiveMessage)) {
                    new SubscriptionActiveHandler().handle(presentOrder);
                } else if(presentOrder.getState().equals(APIConstants.SubscriptionCancelledMessage)) {
                    new SubscriptionCancelledHandler().handle(presentOrder);
                } else if(presentOrder.getState().equals(APIConstants.WebhookTestMessage)) {
                    System.out.println("Received webhook test");
                } else {
                    System.out.println("Unknown order state: " + presentOrder.getState());
                }
            } else if(!presentOrder.getForSubscription()) {
                new ChargeHandler().handle(presentOrder);
            } else {
                new EmailUtil().sendEmail("Lambda handler error", "State not found");
                System.out.println("Error in order");
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }
}
