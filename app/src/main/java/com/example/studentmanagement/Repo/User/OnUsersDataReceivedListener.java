package com.example.studentmanagement.Repo.User;

import com.example.studentmanagement.Models.User;

import java.util.List;

public interface OnUsersDataReceivedListener {
    void onUsersReceived(List<User> users);
    void onError(String errorMessage);
}
