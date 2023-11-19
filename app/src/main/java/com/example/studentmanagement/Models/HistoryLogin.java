package com.example.studentmanagement.Models;

import java.time.LocalDateTime;

public class HistoryLogin {
    private String id;
    private String startLogin;
    private String StartLogout;
    private  String locate;
    private String idAdress;


    public HistoryLogin(String id, String startLogin, String startLogout, String locate, String idAdress) {
        this.id = id;
        this.startLogin = startLogin;
        StartLogout = startLogout;
        this.locate = locate;
        this.idAdress = idAdress;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStartLogin() {
        return startLogin;
    }

    public void setStartLogin(String startLogin) {
        this.startLogin = startLogin;
    }

    public String getStartLogout() {
        return StartLogout;
    }

    public void setStartLogout(String startLogout) {
        StartLogout = startLogout;
    }

    public String getLocate() {
        return locate;
    }

    public void setLocate(String locate) {
        this.locate = locate;
    }

    public String getIdAdress() {
        return idAdress;
    }

    public void setIdAdress(String idAdress) {
        this.idAdress = idAdress;
    }

    public HistoryLogin() {
    }

}
