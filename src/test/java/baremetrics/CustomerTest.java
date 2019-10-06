package baremetrics;

import org.junit.Assert;
import org.junit.Test;

public class CustomerTest {
//    @Test
//    public void twoCustomersWithNewOIDSameName() {
//        Customer cust1 = new Customer().withName("test").withNewOID();
//
//        Customer cust2 = new Customer().withName("test").withNewOID();
//
//        Assert.assertEquals(cust1.getOID(), cust2.getOID());
//    }
//
//    @Test
//    public void twoCustomersWithNewOIDDifferentName() {
//        Customer cust1 = new Customer().withName("test").withNewOID();
//
//        Customer cust2 = new Customer().withName("test1").withNewOID();
//
//        Assert.assertNotEquals(cust1.getOID(), cust2.getOID());
//    }

    @Test
    public void getJsonBodyTest() {
        Customer customer = new Customer()
                .withName("testName")
                .withEmail("testEmail")
                .withCurrentPlan(new Plan().withName("test plan name"));

        System.out.println(customer.getJson());

        Assert.assertEquals(customer.getJson(), "{\"email\":\"testEmail\",\"name\":\"testName\",\"LTV\":0,\"isActive\":false,\"isCancelled\":false,\"currentPlan\":{\"name\":\"test plan name\",\"intervalCount\":0,\"active\":false}}");
    }
}
