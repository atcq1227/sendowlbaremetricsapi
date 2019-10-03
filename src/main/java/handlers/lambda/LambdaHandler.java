package handlers.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import java.io.InputStream;

abstract class LambdaHandler implements RequestHandler<InputStream, String> {
    public abstract String handleRequest(InputStream webHookRequest, Context context);
}

