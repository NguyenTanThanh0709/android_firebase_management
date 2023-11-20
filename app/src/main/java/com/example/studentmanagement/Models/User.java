package com.example.studentmanagement.Models;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class User {
    private String name;
    private  String phoneNumber;
    private String email;
    private String birthDay;
    private Boolean status;
    private String avatar;
    private Role role;
    private String password;



    private Map<String, HistoryLogin> historyLogins;

    public Map<String, HistoryLogin> getHistoryLogins() {
        return historyLogins;
    }

    public void setHistoryLogins(Map<String, HistoryLogin> historyLogins) {
        this.historyLogins = historyLogins;
    }

    public void addHistoryLogin(String customPushId, HistoryLogin historyLogin) {
        if (historyLogins == null) {
            historyLogins = new HashMap<>();
        }
        historyLogins.put(customPushId, historyLogin);
    }

    public User(String name, String phoneNumber, String email, String birthDay, Boolean status, String avatar, Role role, String password) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.birthDay = birthDay;
        this.status = status;
        this.avatar = avatar;
        this.role = role;
        this.password = password;
    }

    public User() {
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

    public Role getRole() {
        return role;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", birthDay='" + birthDay + '\'' +
                ", status=" + status +
                ", avatar='" + avatar + '\'' +
                ", role=" + role +
                ", password='" + password + '\'' +
                '}';
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
