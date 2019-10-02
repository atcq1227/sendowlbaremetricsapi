import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.Iterator;
import java.util.Map;

public class LambdaHandler {

    public static void main(String[] args) {
        JsonArray array = new SendOwlRequestHandler().getJSONResponse();

        System.out.print(array.deepCopy().getAsJsonObject().get("name"));
    }
}
