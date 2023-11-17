package com.example.studentmanagement.Repo.Student;

public interface OnScoreSubjectAddedListener {
    void onScoreSubjectAdded(String scoreSubjectId);
    void onError(String errorMessage);
}
