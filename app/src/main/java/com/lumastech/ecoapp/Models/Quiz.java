package com.lumastech.ecoapp.Models;

public class Quiz {
    public long id, lesson_id;
    public String a,b,c,d,e,as, qs, choice=null, status;
    public int attempts = 3;

    public Quiz(long id, long lesson_id, String a, String b, String c, String d, String e, String as, String qs) {
        this.id = id;
        this.lesson_id = lesson_id;
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.e = e;
        this.as = as;
        this.qs = qs;
        this.status = "pending";
    }
}
