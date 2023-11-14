package com.example.studentmanagement.Models;

import java.time.LocalDate;

public class ScoreSubject {
    private double socre;
    private Subject subject;
    private LocalDate startLearn;

    public ScoreSubject(double socre, Subject subject, LocalDate startLearn) {
        this.socre = socre;
        this.subject = subject;
        this.startLearn = startLearn;
    }

    public ScoreSubject() {
    }

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

    public LocalDate getStartLearn() {
        return startLearn;
    }

    public void setStartLearn(LocalDate startLearn) {
        this.startLearn = startLearn;
    }
}
