package sendowl;

import com.google.gson.JsonObject;
import util.JsonUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class PresentOrder extends Order {
    private String orderBody;

    public PresentOrder(String orderBody) {
        super(orderBody);
        this.orderBody = orderBody;
    }

    public String getOrderBody() {
        return orderBody;
    }

    public void setOrderBody(String orderBody) {
        this.orderBody = orderBody;
    }

    public String getState() {
        return JsonUtil.searchableBody(this.orderBody).get("order").getAsJsonObject()
                .get("state").getAsString();
    }

    public String getCurrency() {
        return JsonUtil.searchableBody(this.orderBody).get("order").getAsJsonObject()
                .get("settled_currency").getAsString();
    }

    public boolean getForSubscription() {
        return JsonUtil.searchableBody(this.orderBody).get("order").getAsJsonObject()
                .get("for_subscription").getAsBoolean();
    }

    public String getProductName() {
        return JsonUtil.searchableBody(this.orderBody).get("order").getAsJsonObject()
                .get("cart").getAsJsonObject()
                .get("cart_items").getAsJsonArray().get(0).getAsJsonObject()
                .get("product").getAsJsonObject()
                .get("name").getAsString();
    }

    public String getPrice() {
        return JsonUtil.searchableBody(this.orderBody).get("order").getAsJsonObject()
                .get("cart").getAsJsonObject()
                .get("cart_items").getAsJsonArray().get(0).getAsJsonObject()
                .get("product").getAsJsonObject()
                .get("price").getAsString().substring(1).replace(".", "");
    }

    public String getRecurringPrice() {
        return JsonUtil.searchableBody(this.orderBody).get("order").getAsJsonObject()
                .get("cart").getAsJsonObject()
                .get("cart_items").getAsJsonArray().get(0).getAsJsonObject()
                .get("product").getAsJsonObject()
                .get("recurring_price").getAsString().replace(".", "");
    }

    public String getFrequencyInterval() {
        return JsonUtil.searchableBody(this.orderBody).get("order").getAsJsonObject()
                .get("cart").getAsJsonObject()
                .get("cart_items").getAsJsonArray().get(0).getAsJsonObject()
                .get("product").getAsJsonObject()
                .get("frequency_interval").getAsString().replace(".", "");
    }

    public String getBuyerName() {
        return JsonUtil.searchableBody(this.orderBody).get("order").getAsJsonObject()
                .get("buyer_name").getAsString();
    }

    public String getBuyerEmail() {
        return JsonUtil.searchableBody(this.orderBody).get("order").getAsJsonObject()
                .get("buyer_email").getAsString();
    }

    public String getCompletedCheckoutAt() {
        return JsonUtil.searchableBody(this.orderBody).get("order").getAsJsonObject()
                .get("cart").getAsJsonObject()
                .get("completed_checkout_at").getAsString();
    }

    public String getProductID() {
        return JsonUtil.searchableBody(this.orderBody).get("order").getAsJsonObject()
                .get("cart").getAsJsonObject()
                .get("cart_items").getAsJsonArray().get(0).getAsJsonObject()
                .get("product").getAsJsonObject()
                .get("id").getAsString();
    }

    public String getBackloadProductID() {
        return JsonUtil.searchableBody(this.orderBody).get("order").getAsJsonObject()
                .get("subscription_id").getAsString();
    }
}
