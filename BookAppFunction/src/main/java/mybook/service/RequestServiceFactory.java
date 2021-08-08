package mybook.service;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;

public class RequestServiceFactory {
    public static RequestEventService getService(APIGatewayProxyRequestEvent inputEvent) throws Exception {
        if (inputEvent == null || inputEvent.getHttpMethod() == null) {
            throw new Exception("Input event or http method is null");
        }
        String method = inputEvent.getHttpMethod();
        if (method.equalsIgnoreCase("GET")) {
            return new GetBookService();
        } else if (method.equalsIgnoreCase("POST")) {
            return new PostBookService();
        } else {
            throw new Exception("Invalid request method: " + method);
        }
    }
}
