package handlers;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class BaremetricsPostHandler {

    public void postSubscription() {
        try {
            String url = "https://api.baremetrics.com/v1/a42c788d-db06-49a4-9f88-951eb5e638d6/subscriptions";

            HttpClient httpClient = new DefaultHttpClient();
            HttpPost post = new HttpPost(url);

            post.addHeader("Authorization", "lk_HvwQkcamtU6MR6KccqXiWQ");

            List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
            urlParameters.add(new BasicNameValuePair("oid", "cool_subscription"));
            urlParameters.add(new BasicNameValuePair("started_at", "1471887288"));
            urlParameters.add(new BasicNameValuePair("canceled_at", "nil"));
            urlParameters.add(new BasicNameValuePair("plan_oid", "basic_plan"));
            urlParameters.add(new BasicNameValuePair("customer_oid", "1"));

            post.setEntity(new UrlEncodedFormEntity(urlParameters));

            HttpResponse response = httpClient.execute(post);

            System.out.println("\nSending 'POST' request to URL : " + url);
            System.out.println("Post parameters : " + post.getEntity());
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



