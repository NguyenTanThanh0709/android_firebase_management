package com.example.studentmanagement.Models;

import java.time.LocalDate;

public class ScoreSubject {
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private double socre;
    private Subject subject;
    private String startLearn;

    public double getSocre() {
        return socre;
    }

    public void setSocre(double socre) {
        this.socre = socre;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public String getStartLearn() {
        return startLearn;
    }

    public void setStartLearn(String startLearn) {
        this.startLearn = startLearn;
    }

    public ScoreSubject() {
    }

    public ScoreSubject(double socre, Subject subject,  String startLearn) {
        this.socre = socre;
        this.subject = subject;

        this.startLearn = startLearn;
    }


}
