package com.example.studentmanagement.Repo.Subject;

import com.example.studentmanagement.Models.Subject;

import java.util.List;

public interface OnSubjectsRetrievedListener {
    void onSubjectsRetrieved(List<Subject> subjectList);
    void onError(String errorMessage);
}
