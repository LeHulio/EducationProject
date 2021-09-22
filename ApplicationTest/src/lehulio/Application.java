package lehulio;


import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.logging.Logger;

public class Application {

    private static final Logger logger = Logger.getLogger(Application.class.getName());

    public static void main(String... args) throws IOException {
        logger.info("info");

        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/articles", new PostsAndSearchHandler());
        server.setExecutor(null);
        server.start();
    }


}
