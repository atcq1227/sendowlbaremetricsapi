package handlers.lambda;

import baremetrics.Customer;
import baremetrics.Plan;
import baremetrics.Subscription;
import handlers.connection.BaremetricsConnectionHandler;
import org.apache.http.HttpResponse;
import sendowl.PresentOrder;
import util.APIConstants;
import util.EmailUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;


public class SubscriptionActiveHandler {
    public String handle(PresentOrder order) {
        try {
            BaremetricsConnectionHandler baremetricsConnectionHandler = new BaremetricsConnectionHandler();

            Plan plan = new Plan()
                    .withOID(order.getProductID())
                    .withName(order.getProductName())
                    .withCurrency(order.getCurrency())
                    .withRecurringPrice(order.getRecurringPrice())
                    .withInterval(order.getFrequencyInterval());

            HttpResponse findPlanResponse = baremetricsConnectionHandler.getSpecificObjectHTTP(APIConstants.BaremetricsPlans, plan.getOID());

            if(findPlanResponse.getStatusLine().getStatusCode() == 200) {
                System.out.println("Plan found with OID: " + plan.getOID());
            } else if (findPlanResponse.getStatusLine().getStatusCode() == 404) {
                System.out.println("Plan not found with OID: " + plan.getOID());
                System.out.println("Creating new plan");

                HttpResponse postNewPlan = baremetricsConnectionHandler.postSubscriptionPlan(plan);

                if(postNewPlan.getStatusLine().getStatusCode() == 200) {
                    System.out.println("Plan successfully posted! OID: " + plan.getOID());
                } else {
                    String error = new BufferedReader(new InputStreamReader(postNewPlan.getEntity().getContent())).readLine();
                    new EmailUtil().sendEmail("Plan post error", error);
                    System.out.println("Error posting new plan");
                    System.out.println("Error: " + error);
                }
            }

            Customer customer = new Customer()
                    .withActive("true")
                    .withOID(order.getBuyerEmail().replace("@", "_").replace(".com", ""))
                    .withEmail(order.getBuyerEmail())
                    .withName(order.getBuyerName());

            HttpResponse findCustomerResponse = baremetricsConnectionHandler.getSpecificObjectHTTP(APIConstants.BaremetricsCustomers, customer.getOID());

            if(findCustomerResponse.getStatusLine().getStatusCode() == 404) {
                System.out.println("Customer with OID: " + customer.getOID() + " not found, creating new customer");

                HttpResponse postCustomerResponse = baremetricsConnectionHandler.postCustomer(customer);

                if(postCustomerResponse.getStatusLine().getStatusCode() == 200) {
                    System.out.println("Customer successfully posted! OID: " + customer.getOID());
                } else {
                    String error = new BufferedReader(new InputStreamReader(postCustomerResponse.getEntity().getContent())).readLine();
                    new EmailUtil().sendEmail("Customer post error", error);
                    System.out.println("Error posting customer with OID: " + customer.getOID());
                    System.out.println("Error: " + error);
                }
            } else if (findCustomerResponse.getStatusLine().getStatusCode() == 200) {
                System.out.println("Found existing customer with OID: " + customer.getOID());
            }

            Subscription subscription = new Subscription()
                    .withOID(plan.getOID() + "_" + customer.getOID() + "_" + order.getCompletedCheckoutAt().replace(":", ""))
                    .withCustomer(customer)
                    .withPlan(plan)
                    .withStartedAt(String.valueOf(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").parse(order.getCompletedCheckoutAt()).getTime() / 1000L));

            HttpResponse postSubscriptionActiveResponse = baremetricsConnectionHandler.postSubscriptionActive(subscription);

            if(postSubscriptionActiveResponse.getStatusLine().getStatusCode() == 200) {
                System.out.println("Subscription successfully posted! OID: " + subscription.getOID());
            } else {
                String error = new BufferedReader(new InputStreamReader(postSubscriptionActiveResponse.getEntity().getContent())).readLine();
                if(error.contains("<HEAD><TITLE>Authorization Required</TITLE></HEAD>") || error.contains("<HEAD><TITLE>Authentication Required</TITLE></HEAD>")) {
                    handle(order);
                } else {
                    new EmailUtil().sendEmail("Subscription post error", error);
                    System.out.println("Error posting subscription with OID: " + subscription.getOID());
                    System.out.println("Error: " + error);
                }
            }

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        return "New subscription active";
    }
}
