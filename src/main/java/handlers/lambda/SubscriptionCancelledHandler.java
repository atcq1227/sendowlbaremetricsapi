package handlers.lambda;

import baremetrics.Customer;
import baremetrics.Plan;
import baremetrics.Subscription;
import handlers.connection.BaremetricsConnectionHandler;
import org.apache.http.HttpResponse;
import sendowl.Order;
import sendowl.PresentOrder;
import util.EmailUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class SubscriptionCancelledHandler {
    public String handle(Order order) {
        try {
            BaremetricsConnectionHandler baremetricsConnectionHandler = new BaremetricsConnectionHandler();

            Plan plan = new Plan()
                    .withOID(order.getProductID())
                    .withName(order.getProductName());

            Customer customer = new Customer()
                    .withOID(order.getBuyerEmail().replace("@", "_").replace(".com", ""))
                    .withEmail(order.getBuyerEmail())
                    .withName(order.getBuyerName());

            Subscription subscription = new Subscription()
                    .withOID(plan.getOID() + "_" + customer.getOID() + "_" + order.getCompletedCheckoutAt())
                    .withCustomer(customer)
                    .withPlan(plan);

            HttpResponse putSubscriptionCancelledResponse = baremetricsConnectionHandler.putSubscriptionCancelled(subscription);

            if(putSubscriptionCancelledResponse.getStatusLine().getStatusCode() == 200) {
                System.out.println("Subscription cancelled with OID: " + subscription.getOID());
            } else {
                String error = new BufferedReader(new InputStreamReader(putSubscriptionCancelledResponse.getEntity().getContent())).readLine();
                if(error.contains("<HEAD><TITLE>Authorization Required</TITLE></HEAD>") || error.contains("<HEAD><TITLE>Authentication Required</TITLE></HEAD>")) {
                    handle(order);
                } else {
                    new EmailUtil().sendEmail("Subscription cancelled put error", error);
                    System.out.println("Error cancelling subscription with OID: " + subscription.getOID());
                    System.out.println("Error: " + error);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "New subscription cancelled";
    }
}
