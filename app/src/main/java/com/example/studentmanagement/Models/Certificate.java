package com.example.studentmanagement.Models;

import java.time.LocalDate;

public class Certificate {
    private String id;
    private String name;
    private LocalDate startCertificate;
    private LocalDate endCertificate;
    private double overalScore;
    private String describe;
    private String link;

    public Certificate() {
    }

    public Certificate(String id, String name, LocalDate startCertificate, LocalDate endCertificate, double overalScore, String describe, String link) {
        this.id = id;
        this.name = name;
        this.startCertificate = startCertificate;
        this.endCertificate = endCertificate;
        this.overalScore = overalScore;
        this.describe = describe;
        this.link = link;
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

    public LocalDate getStartCertificate() {
        return startCertificate;
    }

    public void setStartCertificate(LocalDate startCertificate) {
        this.startCertificate = startCertificate;
    }

    public LocalDate getEndCertificate() {
        return endCertificate;
    }

    public void setEndCertificate(LocalDate endCertificate) {
        this.endCertificate = endCertificate;
    }

    public double getOveralScore() {
        return overalScore;
    }

    public void setOveralScore(double overalScore) {
        this.overalScore = overalScore;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }



}
