package lehulio;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import com.fasterxml.jackson.databind.*;
import com.google.gson.*;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

public class Application {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    public static void main(String... args) throws JsonProcessingException {
        System.out.println("hi there");



        HttpServer server = null;
        try {
            server = HttpServer.create(new InetSocketAddress(8080), 0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        server.createContext("/articles", new MyHandler());
        server.setExecutor(null); // creates a default executor
        server.start();

    }

    private static class MyHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            List<Post> posts = new ArrayList<>();
            posts.add(new Post("first"));
            posts.add(new Post("second"));
            posts.add(new Post("third"));

            // ObjectMapper obj = new ObjectMapper();


            String str = GSON.toJson(posts);



            byte[] bytes = str.getBytes();
            exchange.sendResponseHeaders(200, bytes.length);

            OutputStream os = exchange.getResponseBody();
            os.write(bytes);
            os.close();

        }
    }
}
