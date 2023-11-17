package com.example.studentmanagement.Repo.Class;

import com.example.studentmanagement.Models.Class_;

public interface OnClassReceivedListener {
    void onClassReceived(Class_ class_);
    void onError(String errorMessage);
}
