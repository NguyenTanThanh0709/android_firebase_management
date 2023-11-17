package com.example.studentmanagement.Repo.Subject;

import com.example.studentmanagement.Models.Subject;

public interface OnSubjectRetrievedListener {
    void onSubjectRetrieved(Subject subject);
    void onError(String errorMessage);
}
