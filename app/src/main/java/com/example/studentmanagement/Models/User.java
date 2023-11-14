package com.example.studentmanagement.Models;

import java.time.LocalDate;
import java.util.List;

public class User {
    private String id;
    private String name;
    private  String phoneNumber;
    private String email;
    private LocalDate birthDay;
    private Boolean status;
    private String avatar;
    private Role role;
    private String password;
    private List<HistoryLogin> historyLogin;

    public User(String id, String name, String phoneNumber, String email, LocalDate birthDay, Boolean status, String avatar, Role role, String password, List<HistoryLogin> historyLogin) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.birthDay = birthDay;
        this.status = status;
        this.avatar = avatar;
        this.role = role;
        this.password = password;
        this.historyLogin = historyLogin;
    }

    public User() {
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

    public Role getRole() {
        return role;
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

    public List<HistoryLogin> getHistoryLogin() {
        return historyLogin;
    }

    public void setHistoryLogin(List<HistoryLogin> historyLogin) {
        this.historyLogin = historyLogin;
    }
}
