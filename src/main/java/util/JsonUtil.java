package util;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.*;

public class JsonUtil {
    public static String testProductOrder = "{\"order\": {\n" +
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

    public static String testSubscriptionOrder = "{\n" +
            "  \"order\": {\n" +
            "    \"state\": \"subscription_active\",\n" +
            "    \"gateway\": \"Stripe\",\n" +
            "    \"buyer_email\": \"testEmail\",\n" +
            "    \"paypal_email\": null,\n" +
            "    \"buyer_name\": \"Test Buyer\",\n" +
            "    \"business_name\": null,\n" +
            "    \"business_vat_number\": null,\n" +
            "    \"shipping_address1\": null,\n" +
            "    \"shipping_address2\": null,\n" +
            "    \"shipping_city\": null,\n" +
            "    \"shipping_region\": null,\n" +
            "    \"shipping_postcode\": null,\n" +
            "    \"shipping_country\": \"GB\",\n" +
            "    \"billing_address1\": null,\n" +
            "    \"billing_address2\": null,\n" +
            "    \"billing_city\": null,\n" +
            "    \"billing_region\": null,\n" +
            "    \"billing_postcode\": null,\n" +
            "    \"billing_country\": \"GB\",\n" +
            "    \"buyer_ip_address\": \"testIP\",\n" +
            "    \"settled_currency\": \"USD\",\n" +
            "    \"access_allowed\": true,\n" +
            "    \"dispatched_at\": null,\n" +
            "    \"tag\": null,\n" +
            "    \"giftee_name\": null,\n" +
            "    \"giftee_email\": null,\n" +
            "    \"gift_deliver_at\": null,\n" +
            "    \"download_url\": \"testdownloadURL\",\n" +
            "    \"order_checkout_url\": null,\n" +
            "    \"subscription_management_url\": \"testManagementURL\",\n" +
            "    \"eu_resolved_country\": null,\n" +
            "    \"id\": \"0048206320\",\n" +
            "    \"sendowl_order_id\": 48206320,\n" +
            "    \"refunded\": false,\n" +
            "    \"settled_gateway_fee\": \"0.74\",\n" +
            "    \"settled_gross\": \"15.00\",\n" +
            "    \"eu_reverse_charge\": null,\n" +
            "    \"for_subscription\": true,\n" +
            "    \"payment_method\": \"card\",\n" +
            "    \"cart\": {\n" +
            "      \"completed_checkout_at\": \"2019-10-10T20:25:39Z\",\n" +
            "      \"cart_items\": [\n" +
            "        {\n" +
            "          \"quantity\": 1,\n" +
            "          \"valid_until\": null,\n" +
            "          \"download_attempts\": null,\n" +
            "          \"product\": {\n" +
            "            \"id\": 7489,\n" +
            "            \"name\": \"Playin' it safe.\",\n" +
            "            \"recurring_type\": \"ongoing\",\n" +
            "            \"trial_frequency\": \"day\",\n" +
            "            \"trial_no_of_occurrences\": null,\n" +
            "            \"no_of_occurrences\": null,\n" +
            "            \"frequency_interval\": \"month\",\n" +
            "            \"frequency_value\": 1,\n" +
            "            \"frequency\": \"monthly\",\n" +
            "            \"product_type\": \"subscription\",\n" +
            "            \"recurring_price\": \"15.00\",\n" +
            "            \"trial_price\": null,\n" +
            "            \"member_types\": [\n" +
            "              \"redirect\"\n" +
            "            ],\n" +
            "            \"trial_frequency_legacy\": \"daily\",\n" +
            "            \"fixed_amount_of_payments\": false\n" +
            "          }\n" +
            "        }\n" +
            "      ]\n" +
            "    },\n" +
            "    \"licenses\": [],\n" +
            "    \"discount_code\": null,\n" +
            "    \"unsubscribe_url\": \"testURL\",\n" +
            "    \"product_update_consent_url\": \"http://transactions.sendowl.com/orders/48206320/consent/d1229bd80d6df61b5898c0b6e348fd7b\",\n" +
            "    \"transactions\": [\n" +
            "      {\n" +
            "        \"gateway_transaction_id\": \"ch_1FS8KwKt5ENTcJ3AJHEOLYtA\",\n" +
            "        \"payment_currency\": \"USD\",\n" +
            "        \"alternate_pay_method_note\": null,\n" +
            "        \"created_at\": \"2019-10-10T20:34:21Z\",\n" +
            "        \"payment_gross\": \"15.00\",\n" +
            "        \"payment_gateway_fee\": \"0.74\",\n" +
            "        \"net_price\": \"15.00\",\n" +
            "        \"refund\": false,\n" +
            "        \"card_last_4_digits\": \"1130\",\n" +
            "        \"card_expires_at\": \"2021-09-30\"\n" +
            "      }\n" +
            "    ],\n" +
            "    \"order_custom_checkout_fields\": [\n" +
            "      {\n" +
            "        \"name\": \"Which of these best describe you?\",\n" +
            "        \"value\": \"Publisher / Label\"\n" +
            "      }\n" +
            "    ],\n" +
            "    \"receiver_name\": \"Test\",\n" +
            "    \"receiver_email\": \"test@test.com\",\n" +
            "    \"gift_order\": false,\n" +
            "    \"send_update_emails\": true,\n" +
            "    \"next_payment_date\": null,\n" +
            "    \"errors\": [],\n" +
            "    \"buyer_first_name\": \"test\",\n" +
            "    \"receiver_first_name\": \"test\",\n" +
            "    \"validity_statement\": null,\n" +
            "    \"buyer_address1\": null,\n" +
            "    \"buyer_address2\": null,\n" +
            "    \"buyer_city\": null,\n" +
            "    \"buyer_region\": null,\n" +
            "    \"buyer_postcode\": null,\n" +
            "    \"buyer_country\": \"GB\"\n" +
            "  }\n" +
            "}";

    public static JsonObject searchableBody(String request) {
        return new JsonParser().parse(request).getAsJsonObject();
    }
}
