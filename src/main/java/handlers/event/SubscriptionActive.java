package handlers.event;

import baremetrics.Customer;
import baremetrics.Plan;
import baremetrics.Subscription;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import handlers.BaremetricsPostHandler;
import sendowl.Order;
import util.JsonUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class SubscriptionActive {
    public void handle(InputStream webHookRequest) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(webHookRequest));

        Order order = new Order(br.readLine());

        Plan plan = new Plan()
                .withName(order.getProductName())
                .withNewOID();

        Customer customer = new Customer()
                .withNewOID()
                .withName(order.getBuyerName());

        Subscription subscription = new Subscription()
                .withNewOID()
                .startedNow()
                .withCustomer(customer)
                .withPlan(plan);

        System.out.println("response: " + new BufferedReader(new InputStreamReader(new BaremetricsPostHandler().postSubscriptionActive(plan, customer, subscription).getEntity().getContent())).readLine());
    }
}
