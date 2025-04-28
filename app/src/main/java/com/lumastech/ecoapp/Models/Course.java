package com.lumastech.ecoapp.Models;


import java.util.List;

public class Course {
    public long id;
    public String name;
    public String description;
    public String coordinator;
    public String status;
    public List<Lesson> lessons;

    public Course(long id, String name, String description, String coordinator, String status, List<Lesson> lessons) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.coordinator = coordinator;
        this.status = status;
        this.lessons = lessons;
    }
}
