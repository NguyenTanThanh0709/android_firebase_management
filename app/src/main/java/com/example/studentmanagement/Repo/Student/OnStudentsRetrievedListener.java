package com.example.studentmanagement.Repo.Student;

import com.example.studentmanagement.Models.Student;

import java.util.List;

public interface OnStudentsRetrievedListener {
    public  void onStudentsRetrieved(List<Student> list);
    public  void onError(String error);
}
