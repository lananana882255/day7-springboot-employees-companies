package com.example.SpringBootDemo;

public class Company {
    private long id;
    private String name;
    private static long nextId=1;
    public Company(){
        this.id=this.nextId++;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
