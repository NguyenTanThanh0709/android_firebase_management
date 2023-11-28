package com.example.studentmanagement.Models;

import com.example.studentmanagement.dto.CertificateDTO;

import java.time.LocalDate;

public class Certificate {

    private String id;
    private String name;
    private String startCertificate;
    private String endCertificate;
    private Double overalScore;
    private String describe;
    private String link;

    public Certificate(CertificateDTO certificateDTO) {
        this.id = certificateDTO.getId();
        this.name = certificateDTO.getName();
        this.startCertificate = certificateDTO.getStartCertificate();
        this.endCertificate = certificateDTO.getEndCertificate();
        this.overalScore = certificateDTO.getOveralScore();
        this.describe = certificateDTO.getDescribe();
        this.link = certificateDTO.getLink();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Certificate(String id, String name, String startCertificate, String endCertificate, Double overalScore, String describe, String link) {
        this.id = id;
        this.name = name;
        this.startCertificate = startCertificate;
        this.endCertificate = endCertificate;
        this.overalScore = overalScore;
        this.describe = describe;
        this.link = link;
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
