package com.example.studentmanagement.Repo.Student;

import com.example.studentmanagement.Models.Student;

public interface OnStudentRetrievedListener {
    public  void onStudentRetrieved(Student student);
    public  void onStudentNotFound();
    public  void onError(String ok);
}
