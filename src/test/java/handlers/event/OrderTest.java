package handlers.event;

import org.junit.Assert;
import org.junit.Test;
import sendowl.Order;
import util.JsonUtil;

import java.io.ByteArrayInputStream;

public class OrderTest {

    @Test
    public void testGetPlanName() {
        Assert.assertEquals("Playin' it safe.", new Order(JsonUtil.testSubscriptionOrder).getProductName());
    }

    @Test
    public void testGetPlanNameNegative() {
        Assert.assertNotEquals("", new Order(JsonUtil.testSubscriptionOrder).getProductName());
    }

    @Test
    public void testGetBuyerName() {
        Assert.assertEquals("Test Buyer", new Order(JsonUtil.testSubscriptionOrder).getBuyerName());
    }

    @Test
    public void testGetBuyerNameNegative() {
        Assert.assertNotEquals("", new Order(JsonUtil.testSubscriptionOrder).getBuyerName());
    }

    @Test
    public void testGetBuyerEmail() {
        Assert.assertEquals("testEmail", new Order(JsonUtil.testSubscriptionOrder).getBuyerEmail());
    }

    @Test
    public void testGetBuyerEmailNegative() {
        Assert.assertNotEquals("", new Order(JsonUtil.testSubscriptionOrder).getBuyerEmail());
    }

    @Test
    public void testGetCompletedCheckoutAt() {
        Assert.assertEquals("2019-10-10T20:25:39Z", new Order(JsonUtil.testSubscriptionOrder).getCompletedCheckoutAt());
    }

    @Test
    public void testGetCompletedCheckoutAtNegative() {
        Assert.assertNotEquals("", new Order(JsonUtil.testSubscriptionOrder).getCompletedCheckoutAt());
    }

    @Test
    public void testGetProductID() {
        Assert.assertEquals("7489", new Order(JsonUtil.testSubscriptionOrder).getProductID());
    }

    @Test
    public void testGetProductIDNegative() {
        Assert.assertNotEquals("", new Order(JsonUtil.testSubscriptionOrder).getProductID());
    }

    @Test
    public void testGetState() {
        Assert.assertEquals("subscription_active", new Order(JsonUtil.testSubscriptionOrder).getState());
    }

    @Test
    public void testGetStateNegative() {
        Assert.assertNotEquals("", new Order(JsonUtil.testSubscriptionOrder).getState());
    }

    @Test
    public void testGetCurrency() {
        Assert.assertEquals("USD", new Order(JsonUtil.testSubscriptionOrder).getCurrency());
    }

    @Test
    public void testGetCurrencyNegative() {
        Assert.assertNotEquals("", new Order(JsonUtil.testSubscriptionOrder).getCurrency());
    }

    @Test
    public void testGetForSubscription() {
        Assert.assertTrue(new Order(JsonUtil.testSubscriptionOrder).getForSubscription());
    }

    @Test
    public void testGetPrice() {
        Assert.assertEquals("1500", new Order(JsonUtil.testProductOrder).getPrice());
    }

    @Test
    public void testGetPriceNegative() {
        Assert.assertNotEquals("", new Order(JsonUtil.testProductOrder).getPrice());
    }

    @Test
    public void testGetRecurringPrice() {
        Assert.assertEquals("1500", new Order(JsonUtil.testSubscriptionOrder).getRecurringPrice());
    }

    @Test
    public void testGetRecurringPriceNegative() {
        Assert.assertNotEquals("", new Order(JsonUtil.testSubscriptionOrder).getRecurringPrice());
    }

    @Test
    public void testGetFrequencyInterval() {
        Assert.assertEquals("month", new Order(JsonUtil.testSubscriptionOrder).getFrequencyInterval());
    }

    @Test
    public void testGetFrequencyIntervalNegative() {
        Assert.assertNotEquals("", new Order(JsonUtil.testSubscriptionOrder).getFrequencyInterval());
    }
}
