import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import handlers.lambda.APIBackloadHandler;
import handlers.lambda.BackloadHandler;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import sendowl.PastOrder;
import util.EmailUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Backloader {
    public static void main(String[] args) throws Exception {
//        System.out.println("deleting subscriptions");
//        new BackloadHandler().deleteAllSubscriptions();
//        System.out.println("deleting customers");
//        new BackloadHandler().deleteAllCustomers();
//        System.out.println("deleting plans");
//        new BackloadHandler().deleteAllPlans();
//        System.out.println("deleting charges");
//        new BackloadHandler().deleteAllCharges();
        System.out.println("handling");
        new APIBackloadHandler().handle(0);
        //problemFinder();
    }

    private static void problemFinder() throws IOException, InterruptedException {
        int pageNumber = 0;

        String url = "https://4ab76f6325e7eb2:6ca56c1843e7c63e9d35@www.sendowl.com/api/v1_3/orders?per_page=50&page=" + pageNumber;

        boolean morePages = true;

        boolean found = false;

        while(!found) {

            HttpClient httpClient = new DefaultHttpClient();

            HttpGet get = new HttpGet(url);

            get.addHeader("Accept", "application/json");
            get.addHeader("per_page", "50");
            get.addHeader("page", Integer.toString(pageNumber));

            HttpResponse response = httpClient.execute(get);

            String responseString = new BufferedReader(new InputStreamReader(response.getEntity().getContent())).readLine();

            System.out.println(responseString);

            JsonArray array = new JsonParser().parse(responseString).getAsJsonArray();

            if(array.size() > 0) {
                for (JsonElement element : array) {
                    PastOrder order = new PastOrder(element.getAsJsonObject().get("order").toString());

                    if(order.getRefunded().equals("true")) {
                        System.out.println(order.getID());
                        found = true;
                        break;
                    }
                }
            } else {
                System.out.println("not found");
                found = true;
            }

            pageNumber++;

            url = "https://4ab76f6325e7eb2:6ca56c1843e7c63e9d35@www.sendowl.com/api/v1_3/orders?per_page=50&page=" + pageNumber;

            Thread.sleep(100);
        }
    }
}
