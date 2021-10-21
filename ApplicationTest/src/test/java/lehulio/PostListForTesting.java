package lehulio;

import java.util.ArrayList;
import java.util.List;

public class PostListForTesting implements PostRepository {
    private static final List<Post> postListForTesting;

    static {
        postListForTesting = new ArrayList<>();
    }

    @Override
    public List<Post> getPostsList() {
        return postListForTesting;
    }

    public void add(Post post) {
        postListForTesting.add(post);
    }
}
