package lehulio;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Post {
    private String postName;
    private int postLikes;
    private String postDate;

    public Post(String postName) {
        this.postName = postName;
        this.postLikes = 0;
        this.postDate = getPostDate();
    }

    public String getPostName() {
        return postName;
    }

    public void setPostName(String postName) {
        this.postName = postName;
    }

    public int getPostLikes() {
        return postLikes;
    }

    public void setPostLikes(int postLikes) {
        this.postLikes = postLikes;
    }

    public String getPostDate() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }




}
