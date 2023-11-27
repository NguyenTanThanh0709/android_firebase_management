package com.example.studentmanagement.Models;

import com.google.firebase.firestore.DocumentSnapshot;

import java.time.LocalDate;
import java.util.Map;

public class ScoreSubject {
    private String id;
    private double socre;
    private Subject subject;
    private String startLearn;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getStartLearn() {
        return startLearn;
    }

    public void setStartLearn(String startLearn) {
        this.startLearn = startLearn;
    }

    public ScoreSubject() {
    }

    public ScoreSubject(String id, double socre, Subject subject, String startLearn) {
        this.id = id;
        this.socre = socre;
        this.subject = subject;
        this.startLearn = startLearn;
    }

    public static ScoreSubject convertDocumentToScoreSubject(DocumentSnapshot document) {
        if (document.exists()) {
            ScoreSubject scoreSubject = new ScoreSubject();

            // Set properties from the document data
            scoreSubject.setId(document.getId());
            scoreSubject.setSocre(document.getDouble("socre")); // Assumes "score" is stored as a double in Firestore
            scoreSubject.setStartLearn(document.getString("startLearn"));

            // Convert nested Subject data
            Subject subject = convertSubjectData(document.getData().get("subject"));
            scoreSubject.setSubject(subject);

            return scoreSubject;
        } else {
            return null; // Document does not exist
        }
    }

    private static Subject convertSubjectData(Object subjectData) {
        if (subjectData instanceof Map) {
            Map<String, Object> subjectMap = (Map<String, Object>) subjectData;

            Subject subject = new Subject();
            subject.setId((String) subjectMap.get("id"));
            subject.setName((String) subjectMap.get("name"));
            subject.setImg((String) subjectMap.get("img"));
            // Add other properties as needed

            return subject;
        } else {
            return null; // Subject data is not in the expected format
        }
    }
}
