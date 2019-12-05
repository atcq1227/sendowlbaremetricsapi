package handlers.event;

import org.junit.Assert;
import org.junit.Test;
import sendowl.PresentOrder;
import util.JsonUtil;

public class PresentOrderTest {

    @Test
    public void testGetPlanName() {
        Assert.assertEquals("Playin' it safe.", new PresentOrder(JsonUtil.testSubscriptionOrder).getProductName());
    }

    @Test
    public void testGetPlanNameNegative() {
        Assert.assertNotEquals("", new PresentOrder(JsonUtil.testSubscriptionOrder).getProductName());
    }

    @Test
    public void testGetBuyerName() {
        Assert.assertEquals("Test Buyer", new PresentOrder(JsonUtil.testSubscriptionOrder).getBuyerName());
    }

    @Test
    public void testGetBuyerNameNegative() {
        Assert.assertNotEquals("", new PresentOrder(JsonUtil.testSubscriptionOrder).getBuyerName());
    }

    @Test
    public void testGetBuyerEmail() {
        Assert.assertEquals("testEmail", new PresentOrder(JsonUtil.testSubscriptionOrder).getBuyerEmail());
    }

    @Test
    public void testGetBuyerEmailNegative() {
        Assert.assertNotEquals("", new PresentOrder(JsonUtil.testSubscriptionOrder).getBuyerEmail());
    }

    @Test
    public void testGetCompletedCheckoutAt() {
        Assert.assertEquals("2019-10-10T20:25:39Z", new PresentOrder(JsonUtil.testSubscriptionOrder).getCompletedCheckoutAt());
    }

    @Test
    public void testGetCompletedCheckoutAtNegative() {
        Assert.assertNotEquals("", new PresentOrder(JsonUtil.testSubscriptionOrder).getCompletedCheckoutAt());
    }

    @Test
    public void testGetProductID() {
        Assert.assertEquals("7489", new PresentOrder(JsonUtil.testSubscriptionOrder).getProductID());
    }

    @Test
    public void testGetProductIDNegative() {
        Assert.assertNotEquals("", new PresentOrder(JsonUtil.testSubscriptionOrder).getProductID());
    }

    @Test
    public void testGetState() {
        Assert.assertEquals("subscription_active", new PresentOrder(JsonUtil.testSubscriptionOrder).getState());
    }

    @Test
    public void testGetStateNegative() {
        Assert.assertNotEquals("", new PresentOrder(JsonUtil.testSubscriptionOrder).getState());
    }

    @Test
    public void testGetCurrency() {
        Assert.assertEquals("USD", new PresentOrder(JsonUtil.testSubscriptionOrder).getCurrency());
    }

    @Test
    public void testGetCurrencyNegative() {
        Assert.assertNotEquals("", new PresentOrder(JsonUtil.testSubscriptionOrder).getCurrency());
    }

    @Test
    public void testGetForSubscription() {
        Assert.assertTrue(new PresentOrder(JsonUtil.testSubscriptionOrder).getForSubscription());
    }

    @Test
    public void testGetPrice() {
        Assert.assertEquals("1500", new PresentOrder(JsonUtil.testProductOrder).getPrice());
    }

    @Test
    public void testGetPriceNegative() {
        Assert.assertNotEquals("", new PresentOrder(JsonUtil.testProductOrder).getPrice());
    }

    @Test
    public void testGetRecurringPrice() {
        Assert.assertEquals("1500", new PresentOrder(JsonUtil.testSubscriptionOrder).getRecurringPrice());
    }

    @Test
    public void testGetRecurringPriceNegative() {
        Assert.assertNotEquals("", new PresentOrder(JsonUtil.testSubscriptionOrder).getRecurringPrice());
    }

    @Test
    public void testGetFrequencyInterval() {
        Assert.assertEquals("month", new PresentOrder(JsonUtil.testSubscriptionOrder).getFrequencyInterval());
    }

    @Test
    public void testGetFrequencyIntervalNegative() {
        Assert.assertNotEquals("", new PresentOrder(JsonUtil.testSubscriptionOrder).getFrequencyInterval());
    }
}
