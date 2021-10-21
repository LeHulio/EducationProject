package lehulio;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static lehulio.Application.postRepository;

public class SearchEngine {

    public static List<Post> searchByName(Map<String, String> queryParameters) {
        if (!queryParameters.containsKey("name")) {
            return Collections.emptyList();
        }

        String nameValue = queryParameters.get("name");

        return postRepository.getPostsList().stream()
                .filter(post -> post.getName().equals(nameValue))
                .collect(Collectors.toList());
    }
}
