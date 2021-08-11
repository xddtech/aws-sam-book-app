package mybook;

import java.io.IOException;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import mybook.service.RequestEventService;
import mybook.service.RequestServiceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Handler for requests to Lambda function.
 */
public class BookApp implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    public APIGatewayProxyResponseEvent handleRequest(final APIGatewayProxyRequestEvent input, final Context context) {
        logger.info("Start request ===================8/11====================================");
        APIGatewayProxyResponseEvent response = Utils.getResponseEventWithHeader();
        try {
            RequestEventService service = RequestServiceFactory.getService(input);
            logger.info("Request event service: " + service.getClass());
            String output = service.processRequest(input.getBody());

            return response
                    .withStatusCode(200)
                    .withBody(output);
        } catch (Exception e) {
            logger.error("Request failed ##########", e);
            return response
                    .withBody("{}")
                    .withStatusCode(500);
        }
    }
}
