package com.example.studentmanagement.Models;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.List;

public class Class_ {

    private String id;
    private String name;
    private List<Student> students;

    public Class_(String id, String name, List<Student> students) {
        this.id = id;
        this.name = name;
        this.students = students;
    }

    public static Class_ updateFromDocumentSnapshot(DocumentSnapshot document) {
        // Update the name if it exists in the document
        String name ="";
        String id = "";
        List<Student> list =null;
        if (document.contains("name")) {
            name = document.getString("name");
        }

        if (document.contains("id")) {
            name = document.getString("name");
        }

        // Update the students list if it exists in the document
        if (document.contains("students")) {
            // Correct way to retrieve a List from Firestore
            list = (List<Student>) document.get("students");
        }

        Class_ class_ = new Class_(id, name,list);
        return class_;
    }

    public static Class_ fromQueryDocumentSnapshot(QueryDocumentSnapshot document) {
        String id = document.getString("id");
        String name = document.getString("name");

        // Correct way to retrieve a List from Firestore
        List<Student> students = (List<Student>) document.get("students");

        Class_ class_ = new Class_(id, name,students);
        return class_;
    }

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
