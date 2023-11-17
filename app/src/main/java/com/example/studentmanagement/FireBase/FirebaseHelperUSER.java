package com.example.studentmanagement.FireBase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.studentmanagement.Models.User;
import com.example.studentmanagement.Repo.User.OnUserAddedListener;
import com.example.studentmanagement.Repo.User.OnUserDataReceivedListener;
import com.example.studentmanagement.Repo.User.OnUsersAddedListener;
import com.example.studentmanagement.Repo.User.OnUsersDataReceivedListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FirebaseHelperUSER {

    private FirebaseDatabase database;
    private DatabaseReference databaseReference;

    public FirebaseHelperUSER() {
        // Khởi tạo databaseReference
        database = FirebaseDatabase.getInstance();
        this.databaseReference = database.getReference("list_users");
    }

    public void getUserById(String userId, OnUserDataReceivedListener listener) {
        DatabaseReference userRef = databaseReference.child(userId);
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    User user = dataSnapshot.getValue(User.class);
                    listener.onUserReceived(user);
                } else {
                    listener.onError("User with ID " + userId + " does not exist");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                listener.onError("Error retrieving user with ID " + userId + ": " + databaseError.getMessage());
            }
        });
    }

    public void getListUsers(OnUsersDataReceivedListener  listener) {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<User> users = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    User user = dataSnapshot.getValue(User.class);
                    users.add(user);
                }
                listener.onUsersReceived(users);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                listener.onError("Failed to load users!");
            }
        });
    }

    public void addUser(User user, OnUserAddedListener listener) {
        // Sử dụng số điện thoại làm khóa (có thể thay đổi tùy thuộc vào cấu trúc dữ liệu của bạn)
        String userId = user.getPhoneNumber();

        // Thêm người dùng vào cơ sở dữ liệu
        databaseReference.child(userId).setValue(user, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                if (error != null) {
                    listener.onError("Error adding user: " + error.getMessage());
                } else {
                    listener.onUserAdded();
                }
            }
        });
    }

    public void addUsers(List<User> userList, OnUsersAddedListener listener) {
        Map<String, Object> usersMap = new HashMap<>();

        for (User user : userList) {
            // Sử dụng số điện thoại làm khóa (có thể thay đổi tùy thuộc vào cấu trúc dữ liệu của bạn)
            String userId = user.getPhoneNumber();

            // Thêm người dùng vào Map
            usersMap.put(userId, user);
        }

        // Thêm danh sách người dùng vào cơ sở dữ liệu
        databaseReference.updateChildren(usersMap, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                if (error != null) {
                    listener.onError("Error adding users: " + error.getMessage());
                } else {
                    listener.onUsersAdded();
                }
            }
        });
    }

}
