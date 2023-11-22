package com.example.studentmanagement.dto;

import com.example.studentmanagement.Models.Certificate;
import com.example.studentmanagement.Models.Class_;
import com.example.studentmanagement.Models.ScoreSubject;

import java.util.List;

public class StudentDTO {
    private String name;
    private  String phoneNumber;
    private String email;
    private String birthDay;
    private Boolean status;
    private Boolean sex;
    private String avatar;
    private String startSchool;
    private String endSchool;
    private String class_;

    public StudentDTO() {
    }

    public boolean isValid() {
        return name != null && !name.isEmpty() &&
                phoneNumber != null && !phoneNumber.isEmpty() &&
                email != null && !email.isEmpty() &&
                birthDay != null && !birthDay.isEmpty() &&
                status != null &&
                sex != null &&
                avatar != null && !avatar.isEmpty() &&
                startSchool != null && !startSchool.isEmpty() &&
                endSchool != null && !endSchool.isEmpty() &&
                class_ != null && !class_.isEmpty();
    }

    public StudentDTO(String name, String phoneNumber, String email, String birthDay, Boolean status, Boolean sex, String avatar, String startSchool, String endSchool, String class_) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.birthDay = birthDay;
        this.status = status;
        this.sex = sex;
        this.avatar = avatar;
        this.startSchool = startSchool;
        this.endSchool = endSchool;
        this.class_ = class_;
    }

    public Boolean getSex() {
        return sex;
    }

    public void setSex(Boolean sex) {
        this.sex = sex;
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

    public String getClass_() {
        return class_;
    }

    public void setClass_(String class_) {
        this.class_ = class_;
    }

    @Override
    public String toString() {
        return "StudentDTO{" +
                "name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", birthDay='" + birthDay + '\'' +
                ", status=" + status +
                ", sex=" + sex +
                ", avatar='" + avatar + '\'' +
                ", startSchool='" + startSchool + '\'' +
                ", endSchool='" + endSchool + '\'' +
                ", class_='" + class_ + '\'' +
                '}';
    }
}
