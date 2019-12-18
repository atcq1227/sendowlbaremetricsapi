package sendowl;

import util.JsonUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class PastOrder extends Order {
    private String orderBody;

    public PastOrder(String orderBody) {
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
        return JsonUtil.searchableBody(this.orderBody).getAsJsonObject()
                .get("state").getAsString();
    }

    public String getCurrency() {
        return JsonUtil.searchableBody(this.orderBody).getAsJsonObject()
                .get("settled_currency").getAsString();
    }

    public boolean getForSubscription() {
        return JsonUtil.searchableBody(this.orderBody).getAsJsonObject()
                .get("for_subscription").getAsBoolean();
    }

    public String getProductName() {
        return JsonUtil.searchableBody(this.orderBody).getAsJsonObject()
                .get("cart").getAsJsonObject()
                .get("cart_items").getAsJsonArray().get(0).getAsJsonObject()
                .get("product").getAsJsonObject()
                .get("name").getAsString();
    }

    public String getSettledGross() {
        return JsonUtil.searchableBody(this.orderBody).getAsJsonObject()
                .get("settled_gross").getAsString();
    }

    public String getPrice() {
        return JsonUtil.searchableBody(this.orderBody).getAsJsonObject()
                .get("cart").getAsJsonObject()
                .get("cart_items").getAsJsonArray().get(0).getAsJsonObject()
                .get("product").getAsJsonObject()
                .get("price").getAsString().substring(1).replace(".", "");
    }

    public String getRecurringPrice() {
        return JsonUtil.searchableBody(this.orderBody).getAsJsonObject()
                .get("cart").getAsJsonObject()
                .get("cart_items").getAsJsonArray().get(0).getAsJsonObject()
                .get("product").getAsJsonObject()
                .get("recurring_price").getAsString().replace(".", "");
    }

    public String getFrequencyInterval() {
        return JsonUtil.searchableBody(this.orderBody).getAsJsonObject()
                .get("cart").getAsJsonObject()
                .get("cart_items").getAsJsonArray().get(0).getAsJsonObject()
                .get("product").getAsJsonObject()
                .get("frequency_interval").getAsString().replace(".", "");
    }

    public String getBuyerName() {
        return JsonUtil.searchableBody(this.orderBody).getAsJsonObject()
                .get("buyer_name").getAsString().replaceAll("[^A-Za-z0-9]", "");
    }

    public String getBuyerEmail() {
        return JsonUtil.searchableBody(this.orderBody).getAsJsonObject()
                .get("buyer_email").getAsString();
    }

    public String getCreatedAt() throws ParseException {
        return String.valueOf(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").parse(JsonUtil.searchableBody(this.orderBody).getAsJsonObject()
                .get("created_at").getAsString()).getTime() / 1000L);
    }

    public String getUpdatedAt() throws ParseException {
        return String.valueOf(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").parse(JsonUtil.searchableBody(this.orderBody).getAsJsonObject()
                .get("updated_at").getAsString()).getTime() / 1000L);
    }

    public String getRefunded() {
        return JsonUtil.searchableBody(this.orderBody).getAsJsonObject()
                .get("refunded").getAsString();
    }

    public String getID() {
        return JsonUtil.searchableBody(this.orderBody).getAsJsonObject()
                .get("id").getAsString();
    }

    public String getProductID() {
        return JsonUtil.searchableBody(this.orderBody).getAsJsonObject()
                .get("subscription_id").getAsString();
    }

    public String getCompletedCheckoutAt() {
        return JsonUtil.searchableBody(this.orderBody).getAsJsonObject()
                .get("cart").getAsJsonObject()
                .get("completed_checkout_at").getAsString();
    }

    public String getBackloadProductID() {
        return JsonUtil.searchableBody(this.orderBody).getAsJsonObject()
                .get("subscription_id").getAsString();
    }
}
