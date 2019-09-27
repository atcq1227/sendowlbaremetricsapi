import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

public class SendowlRequestHandler {
    final private String API_KEY = "9c4ee8343e814c4";
    final private String API_SECRET = "6a90ab3cf8cd7f4a07e6";

    public void getProducts() {
        try {
            String url = "https://" + API_KEY + ":" + API_SECRET + "@www.sendowl.com/api/v1/products";

            HttpClient httpClient = new DefaultHttpClient();
            HttpGet get = new HttpGet(url);

            get.addHeader("Accept", "application/json");

            HttpResponse response = httpClient.execute(get);

            System.out.println("Response Code : " +
                    response.getStatusLine().getStatusCode());

            BufferedReader rd = new BufferedReader(
                    new InputStreamReader(response.getEntity().getContent()));

            StringBuffer result = new StringBuffer();
            String line = "";
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            System.out.println(result.toString());
        }

        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
