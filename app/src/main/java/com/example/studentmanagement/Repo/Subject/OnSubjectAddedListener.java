package com.example.studentmanagement.Repo.Subject;

public interface OnSubjectAddedListener {
    void onSubjectAdded(String subjectId);
    void onError(String errorMessage);
}
