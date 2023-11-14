package com.example.studentmanagement.Models;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class HistoryLogin {
    private User user;
    private LocalDateTime startLogin;
    private LocalDateTime StartLogout;

    public HistoryLogin(User user, LocalDateTime startLogin, LocalDateTime startLogout) {
        this.user = user;
        this.startLogin = startLogin;
        StartLogout = startLogout;
    }

    public HistoryLogin() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getStartLogin() {
        return startLogin;
    }

    public void setStartLogin(LocalDateTime startLogin) {
        this.startLogin = startLogin;
    }

    public LocalDateTime getStartLogout() {
        return StartLogout;
    }

    public void setStartLogout(LocalDateTime startLogout) {
        StartLogout = startLogout;
    }
}
