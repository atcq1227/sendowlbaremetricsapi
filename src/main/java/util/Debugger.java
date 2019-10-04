package util;

import com.amazonaws.services.lambda.runtime.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Debugger {
    public static void logInputStream(InputStream webHookRequest) {
        BufferedReader br = new BufferedReader(new InputStreamReader(webHookRequest));

        try {
            while (br.readLine() != null) {
                System.out.println(br.readLine());
            }
        } catch (
                IOException e) {
            e.printStackTrace();
        }
    }

    public static void logContext(Context context) {
        System.out.println("Identity: " + context.getIdentity().getIdentityId());
    }

    public static void logNewSubscriptionActive() {
        System.out.println("New subscription active");
    }
}
