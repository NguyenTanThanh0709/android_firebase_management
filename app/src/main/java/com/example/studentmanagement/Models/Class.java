package com.example.studentmanagement.Models;

import java.util.List;

public class Class {
    private String id;
    private String name;
    private List<Student> students;

    public Class(String id, String name, List<Student> students) {
        this.id = id;
        this.name = name;
        this.students = students;
    }

    public Class() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }
}
