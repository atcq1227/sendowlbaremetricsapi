package handlers;

import handlers.SendOwlGetHandler;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;

public class SendOwlGetHandlerTest {
    String HTTPResponse = "[{\"product\":{\"id\":78124103,\"product_type\":\"digital\",\"name\":\"test\",\"pdf_stamping\":false,\"sales_limit\":null,\"self_hosted_url\":null,\"license_type\":null,\"license_fetch_url\":null,\"shopify_variant_id\":null,\"custom_field\":null,\"price_is_minimum\":false,\"limit_to_single_qty_in_cart\":false,\"download_folder\":null,\"affiliate_sellable\":false,\"commission_rate\":null,\"weight\":null,\"created_at\":\"2019-09-27T20:05:12Z\",\"updated_at\":\"2019-09-27T20:05:12Z\",\"price\":\"100.00\",\"currency_code\":\"USD\",\"product_image_url\":null,\"attachment\":{\"filename\":\"AgentRansack_867.exe\",\"size\":15605488},\"instant_buy_url\":\"https://transactions.sendowl.com/products/78124103/7072CEA1/purchase\",\"add_to_cart_url\":\"https://transactions.sendowl.com/products/78124103/7072CEA1/add_to_cart\",\"sales_page_url\":\"https://transactions.sendowl.com/products/78124103/7072CEA1/view\"}}]";

    @Test
    public void testGetHTTPResponse() {
        assertTrue(new SendOwlGetHandler().getJSONResponse().toString().equals(HTTPResponse));
    }
}
