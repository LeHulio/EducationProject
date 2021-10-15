package lehulio;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static lehulio.Application.postRepository;
import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;

public class ApplicationTest {
    int randomPort;
    Application sutServer;
    PostListForTesting postListForTesting;

    @Before
    public void setUp() throws IOException {
        postListForTesting = new PostListForTesting();
        postRepository = postListForTesting;

        sutServer = new Application();
        randomPort = (int) ((Math.random() * (49151 - 1024)) + 1024);
        sutServer.startServer("" + randomPort);
    }


    @Test
    public void shouldReturnFullPostsList() throws IOException {
        postListForTesting.add(new Post("Endless"));
        postListForTesting.add(new Post("Space"));
        postListForTesting.add(new Post("Near"));
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
        List<Post> expectedResponse = postRepository.getPostsList();
        System.out.println(responseBody);
        System.out.println(expectedResponse);

        assertThatJson(responseBody).isEqualTo(expectedResponse);


    }

    @Test
    public void shouldReturnArticleByName() throws IOException {
        OkHttpClient client = new OkHttpClient();
        String url = HttpUrl
                .parse("http://localhost:" + randomPort + "/articles")
                .newBuilder()
                .addQueryParameter("name", "Endless")
                .build()
                .toString();
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = client.newCall(request).execute();
        String responseBody = response.body().string();
        List<Post> expectedResponse = Collections.singletonList(postRepository.getPostsList().get(0));
        System.out.println(responseBody);
        System.out.println(expectedResponse);

        assertThatJson(responseBody).isEqualTo(expectedResponse);
    }


    @After
    public void tearDown() {
        sutServer.stopServer(0);
    }
}