package handlers;

import baremetrics.Customer;
import baremetrics.Plan;
import baremetrics.Subscription;

import com.google.gson.JsonElement;
import com.google.gson.stream.JsonReader;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import util.APIConstants;
import util.JsonUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class BaremetricsConnectionHandler {
    HttpClient httpClient;

    public HttpResponse postCustomer(Customer customer) throws IOException {
        String url = "https://api.baremetrics.com/v1" + APIConstants.BaremetricsSourceID + APIConstants.BaremetricsCustomers;

        httpClient = new DefaultHttpClient();

        HttpPost post = new HttpPost(url);

        post.addHeader("Authorization", APIConstants.BaremetricsAPIKey);

        List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
        urlParameters.add(new BasicNameValuePair("oid", customer.getOID()));
        urlParameters.add(new BasicNameValuePair("name", customer.getName()));
        urlParameters.add(new BasicNameValuePair("notes", ""));
        urlParameters.add(new BasicNameValuePair("email", customer.getEmail()));
        urlParameters.add(new BasicNameValuePair("created", customer.getCreated()));
        urlParameters.add(new BasicNameValuePair("active", customer.isActive()));

        post.setEntity(new UrlEncodedFormEntity(urlParameters));

        return httpClient.execute(post);
    }

    public HttpResponse postSubscriptionPlan(Plan plan) throws IOException {
        String url = "https://api.baremetrics.com/v1" + APIConstants.BaremetricsSourceID + APIConstants.BaremetricsPlans;

        httpClient = new DefaultHttpClient();

        HttpPost post = new HttpPost(url);

        post.addHeader("Authorization", APIConstants.BaremetricsAPIKey);

        List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
        urlParameters.add(new BasicNameValuePair("oid", plan.getOID()));
        urlParameters.add(new BasicNameValuePair("name", plan.getName()));
        urlParameters.add(new BasicNameValuePair("currency", "USD"));
        urlParameters.add(new BasicNameValuePair("amount", plan.getRecurringPrice()));
        urlParameters.add(new BasicNameValuePair("interval_count", "1"));

        post.setEntity(new UrlEncodedFormEntity(urlParameters));

        return httpClient.execute(post);
    }

    public HttpResponse postSubscriptionActive(Subscription subscription) throws IOException {
        String url = "https://api.baremetrics.com/v1" + APIConstants.BaremetricsSourceID + APIConstants.BaremetricsSubscriptions;

        httpClient = new DefaultHttpClient();

        HttpPost post = new HttpPost(url);

        post.addHeader("Authorization", APIConstants.BaremetricsAPIKey);

        List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
        urlParameters.add(new BasicNameValuePair("oid", subscription.getOID()));
        urlParameters.add(new BasicNameValuePair("started_at", subscription.getStartedAt()));
        urlParameters.add(new BasicNameValuePair("canceled_at", "nil"));
        urlParameters.add(new BasicNameValuePair("plan_oid", subscription.getPlan().getOID()));
        urlParameters.add(new BasicNameValuePair("customer_oid", subscription.getCustomer().getOID()));
        urlParameters.add(new BasicNameValuePair("active", subscription.isActive()));

        post.setEntity(new UrlEncodedFormEntity(urlParameters));

        return httpClient.execute(post);
    }

    public HttpResponse putSubscriptionCancelled(Subscription subscription) throws IOException {
        String url = "https://api.baremetrics.com/v1" + APIConstants.BaremetricsSourceID + APIConstants.BaremetricsSubscriptions + "/" + subscription.getOID() + APIConstants.BaremetricsCancel;

        httpClient = new DefaultHttpClient();

        HttpPut put = new HttpPut(url);

        put.addHeader("Authorization", APIConstants.BaremetricsAPIKey);

        return httpClient.execute(put);
    }

    public HttpResponse getSpecificObjectHTTP(String APIPath, String OID) throws IOException {
        String url = "https://api.baremetrics.com/v1" + APIConstants.BaremetricsSourceID + APIPath + "/"+ OID;

        httpClient = new DefaultHttpClient();

        HttpGet get = new HttpGet(url);

        get.addHeader("Authorization", APIConstants.BaremetricsAPIKey);

        return httpClient.execute(get);
    }

    public JsonElement getSpecificObjectJSON(String APIPath, String OID) throws IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(getSpecificObjectHTTP(APIConstants.BaremetricsPlans, OID).getEntity().getContent()));

        reader.setLenient(true);

        return JsonUtil.searchableBody(new BufferedReader(new InputStreamReader(getSpecificObjectHTTP(APIConstants.BaremetricsPlans, OID).getEntity().getContent())).readLine());
    }
}



