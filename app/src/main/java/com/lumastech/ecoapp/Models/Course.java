package com.lumastech.ecoapp.Models;


public class Course {
    public long id;
    public String name;
    public String description;
    public String coordinator;
    public String status;

    public Course(long id, String name, String description, String coordinator, String status) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.coordinator = coordinator;
        this.status = status;
    }
}
