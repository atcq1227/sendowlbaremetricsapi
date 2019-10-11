package handlers.lambda;

import baremetrics.Charge;
import baremetrics.Customer;
import baremetrics.Plan;
import baremetrics.Subscription;
import handlers.connection.BaremetricsConnectionHandler;
import org.apache.http.HttpResponse;
import sendowl.Order;
import util.APIConstants;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ChargeHandler {
    public String handle(Order order) {
        try {
            BaremetricsConnectionHandler baremetricsConnectionHandler = new BaremetricsConnectionHandler();

            Customer customer = new Customer()
                    .withOID(order.getBuyerEmail().replace("@", "_").replace(".com", ""))
                    .withEmail(order.getBuyerEmail())
                    .withName(order.getBuyerName());

            HttpResponse findCustomerResponse = baremetricsConnectionHandler.getSpecificObjectHTTP(APIConstants.BaremetricsCustomers, customer.getOID());

            if(findCustomerResponse.getStatusLine().getStatusCode() == 404) {
                System.out.println("Customer with OID: " + customer.getOID() + " not found, creating new customer");

                HttpResponse postCustomerResponse = baremetricsConnectionHandler.postCustomer(customer);

                if(postCustomerResponse.getStatusLine().getStatusCode() == 200) {
                    System.out.println("Customer successfully posted! OID: " + customer.getOID());
                } else {
                    System.out.println("Error posting customer with OID: " + customer.getOID());
                    System.out.println("Error: " + new BufferedReader(new InputStreamReader(postCustomerResponse.getEntity().getContent())).readLine());
                }
            } else if (findCustomerResponse.getStatusLine().getStatusCode() == 200) {
                System.out.println("Found existing customer with OID: " + customer.getOID());
            }

            Charge charge = new Charge()
                    .withOID(order.getCompletedCheckoutAt() + "_" + order.getBuyerName())
                    .withAmount(order.getPrice())
                    .withCurrency(order.getCurrency())
                    .withCurrency(order.getCurrency())
                    .withCustomer(customer);

            HttpResponse postChargeResponse = baremetricsConnectionHandler.postCharge(charge);

            if(postChargeResponse.getStatusLine().getStatusCode() == 200) {
                System.out.println("Charge successfully posted with OID: " + charge.getOID());
            } else {
                System.out.println("Error posting charge with OID: " + charge.getOID());
                System.out.println("Error: " + new BufferedReader(new InputStreamReader(postChargeResponse.getEntity().getContent())).readLine());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return "New subscription cancelled";
    }
}
