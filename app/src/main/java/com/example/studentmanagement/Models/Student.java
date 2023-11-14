package com.example.studentmanagement.Models;

import java.time.LocalDate;
import java.util.List;

public class Student {
    private String id;
    private String name;
    private  String phoneNumber;
    private String email;
    private LocalDate birthDay;
    private Boolean status;
    private String avatar;
    private LocalDate startSchool;
    private LocalDate endSchool;
    private double GPA;

    public Student() {
    }

    public Student(String id, String name, String phoneNumber, String email, LocalDate birthDay, Boolean status, String avatar, LocalDate startSchool, LocalDate endSchool, double GPA, Class aClass, List<ScoreSubject> scoreSubjects, List<Certificate> certificates) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.birthDay = birthDay;
        this.status = status;
        this.avatar = avatar;
        this.startSchool = startSchool;
        this.endSchool = endSchool;
        this.GPA = GPA;
        this.aClass = aClass;
        this.scoreSubjects = scoreSubjects;
        this.certificates = certificates;
    }

    private Class aClass;
    private List<ScoreSubject> scoreSubjects;
    private List<Certificate> certificates;

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

    public LocalDate getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(LocalDate birthDay) {
        this.birthDay = birthDay;
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

    public LocalDate getStartSchool() {
        return startSchool;
    }

    public void setStartSchool(LocalDate startSchool) {
        this.startSchool = startSchool;
    }

    public LocalDate getEndSchool() {
        return endSchool;
    }

    public void setEndSchool(LocalDate endSchool) {
        this.endSchool = endSchool;
    }

    public double getGPA() {
        return GPA;
    }

    public void setGPA(double GPA) {
        this.GPA = GPA;
    }

    public Class getaClass() {
        return aClass;
    }

    public void setaClass(Class aClass) {
        this.aClass = aClass;
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
}
