package com.example.studentmanagement.utils;

import com.example.studentmanagement.Models.Certificate;
import com.example.studentmanagement.Models.HistoryLogin;
import com.example.studentmanagement.Models.ScoreSubject;
import com.example.studentmanagement.Models.Student;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.WriteBatch;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DatabaseManagerStudent {
    private FirebaseFirestore firestore;

    public DatabaseManagerStudent() {
        firestore = FirebaseFirestore.getInstance();
    }

    public Task<Void> addListStudentsAndAssociateWithClass( List<Student> studentList) {
        // Create a batched write
        WriteBatch batch = firestore.batch();

        // Collection reference for students
        CollectionReference studentsCollection = firestore.collection("students");

        // Get the class information
        DatabaseManagerClass dbManagerClass = new DatabaseManagerClass();

        // List to hold all tasks for whenAllComplete
        List<Task<?>> tasks = new ArrayList<>();

        // Loop through the list and add each student to the batch
        for (Student student : studentList) {
            // Add student to the general "students" collection
            DocumentReference studentRef = studentsCollection.document(student.getPhoneNumber());
            batch.set(studentRef, student);

            // Add student to the specified class
            Task<Void> addStudentToClassTask = dbManagerClass.addStudentToClass(student.getClass_id(), student);

            // Add tasks to the list
            tasks.add(addStudentToClassTask);

            // IMPORTANT: Do not include updateStudentTask here, move it outside the loop
        }

        // Commit the batch
        tasks.add(batch.commit());

        // IMPORTANT: Move the updateStudentTask outside the loop
        for (Student student : studentList) {
            DocumentReference studentRef = studentsCollection.document(student.getPhoneNumber());
            Task<Void> updateStudentTask = studentRef.update("class_id", student.getClass_id());
            tasks.add(updateStudentTask);
        }

        // Combine tasks
        return Tasks.whenAllComplete(tasks).continueWith(task -> null);
    }


    public Task<Void> addStudentToClassAndAddClassToStudent(String classId, Student student) {
        DocumentReference studentRef = firestore.collection("students").document(student.getPhoneNumber());

        // Add the student document
        Task<Void> addStudentTask = studentRef.set(student);

        // Get the class information
        DatabaseManagerClass dbManagerClass = new DatabaseManagerClass();

        // Add the student to the specified class
        Task<Void> addStudentToClassTask = dbManagerClass.addStudentToClass(classId, student);

        // Update the student document to include class information
        Task<Void> updateStudentTask = studentRef.update("class_id", classId);

        // Combine the tasks
        return Tasks.whenAllComplete(addStudentTask, addStudentToClassTask, updateStudentTask)
                .continueWith(task -> null);
    }

    public Task<Void> updateStudent(String studentId, Student updatedStudent) {
        return firestore.collection("students").document(studentId).set(updatedStudent);
    }

    public Task<Void> deleteStudentById(String studentId) {
        // Assuming 'students' is the collection name
        return firestore.collection("students").document(studentId).delete();
    }

    public Task<DocumentSnapshot> getStudentById(String studentId) {
        return firestore.collection("students").document(studentId).get();
    }

    public Task<QuerySnapshot> getAllStudents() {
        return firestore.collection("students").get();
    }

    public Task<Void> addCertificate(String studentId, Certificate certificate) {
        String id = certificate.getId();

        return firestore.collection("students").document(studentId).collection("certificates")
                .document(id).set(certificate);
    }

    private String generateCustomPushId() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy-HH-mm-ss");
        LocalDateTime now = LocalDateTime.now();
        return formatter.format(now).toString().replace("-","");
    }

    public Task<Void> addSubjectScore(String studentId, ScoreSubject scoreSubject) {
        String id = generateCustomPushId();
        scoreSubject.setId(id);
        return firestore.collection("students").document(studentId).collection("scoresubjects")
                .document(id).set(scoreSubject);
    }

    public Task<Void> updateSubjectScore(String studentId, String scoreSubjectId, ScoreSubject updatedScoreSubject) {
        // Assuming 'students' is the collection name
        // and 'scoresubjects' is a subcollection within a student document
        DocumentReference scoreSubjectRef = firestore.collection("students").document(studentId)
                .collection("scoresubjects").document(scoreSubjectId);

        // Convert the updatedScoreSubject object to a Map
        Map<String, Object> updatedData = new HashMap<>();
        updatedData.put("startLearn", updatedScoreSubject.getStartLearn());  // replace "subjectName" with other fields as needed
        updatedData.put("socre", updatedScoreSubject.getSocre());
        // Add other fields as needed

        // Update the scoreSubject document
        return scoreSubjectRef.update(updatedData);
    }

    public Task<Void> deleteSubjectScore(String studentId, String scoreSubjectId) {
        // Assuming 'students' is the collection name
        // and 'scoresubjects' is a subcollection within a student document
        DocumentReference scoreSubjectRef = firestore.collection("students").document(studentId)
                .collection("scoresubjects").document(scoreSubjectId);

        // Delete the scoreSubject document
        return scoreSubjectRef.delete();
    }

    public Task<QuerySnapshot> getAllSubjectScores(String studentId) {

        return firestore.collection("students").document(studentId)
                .collection("scoresubjects").get();
    }

    public Task<DocumentSnapshot> getSubjectScoreById(String studentId, String scoreSubjectId) {
        // Assuming 'students' is the collection name
        // and 'scoresubjects' is a subcollection within a student document
        return firestore.collection("students").document(studentId)
                .collection("scoresubjects").document(scoreSubjectId).get();
    }

    public Task<Void> addCertificates(String studentId, List<Certificate> certificateList) {
        // Assuming 'students' is the collection name
        // and 'certificates' is a subcollection within a student document

        // Create a batched write
        WriteBatch batch = firestore.batch();

        // Collection reference for certificates
        CollectionReference certificatesCollection = firestore.collection("students").document(studentId).collection("certificates");

        // Loop through the list and add each certificate to the batch with a specific ID
        for (Certificate certificate : certificateList) {
            // Assuming you have a method to generate a unique ID for certificates


            // Create a DocumentReference with the specific ID
            DocumentReference certificateRef = certificatesCollection.document(certificate.getId());

            // Set the certificate data in the batch
            batch.set(certificateRef, certificate);
        }

        // Commit the batch
        return batch.commit();
    }

    public Task<QuerySnapshot> getAllCertificates(String studentId) {
        // Assuming 'students' is the collection name
        // and 'certificates' is a subcollection within a student document
        return firestore.collection("students").document(studentId).collection("certificates").get();
    }

    public Task<DocumentSnapshot> getCertificateById(String studentId, String certificateId) {
        // Assuming 'students' is the collection name
        // and 'certificates' is a subcollection within a student document
        return firestore.collection("students").document(studentId).collection("certificates").document(certificateId).get();
    }

    public Task<Void> deleteCertificate(String studentId, String certificateId) {
        // Assuming 'students' is the collection name
        // and 'certificates' is a subcollection within a student document
        DocumentReference certificateRef = firestore.collection("students").document(studentId).collection("certificates").document(certificateId);

        // Delete the certificate document
        return certificateRef.delete();
    }

    public Task<Void> updateCertificate(String studentId, String certificateId, Certificate updatedCertificate) {
        // Assuming 'students' is the collection name
        // and 'certificates' is a subcollection within a student document
        DocumentReference certificateRef = firestore.collection("students").document(studentId).collection("certificates").document(certificateId);

        // Update the certificate document
        return certificateRef.set(updatedCertificate);
    }
}
