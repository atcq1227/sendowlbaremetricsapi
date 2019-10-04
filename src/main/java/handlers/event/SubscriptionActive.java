package handlers.event;

import baremetrics.Customer;
import baremetrics.Plan;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import sendowl.Order;
import util.JsonUtil;

import java.io.InputStream;

public class SubscriptionActive {
    public void handle(InputStream webHookRequest) {
        Order order = new Order(webHookRequest);

        Plan plan = new Plan().withName(order.getProductName());

        Customer customer = new Customer()
                .withName(order.getBuyerName())
                .withEmail(order.getBuyerEmail())
                .withCurrentPlan(plan);
    }
}
