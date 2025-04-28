package com.lumastech.ecoapp.Models;

import java.util.List;

public class Post {
    public long id, user_id;
    public String title, image, content, status="active", created_at;
    public List<Comment> comments;

    public Post(long id, long user_id, String title, String image, String content, String created_at, List<Comment> comments) {
        this.id = id;
        this.user_id = user_id;
        this.title = title;
        this.image = image;
        this.content = content;
        this.created_at = created_at;
        this.comments = comments;
    }
}
