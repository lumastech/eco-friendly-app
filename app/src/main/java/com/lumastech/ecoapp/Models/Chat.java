package com.lumastech.ecoapp.Models;

import java.util.List;

public class Chat {
    public long id;
    public long user_id;
    public String name;
    public String image_url;
    public List<Message> messages;
}

