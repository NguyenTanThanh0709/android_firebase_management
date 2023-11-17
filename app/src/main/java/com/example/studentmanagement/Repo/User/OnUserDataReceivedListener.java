package com.example.studentmanagement.Repo.User;

import com.example.studentmanagement.Models.User;

import java.util.List;

public interface OnUserDataReceivedListener {
    void onUserReceived(User user);
    void onError(String errorMessage);

}
