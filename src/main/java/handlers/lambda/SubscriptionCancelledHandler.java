package handlers.lambda;

import baremetrics.Customer;
import baremetrics.Plan;
import baremetrics.Subscription;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import handlers.BaremetricsConnectionHandler;
import org.apache.http.HttpResponse;
import sendowl.Order;
import util.APIConstants;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class SubscriptionCancelledHandler {
    public String handle(Order order) {
        try {
            BaremetricsConnectionHandler baremetricsConnectionHandler = new BaremetricsConnectionHandler();

            Plan plan = new Plan()
                    .withOID(order.getProductID())
                    .withName(order.getProductName());

            Customer customer = new Customer()
                    .withActive(false)
                    .withOID(order.getBuyerEmail().replace("@", "_").replace(".com", ""))
                    .withEmail(order.getBuyerEmail())
                    .withName(order.getBuyerName());

            Subscription subscription = new Subscription()
                    .withOID(plan.getOID() + "_" + customer.getOID())
                    .withCustomer(customer)
                    .withPlan(plan)
                    .startedNow();

            HttpResponse putSubscriptionCancelledResponse = baremetricsConnectionHandler.putSubscriptionCancelled(subscription);

            if(putSubscriptionCancelledResponse.getStatusLine().getStatusCode() == 200) {
                System.out.println("Subscription cancelled with OID: " + subscription.getOID());
            } else {
                System.out.println("Error cancelled subscription with OID: " + subscription.getOID());
                System.out.println("Error: " + new BufferedReader(new InputStreamReader(putSubscriptionCancelledResponse.getEntity().getContent())).readLine());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return "New subscription cancelled";
    }
}
