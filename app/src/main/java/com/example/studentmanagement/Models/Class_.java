package com.example.studentmanagement.Models;

import java.util.List;

public class Class_ {

    private String id;
    private String name;
    private List<Student> students;

    public Class_(String name) {

        this.name = name;
        this.students = null;
    }

    public Class_(String name, List<Student> students) {

        this.name = name;
        this.students = students;
    }

    public Class_() {
    }

    public Class_(String id, String name) {
        this.id = id;
        this.name = name;
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

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Class_{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    public void setId(String id) {
        this.id = id;
    }
}
