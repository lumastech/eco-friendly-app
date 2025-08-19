package com.lumastech.ecoapp.Models;


import java.util.List;

public class Course {
    public long id;
    public String name;
    public String description;
    public User coordinator;
    public String status;
    public List<Lesson> lessons;
}
