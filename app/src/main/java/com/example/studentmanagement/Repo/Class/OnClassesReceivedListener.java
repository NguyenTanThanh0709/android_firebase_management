package com.example.studentmanagement.Repo.Class;

import com.example.studentmanagement.Models.Class_;

import java.util.List;

public interface OnClassesReceivedListener {
    void onClassesReceived(List<Class_> classes);
    void onError(String errorMessage);
}



