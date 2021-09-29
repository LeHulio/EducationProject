package lehulio;

import java.time.LocalDateTime;
import java.util.Date;

public class Post {
    private String name;
    private int likes;
    private final Date publicationDate;

    public Post(String name) {
        this.name = name;
        this.likes = 0;
        this.publicationDate = new Date();
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLikes() {
        return this.likes;
    }

    public void setLikes() {
        this.likes += 1;
    }

    public Date getPublicationDate() {
        return this.publicationDate;
    }

}
