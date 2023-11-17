package com.example.studentmanagement.Models;

import java.time.LocalDate;
import java.util.List;

public class Student {
    private String name;
    private  String phoneNumber;
    private String email;
    private String birthDay;
    private Boolean status;
    private String avatar;
    private String startSchool;
    private String endSchool;
    private List<ScoreSubject> scoreSubjects;
    private List<Certificate> certificates;

    public List<ScoreSubject> getScoreSubjects() {
        return scoreSubjects;
    }

    public void setScoreSubjects(List<ScoreSubject> scoreSubjects) {
        this.scoreSubjects = scoreSubjects;
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

    public List<Certificate> getCertificates() {
        return certificates;
    }

    public void setCertificates(List<Certificate> certificates) {
        this.certificates = certificates;
    }

    public Student() {
    }

    public Student(String name, String phoneNumber, String email, String birthDay, Boolean status, String avatar, String startSchool, String endSchool) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.birthDay = birthDay;
        this.status = status;
        this.avatar = avatar;
        this.startSchool = startSchool;
        this.endSchool = endSchool;
    }

    public Student(String name, String phoneNumber, String email, String birthDay, Boolean status, String avatar, String startSchool, String endSchool, List<Certificate> certificates) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.birthDay = birthDay;
        this.status = status;
        this.avatar = avatar;
        this.startSchool = startSchool;
        this.endSchool = endSchool;
        this.certificates = certificates;
    }



}
