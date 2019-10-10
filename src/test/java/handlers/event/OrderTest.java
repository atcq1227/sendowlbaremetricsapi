package handlers.event;

import org.junit.Assert;
import org.junit.Test;
import sendowl.Order;
import util.JsonUtil;

import java.io.ByteArrayInputStream;

public class OrderTest {

    @Test
    public void testGetPlanName() {
        Assert.assertEquals("My Product", new Order(JsonUtil.testProductOrder).getProductName());
    }

    @Test
    public void testGetPlanNameNegative() {
        Assert.assertNotEquals("", new Order(JsonUtil.testProductOrder).getProductName());
    }

    @Test
    public void testGetBuyerName() {
        Assert.assertEquals("Mr Buyer", new Order(JsonUtil.testProductOrder).getBuyerName());
    }

    @Test
    public void testGetBuyerNameNegative() {
        Assert.assertNotEquals("", new Order(JsonUtil.testProductOrder).getBuyerName());
    }

    @Test
    public void testGetBuyerEmail() {
        Assert.assertEquals("mrbuyer@gmail.com", new Order(JsonUtil.testProductOrder).getBuyerEmail());
    }

    @Test
    public void testGetBuyerEmailNegative() {
        Assert.assertNotEquals("", new Order(JsonUtil.testProductOrder).getBuyerEmail());
    }

    @Test
    public void testGetCompletedCheckoutAt() {
        Assert.assertEquals("2016-01-05T10:59:24Z", new Order(JsonUtil.testProductOrder).getCompletedCheckoutAt());
    }

    @Test
    public void testGetCompletedCheckoutAtNegative() {
        Assert.assertNotEquals("", new Order(JsonUtil.testProductOrder).getCompletedCheckoutAt());
    }

    @Test
    public void testGetProductID() {
        Assert.assertEquals("2811", new Order(JsonUtil.testProductOrder).getProductID());
    }

    @Test
    public void testGetProductIDNegative() {
        Assert.assertNotEquals("", new Order(JsonUtil.testProductOrder).getProductID());
    }

    @Test
    public void testGetState() {
        Assert.assertEquals("complete", new Order(JsonUtil.testProductOrder).getState());
    }

    @Test
    public void testGetStateNegative() {
        Assert.assertNotEquals("", new Order(JsonUtil.testProductOrder).getState());
    }

    @Test
    public void testGetCurrency() {
        Assert.assertEquals("GBP", new Order(JsonUtil.testProductOrder).getCurrency());
    }

    @Test
    public void testGetCurrencyNegative() {
        Assert.assertNotEquals("", new Order(JsonUtil.testProductOrder).getCurrency());
    }

    @Test
    public void testGetForSubscription() {
        Assert.assertFalse(new Order(JsonUtil.testProductOrder).getForSubscription());
    }

    @Test
    public void testGetPrice() {
        Assert.assertEquals("1500", new Order(JsonUtil.testProductOrder).getPrice());
    }

    @Test
    public void testGetPriceNegative() {
        Assert.assertNotEquals("", new Order(JsonUtil.testProductOrder).getPrice());
    }
}
