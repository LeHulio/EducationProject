package lehulio;

import com.sun.net.httpserver.HttpServer;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Collections;
import java.util.List;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;

public class ApplicationTest {
    int randomPort;
    HttpServer testServer;

    @Before
    public void runServer() throws IOException {
        randomPort = (int) ((Math.random() * (49151 - 1024)) + 1024);
        testServer = HttpServer.create(new InetSocketAddress(randomPort), 0);
        testServer.createContext("/articles", new PostsAndSearchHandler());
        testServer.start();
    }


    @Test
    public void shouldReturnFullPostsList() throws IOException {
        OkHttpClient client = new OkHttpClient();
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

//        String exceptedResponse =
//                "\\[\n  \\{\n    \"name\": \"first\",\n    \"likes\": 0,\n    \"publicationDate\": \".*\"\n  }," +
//                "\n  \\{\n    \"name\": \"second\",\n    \"likes\": 0,\n    \"publicationDate\": \".*\"\n  }," +
//                "\n  \\{\n    \"name\": \"third\",\n    \"likes\": 0,\n    \"publicationDate\": \".*\"\n  }\n]";
        List<Post> exceptedResponse = PostsList.getPostsList();
        System.out.println(responseBody);
        System.out.println(exceptedResponse);

        assertThatJson(responseBody).isEqualTo(exceptedResponse);

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

//        String exceptedResponse =
//                "\\[\n  \\{\n    \"name\": \"first\",\n    \"likes\": 0,\n    \"publicationDate\": \".*\"\n  }\n]";
        List<Post> exceptedResponse = Collections.singletonList(PostsList.getPostsList().get(0));
        System.out.println(responseBody);
        System.out.println(exceptedResponse);

        assertThatJson(responseBody).isEqualTo(exceptedResponse);
    }


    @After
    public void tearDown() {
        testServer.stop(0);
    }
}