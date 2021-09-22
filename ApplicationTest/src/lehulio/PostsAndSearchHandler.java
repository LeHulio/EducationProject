package lehulio;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PostsAndSearchHandler implements HttpHandler {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String response;
        if (exchange.getRequestURI().getRawQuery() == null) {
            response = GSON.toJson(PostsList.getPostsList());
        } else {
            Map<String, String> requestParameters = getParsedParameters(exchange.getRequestURI().getRawQuery());
            List<Post> foundPosts = SearchEngine.searchPosts(requestParameters);
            response = GSON.toJson(foundPosts);

        }
        byte[] bytes = response.getBytes(StandardCharsets.UTF_8);
        exchange.sendResponseHeaders(200, bytes.length);
        OutputStream os = exchange.getResponseBody();
        os.write(bytes);
        os.close();

    }

    private Map<String, String> getParsedParameters(String query) {
        if (query == null || query.equals("")) {
            return Collections.emptyMap();
        }
        Map<String, String> queryParameters = new HashMap<>();
        String[] parameters = query.split("&");
        for (String current : parameters) {
            String[] splitParameters = current.split("=", 2);
            if (splitParameters[0] != null && splitParameters[1] != null) {
                queryParameters.put(splitParameters[0], splitParameters[1]);
            }
        }
        return queryParameters;
    }
}
