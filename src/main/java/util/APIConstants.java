package util;

public class APIConstants {
    private final static String baremetricsSandboxBaseURL = "https://api-sandbox.baremetrics.com/v1";
    private final static String baremetricsLiveBaseURL = "https://api.baremetrics.com/v1";

    public final static String SendOwlProducts = "/api/v1/products";
    public final static String SendOwlOrders = "/api/v1_3/orders";
    public final static String SendOwlSubscriptions = "/api/v1_3/subscriptions";

    public final static String BaremetricsSubscriptions = "/subscriptions";
    public final static String BaremetricsAPIBaseURL = baremetricsLiveBaseURL;
    public final static String BaremetricsCharges = "/charges";
    public final static String BaremetricsPlans = "/plans";
    public final static String BaremetricsCustomers = "/customers";
    public final static String BaremetricsCancel = "/cancel";
    public final static String BaremetricsSourceID = "/5684c900-a29c-4ca9-8ac4-f85b68288011";
    public final static String BaremetricsAPIKey = "lk_O0dtHhjCNdb0WRyIbFRA";
    public final static String SubscriptionActiveMessage = "subscription_active";
    public final  static String SubscriptionCancelledMessage = "subscription_cancelled";
    public final static String WebhookTestMessage = "initial";

    public final static boolean ErrorEmailsOn = true;
}
