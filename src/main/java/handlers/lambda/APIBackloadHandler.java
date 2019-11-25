package handlers.lambda;

import baremetrics.Customer;
import baremetrics.Plan;
import baremetrics.Subscription;
import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Recordset;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import handlers.connection.BaremetricsConnectionHandler;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import sendowl.Order;
import util.APIConstants;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class APIBackloadHandler {
    public void handle() throws IOException, InterruptedException {
        int pageNumber = 0;

        String url = "https://4ab76f6325e7eb2:6ca56c1843e7c63e9d35@www.sendowl.com/api/v1_3/orders?per_page=50&page=" + pageNumber;

        HttpClient httpClient = new DefaultHttpClient();

        String responseString = "123456";

        while(responseString.length() > 5) {

            HttpGet get = new HttpGet(url);

            get.addHeader("Accept", "application/json");
            get.addHeader("per_page", "50");
            get.addHeader("page", Integer.toString(pageNumber));

            HttpResponse response = httpClient.execute(get);


            System.out.println("Response Code : " +
                    response.getStatusLine().getStatusCode());

//            responseString = new BufferedReader(new InputStreamReader(response.getEntity().getContent())).readLine();
//
//            JsonArray array = new JsonParser().parse(responseString).getAsJsonArray();
//
//            array.forEach(object -> {
//                Order order = new Order(object.getAsJsonObject().toString());
//
//                if(order.getState().equals("subscription_active")) {
//                    handleActiveSubscription(order);
//                }
//            });
//
//            pageNumber++;
//
//            url = "https://4ab76f6325e7eb2:6ca56c1843e7c63e9d35@www.sendowl.com/api/v1_3/orders?per_page=50&page=" + pageNumber;
//
//            System.out.println(responseString.length());
//
//            Thread.sleep(1000);
        }
    }

    private void handleActiveSubscription(Order order) {
        try {
            BaremetricsConnectionHandler baremetricsConnectionHandler = new BaremetricsConnectionHandler();

            String planName = new BufferedReader(new InputStreamReader(baremetricsConnectionHandler.getSpecificObjectHTTP("plans", order.getBackloadProductID()).getEntity().getContent())).readLine();

            Plan plan = new Plan()
                    .withOID(order.getBackloadProductID());

            if(plan.getOID().equals("0")) {
                plan.setName("Playin' It Safe. ($1 TRIAL DISCONTINUED)");
            }

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
                    System.out.println("Error posting new plan");
                    System.out.println("Error: " + new BufferedReader(new InputStreamReader(postNewPlan.getEntity().getContent())).readLine());
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
                    .withStartedAt(order.getCompletedCheckoutAt());

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
