package lehulio;

import com.sun.net.httpserver.HttpServer;
import okhttp3.*;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Random;

import static org.junit.Assert.*;

public class ApplicationTest {
    int randomPort;
    HttpServer testServer;

    @Before
    public void runServer() throws IOException {
        randomPort = (int) (Math.random() * 65536);
        testServer = HttpServer.create(new InetSocketAddress(randomPort), 0);
        testServer.createContext("/articles", new PostsAndSearchHandler());
        testServer.setExecutor(null);
        testServer.start();
    }



    @Test
    public void shouldReturnFullPostsList() throws IOException {
        OkHttpClient client = new OkHttpClient();
        /*String url = new HttpUrl.Builder()
                .scheme("http")
                .host("localhost:" + randomPort)
                .addPathSegment("articles")
                .build()
                .toString();*/
        String url = HttpUrl
                .parse("http://localhost:" + randomPort + "/articles")
                .newBuilder()
                .build()
                .toString();
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = client.newCall(request).execute();
        String responseBody = response.body().string();

        String exceptedResponse =
                "\\[\n  \\{\n    \"name\": \"first\",\n    \"likes\": 0,\n    \"publicationDate\": \".*\"\n  }," +
                "\n  \\{\n    \"name\": \"second\",\n    \"likes\": 0,\n    \"publicationDate\": \".*\"\n  }," +
                "\n  \\{\n    \"name\": \"third\",\n    \"likes\": 0,\n    \"publicationDate\": \".*\"\n  }\n]";
        System.out.println(responseBody);
        System.out.println(exceptedResponse);

        Assert.assertTrue(responseBody.matches(exceptedResponse));

    }

    @Test
    public void shouldReturnArticleByName() throws IOException {
        OkHttpClient client = new OkHttpClient();
        String url = HttpUrl
                .parse("http://localhost:" + randomPort + "/articles")
                .newBuilder()
                .addQueryParameter("name", "first")
                .build()
                .toString();
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = client.newCall(request).execute();
        String responseBody = response.body().string();

        String exceptedResponse =
                "\\[\n  \\{\n    \"name\": \"first\",\n    \"likes\": 0,\n    \"publicationDate\": \".*\"\n  }\n]";
        System.out.println(responseBody);
        System.out.println(exceptedResponse);

        Assert.assertTrue(responseBody.matches(exceptedResponse));

    }


    @After
    public void serverDown() {
        testServer.stop(0);
    }
}