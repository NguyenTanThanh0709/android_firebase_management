package com.example.studentmanagement.Models;

import java.util.Map;

public class Subject {
    private String name;
    private String id;
    private String img;

    public static Subject fromMap(Map<String, Object> map) {
        Subject subject = new Subject();
        subject.setId((String) map.get("id"));
        subject.setName((String) map.get("name"));
        subject.setImg((String) map.get("img"));
        // Set other properties...
        return subject;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Subject(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Subject(String name, String id, String img) {
        this.name = name;
        this.id = id;
        this.img = img;
    }

    public Subject() {
    }

}
