package com.lumastech.ecoapp.Models;

public class ResponseData <T>{
    public Boolean success = false;
    public String message = "";
    public T data;
}
