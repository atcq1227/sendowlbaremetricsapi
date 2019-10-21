package handlers.connection;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import util.APIConstants;

import java.io.*;

public class SendOwlConnectionHandler {
    final private String API_KEY = "9c4ee8343e814c4";
    final private String API_SECRET = "6a90ab3cf8cd7f4a07e6";

    HttpClient httpClient;

    public JsonArray getProductsJSON() {
        try {
            return new JsonParser().parse(new InputStreamReader(getHTTPResponse(APIConstants.SendOwlProducts).getEntity().getContent())).getAsJsonArray();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public JsonArray getOrdersJSON() {
        try {
            return new JsonParser().parse(new InputStreamReader(getHTTPResponse(APIConstants.SendOwlOrders).getEntity().getContent())).getAsJsonArray();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public HttpResponse getHTTPResponse(String APIConstant) {
        String url = "https://" + API_KEY + ":" + API_SECRET + "@www.sendowl.com" + APIConstant;

        httpClient = new DefaultHttpClient();

        HttpGet get = new HttpGet(url);

        get.addHeader("Accept", "application/json");

        HttpResponse response = null;

        try {
            response = httpClient.execute(get);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Response Code : " +
                response.getStatusLine().getStatusCode());

        return response;
    }
}
