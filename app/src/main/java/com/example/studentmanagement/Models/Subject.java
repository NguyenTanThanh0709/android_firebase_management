package com.example.studentmanagement.Models;

public class Subject {
    private String id;
    private String name;

    public Subject(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public Subject() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
