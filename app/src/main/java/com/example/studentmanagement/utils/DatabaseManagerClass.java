package com.example.studentmanagement.utils;

import com.example.studentmanagement.Models.Class_;
import com.example.studentmanagement.Models.Student;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;
import java.util.Map;

public class DatabaseManagerClass {
    private FirebaseFirestore firestore;

    public DatabaseManagerClass() {
        firestore = FirebaseFirestore.getInstance();
    }

    public Task<Void> addClass(Class_ class_) {
        return firestore.collection("classes").document(class_.getId()).set(class_);
    }

    public Task<Void> updateClass(String classId, Class_ updatedClass) {
        return firestore.collection("classes").document(classId).set(updatedClass);
    }

    public Task<Void> deleteClass(String classId) {
        return firestore.collection("classes").document(classId).delete();
    }

    public Task<DocumentSnapshot> getClassById(String classId) {
        return firestore.collection("classes").document(classId).get();
    }

    public Task<QuerySnapshot> getAllClasses() {
        return firestore.collection("classes").get();
    }

    public Task<Void> addStudentToClass(String classId, Student student) {
        return firestore.collection("classes").document(classId).collection("students")
                .document(student.getPhoneNumber()).set(student);
    }

    public Task<Void> deleteStudentFromClass(String classId, String phoneNumber) {
        return firestore.collection("classes").document(classId).collection("students")
                .document(phoneNumber).delete();
    }


}
