package handlers.event;

import org.junit.Assert;
import org.junit.Test;
import sendowl.Order;
import util.JsonUtil;

import java.io.ByteArrayInputStream;

public class OrderTest {

    @Test
    public void testGetPlanName() {
        Assert.assertEquals("My Product", new Order(new ByteArrayInputStream(JsonUtil.testProductOrder.getBytes())).getProductName());
    }

    @Test
    public void testGetPlanNameNegative() {
        Assert.assertNotEquals("", new Order(new ByteArrayInputStream(JsonUtil.testProductOrder.getBytes())).getProductName());
    }

    @Test
    public void testGetBuyerName() {
        Assert.assertEquals("Mr Buyer", new Order(new ByteArrayInputStream(JsonUtil.testProductOrder.getBytes())).getBuyerName());
    }

    @Test
    public void testGetBuyerNameNegative() {
        Assert.assertNotEquals("", new Order(new ByteArrayInputStream(JsonUtil.testProductOrder.getBytes())).getBuyerName());
    }

    @Test
    public void testGetBuyerEmail() {
        Assert.assertEquals("mrbuyer@gmail.com", new Order(new ByteArrayInputStream(JsonUtil.testProductOrder.getBytes())).getBuyerEmail());
    }

    @Test
    public void testGetBuyerEmailNegative() {
        Assert.assertNotEquals("", new Order(new ByteArrayInputStream(JsonUtil.testProductOrder.getBytes())).getBuyerEmail());
    }

    @Test
    public void testGetCompletedCheckoutAt() {
        Assert.assertEquals("2016-01-05T10:59:24Z", new Order(new ByteArrayInputStream(JsonUtil.testProductOrder.getBytes())).getCompletedCheckoutAt());
    }

    @Test
    public void testGetCompletedCheckoutAtNegative() {
        Assert.assertNotEquals("", new Order(new ByteArrayInputStream(JsonUtil.testProductOrder.getBytes())).getCompletedCheckoutAt());
    }
}
