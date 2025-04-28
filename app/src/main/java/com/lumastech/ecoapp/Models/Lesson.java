package com.lumastech.ecoapp.Models;

import java.util.List;

public class Lesson {
    public  long id;
    public String title;
    public String description;
    public String content;
    public String duration;
    public String difficulty;
    public int order;
    public String video_url;
    public float points;
    public  String infographic_url;
    public List<Quiz> quizzes;
    public boolean is_free;

    public Lesson(long id, String title, float points, String description, String content, List<Quiz> quizzes){
        this.id = id;
        this.title = title;
        this.points = points;
        this.description = description;
        this.content = content;
        this.quizzes = quizzes;
    }
}
