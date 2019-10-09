package handlers.lambda;

import baremetrics.Customer;
import baremetrics.Plan;
import baremetrics.Subscription;
import handlers.BaremetricsConnectionHandler;
import org.apache.http.HttpResponse;
import sendowl.Order;
import util.APIConstants;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class SubscriptionActiveHandler {
    public String handle(Order order) {
        try {
            BaremetricsConnectionHandler baremetricsConnectionHandler = new BaremetricsConnectionHandler();

            Plan plan = new Plan()
                    .withOID(order.getProductID())
                    .withName(order.getProductName());

            if(baremetricsConnectionHandler.getSpecificObjectHTTP(APIConstants.BaremetricsPlans, plan.getOID()).getStatusLine().getStatusCode() == 200) {
                System.out.println("Plan not found");
            }

            Customer customer = new Customer()
                    .withOID(order.getBuyerEmail().replace("@", "_").replace(".com", ""))
                    .withEmail(order.getBuyerEmail())
                    .withName(order.getBuyerName());

            HttpResponse findCustomerResponse = baremetricsConnectionHandler.getSpecificObjectHTTP(APIConstants.BaremetricsCustomers, customer.getOID());

            if(findCustomerResponse.getStatusLine().getStatusCode() == 404) {
                System.out.println(findCustomerResponse.getStatusLine().getStatusCode());
                System.out.println("Customer with OID: " + customer.getOID() + " not found, creating new customer");

                HttpResponse postCustomerResponse = baremetricsConnectionHandler.postCustomer(customer);

                if(postCustomerResponse.getStatusLine().getStatusCode() == 200) {
                    System.out.println("Customer successfully posted! OID: " + customer.getOID());
                } else {
                    System.out.println("Error posting customer with OID: " + customer.getOID());
                    System.out.println("Error: " + new BufferedReader(new InputStreamReader(postCustomerResponse.getEntity().getContent())).readLine());
                }
            } else if (findCustomerResponse.getStatusLine().getStatusCode() == 200) {
                System.out.println("Found existing customer with OID: " + customer.getOID());
            }

            Subscription subscription = new Subscription()
                    .withOID(plan.getOID() + "_" + customer.getOID())
                    .withCustomer(customer)
                    .withPlan(plan)
                    .startedNow();

            HttpResponse postSubscriptionActiveResponse = baremetricsConnectionHandler.postSubscriptionActive(subscription);

            if(postSubscriptionActiveResponse.getStatusLine().getStatusCode() == 200) {
                System.out.println("Subscription successfully posted! OID: " + subscription.getOID());
            } else {
                System.out.println("Error posting subscription with OID: " + subscription.getOID());
                System.out.println("Error: " + new BufferedReader(new InputStreamReader(postSubscriptionActiveResponse.getEntity().getContent())).readLine());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return "New subscription active";
    }
}
