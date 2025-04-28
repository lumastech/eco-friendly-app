package com.lumastech.ecoapp.Models;

public class Question {
    public long id, user_id;
    public String question, answer, status, created_at, updated_at;
    public User user;
}
