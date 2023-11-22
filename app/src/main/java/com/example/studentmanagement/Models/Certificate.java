package com.example.studentmanagement.Models;

import java.time.LocalDate;

public class Certificate {

    private String id;
    private String name;
    private String startCertificate;
    private String endCertificate;
    private Double overalScore;
    private String describe;
    private String link;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public Certificate() {
    }

    public Certificate( String name, String startCertificate, String endCertificate, double overalScore, String describe, String link) {

        this.name = name;
        this.startCertificate = startCertificate;
        this.endCertificate = endCertificate;
        this.overalScore = overalScore;
        this.describe = describe;
        this.link = link;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartCertificate() {
        return startCertificate;
    }

    public void setStartCertificate(String startCertificate) {
        this.startCertificate = startCertificate;
    }
    public String getEndCertificate() {
        return endCertificate;
    }

    public void setEndCertificate(String endCertificate) {
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
