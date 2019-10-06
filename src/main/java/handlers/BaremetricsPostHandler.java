package handlers;

import baremetrics.Customer;
import baremetrics.Plan;
import baremetrics.Subscription;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import util.APIConstants;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BaremetricsPostHandler {

    public HttpResponse postSubscriptionActive(Plan plan, Customer customer, Subscription subscription) throws IOException {
        String url = "https://api.baremetrics.com/v1/a42c788d-db06-49a4-9f88-951eb5e638d6" + APIConstants.BaremetricsSubscriptions;

        HttpClient httpClient = new DefaultHttpClient();
        HttpPost post = new HttpPost(url);

        post.addHeader("Authorization", "lk_HvwQkcamtU6MR6KccqXiWQ");

        List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
        urlParameters.add(new BasicNameValuePair("customer", customer.getJson()));
        urlParameters.add(new BasicNameValuePair("plan", plan.getJson()));

        post.setEntity(new UrlEncodedFormEntity(urlParameters));

        return httpClient.execute(post);
    }


}



