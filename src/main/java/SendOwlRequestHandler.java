import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;


import java.io.*;

public class SendOwlRequestHandler {
    final private String API_KEY = "9c4ee8343e814c4";
    final private String API_SECRET = "6a90ab3cf8cd7f4a07e6";

    public JsonArray getJSONResponse() {
        try {
            return new JsonParser().parse(new InputStreamReader(getHTTPResponse().getEntity().getContent())).getAsJsonArray();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private HttpResponse getHTTPResponse() {
        String url = "https://" + API_KEY + ":" + API_SECRET + "@www.sendowl.com/api/v1/products";

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
