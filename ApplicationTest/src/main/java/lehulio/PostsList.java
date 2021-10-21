package lehulio;

import java.util.ArrayList;
import java.util.List;

public class PostsList implements PostRepository {
    private static final List<Post> postsList = new ArrayList<>();

    static {
        postsList.add(new Post("first"));
        postsList.add(new Post("second"));
        postsList.add(new Post("third"));
    }

    @Override
    public List<Post> getPostsList() {
        return postsList;
    }
}
