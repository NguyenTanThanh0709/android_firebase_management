package com.example.studentmanagement.utils;

import com.example.studentmanagement.Models.Subject;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class DatabaseManagerSubject {
    private FirebaseFirestore firestore;

    public DatabaseManagerSubject() {
        firestore = FirebaseFirestore.getInstance();
    }

    public Task<Void> addSubject(Subject subject) {
        // Use the generated ID directly in the document method
        return firestore.collection("subjects").document(subject.getId()).set(subject);
    }


    public Task<Void> updateSubject(String subjectId, Subject updatedSubject) {
        return firestore.collection("subjects").document(subjectId).set(updatedSubject);
    }

    public Task<Void> deleteSubject(String subjectId) {
        return firestore.collection("subjects").document(subjectId).delete();
    }

    public Task<DocumentSnapshot> getSubjectById(String subjectId) {
        return firestore.collection("subjects").document(subjectId).get();
    }

    public Task<QuerySnapshot> getAllSubjects() {
        return firestore.collection("subjects").get();
    }

}
