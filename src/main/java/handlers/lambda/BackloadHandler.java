package handlers.lambda;

import baremetrics.Customer;
import baremetrics.Plan;
import baremetrics.Subscription;
import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import handlers.connection.BaremetricsConnectionHandler;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import util.APIConstants;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BackloadHandler {
    public void handle() {
        Fillo fillo = new Fillo();

        try {
            Connection connection = fillo.getConnection("/Users/matthewshaw/Downloads/orders_report.xlsx");

            String query = "Select * from Worksheet";

            System.out.println(connection.getMetaData().getTableNames());

            Recordset rs = connection.executeQuery(query);

            while(rs.next()) {
                if(rs.getField("State").equals("Subscription Active") || rs.getField("State").equals("Subscription Cancelling")) {
                    handleActiveSubscription(rs);
                } else if(rs.getField("State").equals("Subscription Cancelled")) {
                    handleCancelledSubscription(rs);
                }

                Thread.sleep(1000);
            }

        } catch (FilloException | InterruptedException e) {
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
                recurringPrice = "1500";
                interval = "month";
            } else if(rs.getField("Item Name").equals("Goin' steady.")) {
                OID = "7491";
                recurringPrice = "13500";
                interval = "year";
            } else {
                OID = "8308";
                recurringPrice = "1500";
                interval = "month";
            }

            Plan plan = new Plan()
                    .withOID(OID)
                    .withName(rs.getField("Item Name"))
                    .withCurrency(rs.getField("Currency"))
                    .withRecurringPrice(recurringPrice)
                    .withInterval(interval)
                    .withCreated(String.valueOf(new SimpleDateFormat("yyyy-MM-ddHH:mm:ss").parse(rs.getField("Order date/time")).getTime() / 1000L));

            HttpResponse findPlanResponse = baremetricsConnectionHandler.getSpecificObjectHTTP(APIConstants.BaremetricsPlans, plan.getOID());

            if (findPlanResponse.getStatusLine().getStatusCode() == 200) {
                System.out.println("Plan found with OID: " + plan.getOID());
            } else if (findPlanResponse.getStatusLine().getStatusCode() == 404) {
                System.out.println("Plan not found with OID: " + plan.getOID());
                System.out.println("Creating new plan");

                HttpResponse postNewPlan = baremetricsConnectionHandler.postBackloadSubscriptionPlan(plan);

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
                    .withName(rs.getField("Buyer Name"))
                    .withCreated(String.valueOf(new SimpleDateFormat("yyyy-MM-ddHH:mm:ss").parse(rs.getField("Order date/time")).getTime() / 1000L));

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
                    .withOID(plan.getOID() + "_" + customer.getOID() + "_" + String.valueOf(new SimpleDateFormat("yyyy-MM-ddHH:mm:ss").parse(rs.getField("Order date/time")).getTime() / 1000L))
                    .withCustomer(customer)
                    .withPlan(plan)
                    .withStartedAt(String.valueOf(new SimpleDateFormat("yyyy-MM-ddHH:mm:ss").parse(rs.getField("Order date/time")).getTime() / 1000L));

            HttpResponse postSubscriptionActiveResponse = baremetricsConnectionHandler.postSubscriptionActive(subscription);

            if (postSubscriptionActiveResponse.getStatusLine().getStatusCode() == 200) {
                System.out.println("Subscription successfully posted! OID: " + subscription.getOID());
            } else {
                System.out.println("Error posting subscription with OID: " + subscription.getOID());
                System.out.println("Error: " + new BufferedReader(new InputStreamReader(postSubscriptionActiveResponse.getEntity().getContent())).readLine());
            }

        } catch(IOException | ParseException e) {
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
                recurringPrice = "1500";
                interval = "month";
            } else if(rs.getField("Item Name").equals("Goin' steady.")) {
                OID = "7491";
                recurringPrice = "13500";
                interval = "year";
            } else {
                OID = "8308";
                recurringPrice = "1500";
                interval = "month";
            }

            Plan plan = new Plan()
                    .withOID(OID)
                    .withName(rs.getField("Item Name"))
                    .withCurrency(rs.getField("Currency"))
                    .withRecurringPrice(recurringPrice)
                    .withInterval(interval)
                    .withCreated(String.valueOf(new SimpleDateFormat("yyyy-MM-ddHH:mm:ss").parse(rs.getField("Order date/time")).getTime() / 1000L));

            HttpResponse findPlanResponse = baremetricsConnectionHandler.getSpecificObjectHTTP(APIConstants.BaremetricsPlans, plan.getOID());

            if (findPlanResponse.getStatusLine().getStatusCode() == 200) {
                System.out.println("Plan found with OID: " + plan.getOID());
            } else if (findPlanResponse.getStatusLine().getStatusCode() == 404) {
                System.out.println("Plan not found with OID: " + plan.getOID());
                System.out.println("Creating new plan");

                HttpResponse postNewPlan = baremetricsConnectionHandler.postBackloadSubscriptionPlan(plan);

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
                    .withName(rs.getField("Buyer Name"))
                    .withCreated(String.valueOf(new SimpleDateFormat("yyyy-MM-ddHH:mm:ss").parse(rs.getField("Order date/time")).getTime() / 1000L));

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
                    .withOID(plan.getOID() + "_" + customer.getOID() + "_" + String.valueOf(new SimpleDateFormat("yyyy-MM-ddHH:mm:ss").parse(rs.getField("Order date/time")).getTime() / 1000L))
                    .withCustomer(customer)
                    .withPlan(plan)
                    .withStartedAt(String.valueOf(new SimpleDateFormat("yyyy-MM-ddHH:mm:ss").parse(rs.getField("Order date/time")).getTime() / 1000L))
                    .withCancelledAt(String.valueOf(new SimpleDateFormat("yyyy-MM-ddHH:mm:ss").parse(rs.getField("Subscription Cancelled At")).getTime() / 1000L));

            HttpResponse postSubscriptionActiveResponse = baremetricsConnectionHandler.postBackloadSubscriptionCancelled(subscription);

            if (postSubscriptionActiveResponse.getStatusLine().getStatusCode() == 200) {
                System.out.println("Subscription successfully posted! OID: " + subscription.getOID());
            } else {
                System.out.println("Error posting subscription with OID: " + subscription.getOID());
                System.out.println("Error: " + new BufferedReader(new InputStreamReader(postSubscriptionActiveResponse.getEntity().getContent())).readLine());
            }

        } catch(IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    public void deleteAllSubscriptions() {
        String subscriptions;

        JsonArray ordersArray;

        try {

            do {
                HttpClient httpClient = new DefaultHttpClient();

                HttpGet get = new HttpGet("https://api.baremetrics.com/v1/5684c900-a29c-4ca9-8ac4-f85b68288011/subscriptions");

                get.addHeader("Authorization", APIConstants.BaremetricsAPIKey);

                subscriptions = new BufferedReader(new InputStreamReader(httpClient.execute(get).getEntity().getContent())).readLine();

                ordersArray = new JsonParser().parse(subscriptions).getAsJsonObject().get("subscriptions").getAsJsonArray();

                System.out.println(ordersArray.size());

                ordersArray.forEach(order -> {
                    String oid = order.getAsJsonObject().get("oid").getAsString();

                    HttpClient lambdaHttpClient = new DefaultHttpClient();

                    HttpDelete delete = new HttpDelete("https://api.baremetrics.com/v1/5684c900-a29c-4ca9-8ac4-f85b68288011/subscriptions/" + oid);

                    delete.addHeader("Authorization", APIConstants.BaremetricsAPIKey);

                    try {
                        System.out.println(lambdaHttpClient.execute(delete).getStatusLine());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

            } while(ordersArray.size() > 0);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteAllPlans() {
        String subscriptions;

        JsonArray ordersArray;

        try {

            do {
                HttpClient httpClient = new DefaultHttpClient();

                HttpGet get = new HttpGet("https://api.baremetrics.com/v1/5684c900-a29c-4ca9-8ac4-f85b68288011/plans");

                get.addHeader("Authorization", APIConstants.BaremetricsAPIKey);

                subscriptions = new BufferedReader(new InputStreamReader(httpClient.execute(get).getEntity().getContent())).readLine();

                ordersArray = new JsonParser().parse(subscriptions).getAsJsonObject().get("plans").getAsJsonArray();

                System.out.println(ordersArray.size());

                ordersArray.forEach(order -> {
                    String oid = order.getAsJsonObject().get("oid").getAsString();
                    System.out.println("Plan OID: " + oid);

                    HttpClient lambdaHttpClient = new DefaultHttpClient();

                    HttpDelete delete = new HttpDelete("https://api.baremetrics.com/v1/5684c900-a29c-4ca9-8ac4-f85b68288011/plans/" + oid);

                    delete.addHeader("Authorization", APIConstants.BaremetricsAPIKey);

                    try {
                        System.out.println(lambdaHttpClient.execute(delete).getStatusLine());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

            } while(ordersArray.size() > 0);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteAllCustomers() {
        String subscriptions;

        JsonArray ordersArray;

        try {

            do {
                HttpClient httpClient = new DefaultHttpClient();

                HttpGet get = new HttpGet("https://api.baremetrics.com/v1/5684c900-a29c-4ca9-8ac4-f85b68288011/customers");

                get.addHeader("Authorization", APIConstants.BaremetricsAPIKey);

                subscriptions = new BufferedReader(new InputStreamReader(httpClient.execute(get).getEntity().getContent())).readLine();

                ordersArray = new JsonParser().parse(subscriptions).getAsJsonObject().get("customers").getAsJsonArray();

                System.out.println(ordersArray.size());

                ordersArray.forEach(order -> {
                    String oid = order.getAsJsonObject().get("oid").getAsString();

                    HttpClient lambdaHttpClient = new DefaultHttpClient();

                    HttpDelete delete = new HttpDelete("https://api.baremetrics.com/v1/5684c900-a29c-4ca9-8ac4-f85b68288011/customers/" + oid);

                    delete.addHeader("Authorization", APIConstants.BaremetricsAPIKey);

                    try {
                        System.out.println(lambdaHttpClient.execute(delete).getStatusLine());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

            } while(ordersArray.size() > 0);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteAllCharges() {
        String subscriptions;

        JsonArray ordersArray;

        try {

            int pageNumber = 68;

            do {
                HttpClient httpClient = new DefaultHttpClient();

                HttpGet get = new HttpGet("https://api.baremetrics.com/v1/5684c900-a29c-4ca9-8ac4-f85b68288011/charges?per_page=50&page=" + pageNumber);

                get.addHeader("Authorization", APIConstants.BaremetricsAPIKey);

                subscriptions = new BufferedReader(new InputStreamReader(httpClient.execute(get).getEntity().getContent())).readLine();

                ordersArray = new JsonParser().parse(subscriptions).getAsJsonObject().get("charges").getAsJsonArray();

                System.out.println(ordersArray.size());

                ordersArray.forEach(order -> {
                    String oid = order.getAsJsonObject().get("oid").getAsString();

                    if(!oid.contains(" ")) {

                        System.out.println(oid);

                        HttpClient lambdaHttpClient = new DefaultHttpClient();

                        HttpDelete delete = new HttpDelete("https://api.baremetrics.com/v1/5684c900-a29c-4ca9-8ac4-f85b68288011/charges/" + oid);

                        delete.addHeader("Authorization", APIConstants.BaremetricsAPIKey);

                        try {
                            System.out.println(lambdaHttpClient.execute(delete).getStatusLine());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });

                pageNumber--;

            } while(ordersArray.size() > 0);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
