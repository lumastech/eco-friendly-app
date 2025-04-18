package com.lumastech.ecoapp.Models;

import java.lang.reflect.Array;

public class ApiResponse {
    public String message = "", token= "null", status = null;
    public boolean success = false;
    public User user;
    public Array errors;
}
