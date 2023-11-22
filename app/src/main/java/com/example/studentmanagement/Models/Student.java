package com.example.studentmanagement.Models;

import java.time.LocalDate;
import java.util.List;

public class Student {
    private String name;
    private  String phoneNumber;
    private String email;
    private String birthDay;
    private Boolean sex;

    public Boolean getSex() {
        return sex;
    }

    public void setSex(Boolean sex) {
        this.sex = sex;
    }

    private Boolean status;
    private String avatar;
    private String startSchool;
    private String endSchool;
    private Double GPA;

    public Student(String name, String phoneNumber, String email, String birthDay, Boolean sex, Boolean status, String avatar, String startSchool, String endSchool, Double GPA, Class_ class_) {
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
    }

    public Double getGPA() {
        return GPA;
    }

    public void setGPA(Double GPA) {
        this.GPA = GPA;
    }

    private List<ScoreSubject> scoreSubjects;
    private List<Certificate> certificates;
    private Class_ class_;


    public Student(String name, String phoneNumber, String email, String birthDay, Boolean status, String avatar, String startSchool, String endSchool, Class_ class_) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.birthDay = birthDay;
        this.status = status;
        this.avatar = avatar;
        this.startSchool = startSchool;
        this.endSchool = endSchool;
        this.class_ = class_;
        this.GPA = 0.0;
    }

    public Student(String name, String phoneNumber, String email, String birthDay, Boolean sex, Boolean status, String avatar, String startSchool, String endSchool, List<ScoreSubject> scoreSubjects, List<Certificate> certificates, Class_ class_) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.birthDay = birthDay;
        this.sex = sex;
        this.status = status;
        this.GPA = 0.0;
        this.avatar = avatar;
        this.startSchool = startSchool;
        this.endSchool = endSchool;
        this.scoreSubjects = scoreSubjects;
        this.certificates = certificates;
        this.class_ = class_;
    }

    public Class_ getClass_() {
        return class_;
    }

    public void setClass_(Class_ class_) {
        this.class_ = class_;
    }

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

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", birthDay='" + birthDay + '\'' +
                ", sex=" + sex +
                ", status=" + status +
                ", avatar='" + avatar + '\'' +
                ", startSchool='" + startSchool + '\'' +
                ", endSchool='" + endSchool + '\'' +
                ", GPA=" + GPA +
                ", scoreSubjects=" + scoreSubjects +
                ", certificates=" + certificates +
                ", class_=" + class_ +
                '}';
    }
}
