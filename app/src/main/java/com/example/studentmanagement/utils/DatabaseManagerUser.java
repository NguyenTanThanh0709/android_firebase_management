package com.example.studentmanagement.utils;

import android.widget.Toast;

import com.example.studentmanagement.Models.HistoryLogin;
import com.example.studentmanagement.Models.User;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DatabaseManagerUser {
    private FirebaseFirestore firestore;
    public DatabaseManagerUser() {
        firestore = FirebaseFirestore.getInstance();
    }
    public Task<Void> addUser(User user) {
        return firestore.collection("users").document(user.getEmail()).set(user);
    }

    private String generateCustomPushId() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy-HH-mm-ss");
        LocalDateTime now = LocalDateTime.now();
        return formatter.format(now).toString().replace("-","");
    }


    public Task<Void> addHistoryLogin(String userId, HistoryLogin historyLogin) {
        String id = generateCustomPushId();
        historyLogin.setId(id);
        return firestore.collection("users").document(userId).collection("historyLogins")
                .document(id).set(historyLogin);
    }

    public Task<DocumentSnapshot> getUserById(String userId) {
        return firestore.collection("users").document(userId).get();
    }

    public Task<Void> deleteUserById(String userId) {
        return firestore.collection("users").document(userId).delete();
    }

    public Task<QuerySnapshot> getAllUsers() {
        return firestore.collection("users").get();
    }

    public Task<Void> updateUser(String userId, User updatedUser) {
        return firestore.collection("users").document(userId).set(updatedUser);
    }
}
