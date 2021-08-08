package mybook;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Utils {

    public static APIGatewayProxyResponseEvent getResponseEventWithHeader() {
        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent()
                .withHeaders(Utils.getResponseHeaders());
        return response;
    }

    public static Map<String, String> getResponseHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("X-Custom-Header", "application/json");
        return headers;
    }

    public static String getTestOutput() throws IOException {
        String pageContents = "";
        URL url = new URL("https://checkip.amazonaws.com");
        try(BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()))) {
            pageContents = br.lines().collect(Collectors.joining(System.lineSeparator()));
        }
        String output = String.format("{ \"message\": \"hello world\", \"location\": \"%s\" }", pageContents);
        return output;
    }
}
