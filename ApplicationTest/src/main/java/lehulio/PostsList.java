package lehulio;

import java.util.ArrayList;
import java.util.List;

public class PostsList {
    private static final List<Post> postsList = new ArrayList<>();

    static {
        postsList.add(new Post("first"));
        postsList.add(new Post("second"));
        postsList.add(new Post("third"));
    }

    public static List<Post> getPostsList() {
        return postsList;
    }
}
