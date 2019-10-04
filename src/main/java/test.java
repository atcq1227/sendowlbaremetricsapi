import handlers.SendOwlGetHandler;
import handlers.event.SubscriptionActive;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class test {
    public static void main(String[] args)  {
        String json = "{\"order\": {\n" +
                "    \"access_allowed\": true,\n" +
                "    \"business_name\": null,\n" +
                "    \"business_vat_number\": null,\n" +
                "    \"buyer_address1\": null,\n" +
                "    \"buyer_address2\": null,\n" +
                "    \"buyer_city\": null,\n" +
                "    \"buyer_country\": \"GB\",\n" +
                "    \"buyer_email\": \"mrbuyer@gmail.com\",\n" +
                "    \"buyer_ip_address\": \"127.0.0.1\",\n" +
                "    \"buyer_name\": \"Mr Buyer\",\n" +
                "    \"buyer_postcode\": null,\n" +
                "    \"buyer_region\": null,\n" +
                "    \"cart\": {\n" +
                "        \"cart_items\": [\n" +
                "            {\n" +
                "                \"download_attempts\": 3,\n" +
                "                \"product\": {\n" +
                "                    \"id\": 2811,\n" +
                "                    \"license_type\": \"generated\",\n" +
                "                    \"member_types\": [\n" +
                "                        \"digital\"\n" +
                "                    ],\n" +
                "                    \"name\": \"My Product\",\n" +
                "                    \"price\": \"£15.00\",\n" +
                "                    \"product_image_url\": null,\n" +
                "                    \"product_type\": \"digital\",\n" +
                "                    \"shopify_variant_id\": null\n" +
                "                },\n" +
                "                \"quantity\": 1,\n" +
                "                \"tax_rate\": 20.0,\n" +
                "                \"valid_until\": \"2016-02-04T10:59:25Z\"\n" +
                "            }\n" +
                "        ],\n" +
                "        \"completed_checkout_at\": \"2016-01-05T10:59:24Z\"\n" +
                "    },\n" +
                "    \"discount\": null,\n" +
                "    \"dispatched_at\": null,\n" +
                "    \"download_url\": \"http://transactions.sendowl.com/orders/123456/download/XXX\",\n" +
                "    \"eu_resolved_country\": \"GB\",\n" +
                "    \"eu_reverse_charge\": null,\n" +
                "    \"for_subscription\": false,\n" +
                "    \"gateway\": \"Stripe\",\n" +
                "    \"gift_deliver_at\": null,\n" +
                "    \"gift_order\": false,\n" +
                "    \"giftee_email\": null,\n" +
                "    \"giftee_name\": null,\n" +
                "    \"id\": \"0000123456\",\n" +
                "    \"licenses\": [],\n" +
                "    \"order_custom_checkout_fields\": [],\n" +
                "    \"paypal_email\": null,\n" +
                "    \"price_at_checkout\": \"£15.00\",\n" +
                "    \"receiver_email\": \"mrbuyer@gmail.com\",\n" +
                "    \"receiver_name\": null,\n" +
                "    \"settled_affiliate_fee\": \"£5.00\",\n" +
                "    \"settled_currency\": \"GBP\",\n" +
                "    \"settled_gateway_fee\": \"£0.38\",\n" +
                "    \"settled_gross\": \"£18.00\",\n" +
                "    \"settled_tax\": \"£3.00\",\n" +
                "    \"state\": \"complete\",\n" +
                "    \"subscription_management_url\": null,\n" +
                "    \"tag\": null,\n" +
                "    \"transactions\": [\n" +
                "        {\n" +
                "            \"alternate_pay_method_note\": null,\n" +
                "            \"created_at\": \"2016-01-05T10:59:24Z\",\n" +
                "            \"gateway_transaction_id\": \"ch_fake001\",\n" +
                "            \"net_price\": \"£15.60\",\n" +
                "            \"payment_currency\": \"GBP\",\n" +
                "            \"payment_gateway_fee\": \"£0.38\",\n" +
                "            \"payment_gross\": \"£18.00\",\n" +
                "            \"payment_tax\": \"£3.00\",\n" +
                "            \"refund\": false\n" +
                "        }\n" +
                "    ],\n" +
                "    \"unsubscribe_url\": \"http://transactions.sendowl.com/orders/123456/unsubscribe/XXX\",\n" +
                "    \"validity_statement\": \"This link may be used up to 3 times before 2016-02-04 10:59:25 UTC when it will expire.\"\n" +
                "}}";

        InputStream jsonStream = new ByteArrayInputStream(json.getBytes());

        new SubscriptionActive().handle(jsonStream);
    }
}
