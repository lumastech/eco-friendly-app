package com.lumastech.ecoapp.Models;

public class RegisterRequest {
    String name, email, password;

    public String getName() {
        return name;
    }



    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
