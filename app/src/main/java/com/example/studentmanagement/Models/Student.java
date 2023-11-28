package com.example.studentmanagement.Models;

import com.example.studentmanagement.dto.StudentDTO;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class Student {
    private String name;
    private  String phoneNumber;
    private String email;
    private String birthDay;
    private Boolean sex;
    private Boolean status;
    private String avatar;
    private String startSchool;
    private String endSchool;
    private Double GPA;
    private Class_ class_;
    private List<ScoreSubject> scoreSubjects;
    private List<Certificate> certificates;

    public Student(String name, String phoneNumber, String email, String birthDay, Boolean sex, Boolean status, String avatar, String startSchool, String endSchool, Double GPA) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.birthDay = birthDay;
        this.sex = sex;
        this.status = status;
        this.avatar = avatar;
        this.startSchool = startSchool;
        this.endSchool = endSchool;
        this.GPA = GPA;
    }

    public static Student fromStudentDTO(StudentDTO studentDTO, Class_ class_) {
        // Perform the conversion from StudentDTO to Student
        String name = studentDTO.getName();
        String phoneNumber = studentDTO.getPhoneNumber();
        String email = studentDTO.getEmail();
        String birthDay = studentDTO.getBirthDay();
        Boolean sex = studentDTO.getSex();
        Boolean status = studentDTO.getStatus();
        String avatar = studentDTO.getAvatar();
        String startSchool = studentDTO.getStartSchool();
        String endSchool = studentDTO.getEndSchool();
        String classId = studentDTO.getClass_(); // Assuming class_ is the class ID

        // Create a new Student object with the converted data
        return new Student(classId,name, phoneNumber, email, birthDay, sex, status, avatar, startSchool, endSchool,0.0,class_);
    }

    public Student(String class_id,String name, String phoneNumber, String email, String birthDay, Boolean sex, Boolean status, String avatar, String startSchool, String endSchool, Double GPA, Class_ class_) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.birthDay = birthDay;
        this.sex = sex;
        this.status = status;
        this.avatar = avatar;
        this.startSchool = startSchool;
        this.endSchool = endSchool;
        this.GPA = GPA;
        this.class_ = class_;
        this.class_id = class_id;
    }

    private String class_id;

    public String getClass_id() {
        return class_id;
    }

    public void setClass_id(String class_id) {
        this.class_id = class_id;
    }

    public Student(String name, String phoneNumber, String email, String birthDay, Boolean sex, Boolean status, String avatar, String startSchool, String endSchool, Double GPA, List<ScoreSubject> scoreSubjects, List<Certificate> certificates, Class_ class_) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.birthDay = birthDay;
        this.sex = sex;
        this.status = status;
        this.avatar = avatar;
        this.startSchool = startSchool;
        this.endSchool = endSchool;
        this.GPA = GPA;
        this.scoreSubjects = scoreSubjects;
        this.certificates = certificates;
        this.class_ = class_;
    }

    public Student() {
    }

    public static Student fromQueryDocumentSnapshot(QueryDocumentSnapshot document) {
        String name = document.getString("name");
        String phoneNumber = document.getString("phoneNumber");
        String email = document.getString("email");
        String birthDay = document.getString("birthDay");
        Boolean sex = document.getBoolean("sex");
        Boolean status = document.getBoolean("status");
        String avatar = document.getString("avatar");
        String startSchool = document.getString("startSchool");
        String endSchool = document.getString("endSchool");
        String class_id = document.getString("class_id");
        Double GPA = document.getDouble("gpa");

        Object classObj = document.get("class_");
        Class_ class_ = null;

        if (classObj instanceof Map) {
            // Check if 'classObj' is a HashMap
            Map<String, Object> classMap = (Map<String, Object>) classObj;

            // Assuming you have keys like "key1", "key2", "key3" in your HashMap
            String name_ = (String) classMap.get("name");
            String id = (String) classMap.get("id");
            class_ = new Class_(id,name_);
        } else {
            // Handle the case where 'classObj' is not a HashMap
            // You may need to adjust this based on the actual structure of your data
            System.out.println("Invalid data type for 'classObj'");
        }



        // Create and return the Student object with the nested Class_
        return new Student(class_id,name, phoneNumber, email, birthDay, sex, status, avatar, startSchool, endSchool, GPA, class_);
    }




    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
    }

    public Boolean getSex() {
        return sex;
    }

    public void setSex(Boolean sex) {
        this.sex = sex;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getStartSchool() {
        return startSchool;
    }

    public void setStartSchool(String startSchool) {
        this.startSchool = startSchool;
    }

    public String getEndSchool() {
        return endSchool;
    }

    public void setEndSchool(String endSchool) {
        this.endSchool = endSchool;
    }

    public Double getGPA() {
        return GPA;
    }

    public void setGPA(Double GPA) {
        this.GPA = GPA;
    }

    public List<ScoreSubject> getScoreSubjects() {
        return scoreSubjects;
    }

    public void setScoreSubjects(List<ScoreSubject> scoreSubjects) {
        this.scoreSubjects = scoreSubjects;
    }

    public List<Certificate> getCertificates() {
        return certificates;
    }

    public void setCertificates(List<Certificate> certificates) {
        this.certificates = certificates;
    }

    public Class_ getClass_() {
        return class_;
    }

    public void setClass_(Class_ class_) {
        this.class_ = class_;
    }




    private static Class_ convertClassDocumentSnapshot(DocumentSnapshot classDocument) {
        // Use your existing method or logic to convert the class document to a Class_ object
        // For example:
        String className = classDocument.getString("className");
        // ... other properties ...
        return new Class_(className);
    }
}
