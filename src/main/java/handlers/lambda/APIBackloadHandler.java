package handlers.lambda;

import baremetrics.Customer;
import baremetrics.Plan;
import baremetrics.Subscription;
import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Recordset;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import handlers.connection.BaremetricsConnectionHandler;
import handlers.connection.SendOwlConnectionHandler;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import sendowl.Order;
import sendowl.PresentOrder;
import sendowl.PastOrder;
import util.APIConstants;
import util.JsonUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class APIBackloadHandler {

    public void handle() throws IOException, InterruptedException {
        int pageNumber = 0;

        String url = "https://4ab76f6325e7eb2:6ca56c1843e7c63e9d35@www.sendowl.com/api/v1_3/orders?per_page=50&page=" + pageNumber;

        boolean morePages = true;

        while(morePages) {

            HttpClient httpClient = new DefaultHttpClient();

            HttpGet get = new HttpGet(url);

            get.addHeader("Accept", "application/json");
            get.addHeader("per_page", "50");
            get.addHeader("page", Integer.toString(pageNumber));

            HttpResponse response = httpClient.execute(get);

            String responseString = new BufferedReader(new InputStreamReader(response.getEntity().getContent())).readLine();

            System.out.println(responseString);

            JsonArray array = new JsonParser().parse(responseString).getAsJsonArray();

            if(array.size() > 0) {
                for (JsonElement element : array) {
                    PastOrder order = new PastOrder(element.getAsJsonObject().get("order").toString());

                    if(order.getState().equals("subscription_active")) {
                        handleActiveSubscription(order);
                    } else if(order.getState().equals("subscription_cancelled")) {
                        handleCancelledSubscription(order);
                    }
//                    } else if(order.getState().equals("complete")) {
//                        new ChargeHandler().handle(order);
//                    }
//
//                    if(order.getState().equals("complete")) {
//                        new ChargeHandler().handle(order);
//                    }
                }
            } else {
                morePages = false;
            }

            pageNumber++;

            url = "https://4ab76f6325e7eb2:6ca56c1843e7c63e9d35@www.sendowl.com/api/v1_3/orders?per_page=50&page=" + pageNumber;
        }
    }

    private void handleActiveSubscription(PastOrder order) {
        try {
            BaremetricsConnectionHandler baremetricsConnectionHandler = new BaremetricsConnectionHandler();

            SendOwlConnectionHandler sendOwlConnectionHandler = new SendOwlConnectionHandler();

            String specificPlan = sendOwlConnectionHandler.getSpecificSubscriptionJSON(order.getBackloadProductID());

            Plan plan = new Plan()
                    .withOID(order.getBackloadProductID())
                    .withName(JsonUtil.searchableBody(specificPlan).get("subscription").getAsJsonObject().get("name").getAsString())
                    .withRecurringPrice(JsonUtil.searchableBody(specificPlan).get("subscription").getAsJsonObject().get("recurring_price").getAsString().replace(".", ""))
                    .withInterval(JsonUtil.searchableBody(specificPlan).get("subscription").getAsJsonObject().get("frequency_interval").getAsString())
                    .withCurrency(JsonUtil.searchableBody(specificPlan).get("subscription").getAsJsonObject().get("currency_code").getAsString())
                    .withCreated(order.getCreatedAt());

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
                    .withName(order.getBuyerName())
                    .withCreated(order.getCreatedAt());

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
                    .withOID(plan.getOID() + "_" + customer.getOID() + "_" + order.getCreatedAt())
                    .withCustomer(customer)
                    .withPlan(plan)
                    .withStartedAt(order.getCreatedAt());

            HttpResponse postSubscriptionActiveResponse = baremetricsConnectionHandler.postSubscriptionActive(subscription);

            if(postSubscriptionActiveResponse.getStatusLine().getStatusCode() == 200) {
                System.out.println("Subscription successfully posted! OID: " + subscription.getOID());
            } else {
                System.out.println("Error posting subscription with OID: " + subscription.getOID());
                System.out.println("Error: " + new BufferedReader(new InputStreamReader(postSubscriptionActiveResponse.getEntity().getContent())).readLine());
            }

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    private void handleCancelledSubscription(PastOrder order) {
        try {
            BaremetricsConnectionHandler baremetricsConnectionHandler = new BaremetricsConnectionHandler();

            SendOwlConnectionHandler sendOwlConnectionHandler = new SendOwlConnectionHandler();

            String specificPlan = sendOwlConnectionHandler.getSpecificSubscriptionJSON(order.getBackloadProductID());

            Plan plan = new Plan()
                    .withOID(order.getBackloadProductID())
                    .withName(JsonUtil.searchableBody(specificPlan).get("subscription").getAsJsonObject().get("name").getAsString())
                    .withRecurringPrice(JsonUtil.searchableBody(specificPlan).get("subscription").getAsJsonObject().get("recurring_price").getAsString().replace(".", ""))
                    .withInterval(JsonUtil.searchableBody(specificPlan).get("subscription").getAsJsonObject().get("frequency_interval").getAsString())
                    .withCurrency(JsonUtil.searchableBody(specificPlan).get("subscription").getAsJsonObject().get("currency_code").getAsString())
                    .withCreated(order.getCreatedAt());


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
                    .withOID(order.getBuyerEmail().replace("@", "_").replace(".com", ""))
                    .withEmail(order.getBuyerEmail())
                    .withName(order.getBuyerName())
                    .withCreated(order.getCreatedAt());

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
                    .withOID(plan.getOID() + "_" + customer.getOID() + "_" + order.getCreatedAt())
                    .withCustomer(customer)
                    .withPlan(plan)
                    .withStartedAt(order.getCreatedAt())
                    .withCancelledAt(order.getUpdatedAt());

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
}
