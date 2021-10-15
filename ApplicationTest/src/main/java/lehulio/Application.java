package lehulio;


import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.logging.Logger;

public class Application {

    private static final Logger logger = Logger.getLogger(Application.class.getName());
    public static PostRepository postRepository = new PostsList();
    private HttpServer server;


    public static void main(String... args) throws IOException {
        logger.info("main method start");
        String port = args[0];
        new Application().startServer(port);


    }

    public void startServer(String port) {
        int parsedPort;
        if (port.length() > 0) {
            try {
                parsedPort = Integer.parseInt(port);
                if (parsedPort > 0) {
                    server = HttpServer.create(new InetSocketAddress(parsedPort), 0);
                } else {
                    System.out.println("Enter valid port number");
                    throw new RuntimeException();
                }

            } catch (NumberFormatException | IOException e) {
                System.out.println("Enter valid port number");
                throw new RuntimeException(e);
            }

        }
        if (server != null) {
            server.createContext("/articles", new PostsAndSearchHandler());
            server.start();
        }

    }

    public void stopServer(int delay) {
        server.stop(delay);

    }


}
