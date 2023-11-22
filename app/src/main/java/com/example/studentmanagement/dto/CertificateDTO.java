package com.example.studentmanagement.dto;

public class CertificateDTO {

    private String id;
    private String name;
    private String startCertificate;
    private String endCertificate;
    private Double overalScore;
    private String describe;
    private String link;
    private String phoneStudent;

    public boolean isValid() {
        return id != null && !id.isEmpty() &&
                name != null && !name.isEmpty() &&
                startCertificate != null && !startCertificate.isEmpty() &&
                endCertificate != null && !endCertificate.isEmpty() &&
                describe != null && !describe.isEmpty() &&
                link != null && !link.isEmpty() &&
                phoneStudent != null && !phoneStudent.isEmpty() &&
                overalScore != null; // Check if overalScore is not null
    }

    public CertificateDTO() {
    }

    public CertificateDTO(String id, String name, String startCertificate, String endCertificate, double overalScore, String describe, String link, String phoneStudent) {
        this.id = id;
        this.name = name;
        this.startCertificate = startCertificate;
        this.endCertificate = endCertificate;
        this.overalScore = overalScore;
        this.describe = describe;
        this.link = link;
        this.phoneStudent = phoneStudent;
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

    public String getPhoneStudent() {
        return phoneStudent;
    }

    @Override
    public String toString() {
        return "CertificateDTO{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", startCertificate='" + startCertificate + '\'' +
                ", endCertificate='" + endCertificate + '\'' +
                ", overalScore=" + overalScore +
                ", describe='" + describe + '\'' +
                ", link='" + link + '\'' +
                ", phoneStudent='" + phoneStudent + '\'' +
                '}';
    }

    public void setPhoneStudent(String phoneStudent) {
        this.phoneStudent = phoneStudent;
    }
}
