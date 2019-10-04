package sendowl;

import com.google.gson.JsonObject;
import util.JsonUtil;

import java.io.InputStream;

public class Order {
    private InputStream orderStream;

    public Order(InputStream orderStream) {
        this.orderStream = orderStream;
    }

    public String getProductName() {
        return JsonUtil.searchableBody(orderStream).get("order").getAsJsonObject()
                .get("cart").getAsJsonObject()
                .get("cart_items").getAsJsonArray().get(0).getAsJsonObject()
                .get("product").getAsJsonObject()
                .get("name").getAsString();
    }

    public String getBuyerName() {
        return JsonUtil.searchableBody(orderStream).get("order").getAsJsonObject()
                .get("buyer_name").getAsString();
    }

    public String getBuyerEmail() {
        return JsonUtil.searchableBody(orderStream).get("order").getAsJsonObject()
                .get("buyer_email").getAsString();
    }

    public String getCompletedCheckoutAt() {
        return JsonUtil.searchableBody(orderStream).get("order").getAsJsonObject()
                .get("cart").getAsJsonObject()
                .get("completed_checkout_at").getAsString();
    }
}
