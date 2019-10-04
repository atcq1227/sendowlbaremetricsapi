package handlers;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import util.APIConstants;


import java.io.*;

public class SendOwlGetHandler {
    final private String API_KEY = "4ab76f6325e7eb2";
    final private String API_SECRET = "6ca56c1843e7c63e9d35";

    public JsonArray getProductsJSON() {
        try {
            return new JsonParser().parse(new InputStreamReader(getHTTPResponse(APIConstants.SendOwlProducts).getEntity().getContent())).getAsJsonArray();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private HttpResponse getHTTPResponse(String APIConstant) {
        String url = "https://" + API_KEY + ":" + API_SECRET + "@www.sendowl.com" + APIConstant;

        HttpClient httpClient = new DefaultHttpClient();
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
