package com.example.studentmanagement.FireBase;

import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.studentmanagement.MainActivity;
import com.example.studentmanagement.Models.ScoreSubject;
import com.example.studentmanagement.Models.Student;
import com.example.studentmanagement.Models.Subject;
import com.example.studentmanagement.Repo.Class.OnStudentAddedListener;
import com.example.studentmanagement.Repo.Class.OnStudentsAddedListener;
import com.example.studentmanagement.Repo.Student.OnScoreSubjectAddedListener;
import com.example.studentmanagement.Repo.Student.OnStudentRetrievedListener;
import com.example.studentmanagement.Repo.Student.OnStudentsRetrievedListener;
import com.example.studentmanagement.Repo.Subject.OnSubjectRetrievedListener;
import com.google.android.material.color.utilities.Score;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FirebaseHelperStudent {

    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private FirebaseHelperSubject firebaseHelperSubject;

    public FirebaseHelperStudent() {
        // Khởi tạo databaseReference
        database = FirebaseDatabase.getInstance();
        this.databaseReference = database.getReference("list_students");
        firebaseHelperSubject = new FirebaseHelperSubject();
    }

    public void addStudent(Student student, OnStudentAddedListener listener) {
        databaseReference.child(student.getPhoneNumber()).setValue(student, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                if (error != null) {
                    listener.onError("Error adding student: " + error.getMessage());
                } else {
                    listener.onStudentAdded();
                }
            }
        });
    }

    public void addStudents(List<Student> studentList, OnStudentsAddedListener listener) {
        Map<String, Object> studentsMap = new HashMap<>();

        for (Student student : studentList) {
            studentsMap.put(student.getPhoneNumber(), student);
        }

        // Add the list of students to the students list
        databaseReference.updateChildren(studentsMap, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                if (error != null) {
                    listener.onError("Error adding students: " + error.getMessage());
                } else {
                    listener.onStudentsAdded();
                }
            }
        });
    }

    public void getStudentById(String studentId, OnStudentRetrievedListener listener) {
        DatabaseReference studentRef = databaseReference.child(studentId);

        studentRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Student student = dataSnapshot.getValue(Student.class);
                    listener.onStudentRetrieved(student);
                } else {
                    listener.onStudentNotFound();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                listener.onError("Error retrieving student: " + databaseError.getMessage());
            }
        });
    }

    // Get a list of all students
    public void getAllStudents(OnStudentsRetrievedListener listener) {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Student> studentList = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Student student = snapshot.getValue(Student.class);
                    studentList.add(student);
                }
                listener.onStudentsRetrieved(studentList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                listener.onError("Error retrieving students: " + databaseError.getMessage());
            }
        });
    }

    public void addScoreSubjectToStudent(String studentId, String SubjectId,double score, String StratLearn, OnScoreSubjectAddedListener listener) {

        firebaseHelperSubject.getsubjectById(SubjectId, new OnSubjectRetrievedListener() {
            @Override
            public void onSubjectRetrieved(Subject subject) {
                ScoreSubject scoreSubject = new ScoreSubject(score,subject,StratLearn);
                DatabaseReference studentRef = databaseReference.child(studentId).child("scoreSubjects");
                String scoreSubjectId = studentRef.push().getKey();
                scoreSubject.setId(scoreSubjectId);
                studentRef.child(scoreSubjectId).setValue(scoreSubject, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                        if (error != null) {
                            listener.onError("Error adding ScoreSubject: " + error.getMessage());
                        } else {
                            listener.onScoreSubjectAdded(scoreSubjectId);
                        }
                    }
                });
            }

            @Override
            public void onError(String errorMessage) {
            }
        });

    }
}
