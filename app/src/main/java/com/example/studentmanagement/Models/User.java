package com.example.studentmanagement.Models;

import com.google.firebase.firestore.QueryDocumentSnapshot;

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
    private Boolean sex;

    private Map<String, HistoryLogin> historyLogins;


    public User(String name, String phoneNumber, String email, String birthDay, Boolean status, String avatar, Role role, String password, Boolean sex, Map<String, HistoryLogin> historyLogins) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.birthDay = birthDay;
        this.status = status;
        this.avatar = avatar;
        this.role = role;
        this.password = password;
        this.sex = sex;
        this.historyLogins = historyLogins;
    }

    public Boolean getSex() {
        return sex;
    }

    public void setSex(Boolean sex) {
        this.sex = sex;
    }


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
        this.sex = true;
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

    private static Role convertToUserRole(String roleString) {
        if ("ADMIN".equals(roleString)) {
            return Role.ADMIN;
        } else if ("MANAGER".equals(roleString)) {
            return Role.MANAGER;
        } else if ("EMPLOYEE".equals(roleString)) {
            return Role.EMPLOYEE;
        } else {
            // Handle unknown role or return a default value
            return Role.EMPLOYEE;
        }
    }


    public static  User fromQueryDocumentSnapshot(QueryDocumentSnapshot document) {
        String name = document.getString("name");
        String phoneNumber = document.getString("phoneNumber");
        String email = document.getString("email");
        String birthDay = document.getString("birthDay");
        Boolean status = document.getBoolean("status");
        String avatar = document.getString("avatar");
        String roleString = document.getString("role"); // Đảm bảo rằng role lưu trong Firestore là một chuỗi
        Role role = convertToUserRole(roleString);
        String password = document.getString("password");
        Boolean sex = document.getBoolean("sex");

        // Khởi tạo đối tượng User từ các giá trị thu được
        User user = new User(name, phoneNumber, email, birthDay, status, avatar, role, password, sex);

        return user;
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
                ", sex=" + sex +
                '}';
    }

    public User(String name, String phoneNumber, String email, String birthDay, Boolean status, String avatar, Role role, String password, Boolean sex) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.birthDay = birthDay;
        this.status = status;
        this.avatar = avatar;
        this.role = role;
        this.password = password;
        this.sex = sex;
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
