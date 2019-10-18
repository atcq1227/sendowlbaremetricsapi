package handlers.lambda;

import baremetrics.Customer;
import baremetrics.Plan;
import baremetrics.Subscription;
import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;
import handlers.connection.BaremetricsConnectionHandler;
import org.apache.http.HttpResponse;
import util.APIConstants;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BackloadHandler {
    public void handle() {
        Fillo fillo = new Fillo();

        try {
            Connection connection = fillo.getConnection("C:\\Users\\E671532\\Documents\\orders.xlsx");

            String query = "Select * from orders_report";

            Recordset rs = connection.executeQuery(query);

            while(rs.next()) {
                if(rs.getField("State").equals("Subscription Active") || rs.getField("State").equals("Subscription Cancelling")) {
                    handleActiveSubscription(rs);
                } else if(rs.getField("State").equals("Subscription Cancelled")) {
                    handleCancelledSubscription(rs);
                }
            }

        } catch (FilloException e) {
            e.printStackTrace();
        }
    }

    private void handleActiveSubscription(Recordset rs) throws FilloException {
        try {
            BaremetricsConnectionHandler baremetricsConnectionHandler = new BaremetricsConnectionHandler();

            String OID = null;
            String recurringPrice = null;
            String interval = null;

            if(rs.getField("Item Name").equals("Playin' it safe.")) {
                OID = "7489";
                recurringPrice = "15.00";
                interval = "month";
            } else if(rs.getField("Item Name").equals("Goin' steady.")) {
                OID = "7491";
                recurringPrice = "135.00";
                interval = "year";
            } else {
                OID = "8308";
                recurringPrice = "15.00";
                interval = "month";
            }

            Plan plan = new Plan()
                    .withOID(OID)
                    .withName(rs.getField("Item Name"))
                    .withCurrency(rs.getField("Currency"))
                    .withRecurringPrice(recurringPrice)
                    .withInterval(interval);

            HttpResponse findPlanResponse = baremetricsConnectionHandler.getSpecificObjectHTTP(APIConstants.BaremetricsPlans, plan.getOID());

            if (findPlanResponse.getStatusLine().getStatusCode() == 200) {
                System.out.println("Plan found with OID: " + plan.getOID());
            } else if (findPlanResponse.getStatusLine().getStatusCode() == 404) {
                System.out.println("Plan not found with OID: " + plan.getOID());
                System.out.println("Creating new plan");

                HttpResponse postNewPlan = baremetricsConnectionHandler.postSubscriptionPlan(plan);

                if (postNewPlan.getStatusLine().getStatusCode() == 200) {
                    System.out.println("Plan successfully posted! OID: " + plan.getOID());
                } else {
                    System.out.println("Error posting new plan");
                    System.out.println("Error: " + new BufferedReader(new InputStreamReader(postNewPlan.getEntity().getContent())).readLine());
                }
            }

            Customer customer = new Customer()
                    .withActive("true")
                    .withOID(rs.getField("Buyer Email").replace("@", "_").replace(".com", ""))
                    .withEmail(rs.getField("Buyer Email"))
                    .withName(rs.getField("Buyer Name"));

            HttpResponse findCustomerResponse = baremetricsConnectionHandler.getSpecificObjectHTTP(APIConstants.BaremetricsCustomers, customer.getOID());

            if (findCustomerResponse.getStatusLine().getStatusCode() == 404) {
                System.out.println("Customer with OID: " + customer.getOID() + " not found, creating new customer");

                HttpResponse postCustomerResponse = baremetricsConnectionHandler.postCustomer(customer);

                if (postCustomerResponse.getStatusLine().getStatusCode() == 200) {
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
                    .withStartedAt(rs.getField("Order date/time"));

            HttpResponse postSubscriptionActiveResponse = baremetricsConnectionHandler.postSubscriptionActive(subscription);

            if (postSubscriptionActiveResponse.getStatusLine().getStatusCode() == 200) {
                System.out.println("Subscription successfully posted! OID: " + subscription.getOID());
            } else {
                System.out.println("Error posting subscription with OID: " + subscription.getOID());
                System.out.println("Error: " + new BufferedReader(new InputStreamReader(postSubscriptionActiveResponse.getEntity().getContent())).readLine());
            }

        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private void handleCancelledSubscription(Recordset rs) throws FilloException {
        try {
            BaremetricsConnectionHandler baremetricsConnectionHandler = new BaremetricsConnectionHandler();

            String OID = null;
            String recurringPrice = null;
            String interval = null;

            if(rs.getField("Item Name").equals("Playin' it safe.")) {
                OID = "7489";
                recurringPrice = "15.00";
                interval = "month";
            } else if(rs.getField("Item Name").equals("Goin' steady.")) {
                OID = "7491";
                recurringPrice = "135.00";
                interval = "year";
            } else {
                OID = "8308";
                recurringPrice = "15.00";
                interval = "month";
            }

            Plan plan = new Plan()
                    .withOID(OID)
                    .withName(rs.getField("Item Name"));

            Customer customer = new Customer()
                    .withOID(rs.getField("Buyer Email").replace("@", "_").replace(".com", ""))
                    .withEmail(rs.getField("Buyer Email"))
                    .withName(rs.getField("Buyer Name"));

            Subscription subscription = new Subscription()
                    .withOID(plan.getOID() + "_" + customer.getOID())
                    .withCustomer(customer)
                    .withPlan(plan)
                    .startedNow();

            HttpResponse putSubscriptionCancelledResponse = baremetricsConnectionHandler.putSubscriptionCancelled(subscription);

            if(putSubscriptionCancelledResponse.getStatusLine().getStatusCode() == 200) {
                System.out.println("Subscription cancelled with OID: " + subscription.getOID());
            } else {
                System.out.println("Error cancelling subscription with OID: " + subscription.getOID());
                System.out.println("Error: " + new BufferedReader(new InputStreamReader(putSubscriptionCancelledResponse.getEntity().getContent())).readLine());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
