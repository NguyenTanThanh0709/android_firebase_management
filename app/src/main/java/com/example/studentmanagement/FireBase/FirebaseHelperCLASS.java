package com.example.studentmanagement.FireBase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.studentmanagement.Models.Class_;
import com.example.studentmanagement.Models.Student;
import com.example.studentmanagement.Repo.Class.OnClassAddedListener;
import com.example.studentmanagement.Repo.Class.OnClassReceivedListener;
import com.example.studentmanagement.Repo.Class.OnClassesAddedListener;
import com.example.studentmanagement.Repo.Class.OnClassesReceivedListener;
import com.example.studentmanagement.Repo.Class.OnStudentAddedListener;
import com.example.studentmanagement.Repo.Class.OnStudentsAddedListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FirebaseHelperCLASS {
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;

    public FirebaseHelperCLASS() {
        // Khởi tạo databaseReference
        database = FirebaseDatabase.getInstance();
        this.databaseReference = database.getReference("list_classes");
    }

    public void addClass(Class_ class_, OnClassAddedListener listener) {
        // Use the class ID as the key (you may customize this based on your data structure)
        String classId = databaseReference.push().getKey();
        class_.setId(classId);
        // Add the class to the database without including students
        databaseReference.child(classId).setValue(class_, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                if (error != null) {
                    listener.onError("Error adding class: " + error.getMessage());
                } else {
                    listener.onClassAdded();
                }
            }
        });
    }

    public void addClasses(List<Class_> classList, OnClassesAddedListener listener) {
        Map<String, Object> classesMap = new HashMap<>();

        for (Class_ class_ : classList) {
            // Use the class ID as the key
            String classId = databaseReference.push().getKey();
            class_.setId(classId);
            // Add the class to the Map
            classesMap.put(classId, class_);
        }

        // Add the list of classes to the database
        databaseReference.updateChildren(classesMap, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                if (error != null) {
                    listener.onError("Error adding classes: " + error.getMessage());
                } else {
                    listener.onClassesAdded();
                }
            }
        });
    }

    public void getClasses(OnClassesReceivedListener listener) {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Class_> classes = new ArrayList<>();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Class_ class_ = snapshot.getValue(Class_.class);
                    classes.add(class_);
                }

                listener.onClassesReceived(classes);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                listener.onError("Error retrieving classes: " + databaseError.getMessage());
            }
        });
    }

    public void getClassById(String classId, OnClassReceivedListener listener) {
        DatabaseReference classRef = databaseReference.child(classId);

        classRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Class_ class_ = dataSnapshot.getValue(Class_.class);
                    listener.onClassReceived(class_);
                } else {
                    listener.onError("Class with ID " + classId + " does not exist");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                listener.onError("Error retrieving class with ID " + classId + ": " + databaseError.getMessage());
            }
        });
    }

    public void addStudentToClass(String classId, Student student, OnStudentAddedListener listener) {
        DatabaseReference classRef = databaseReference.child(classId).child("students");
        classRef.child(student.getPhoneNumber()).setValue(student, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                if (error != null) {
                    listener.onError("Error adding student to class: " + error.getMessage());
                } else {
                    updateClassListStudents(classId, student.getPhoneNumber());
                    listener.onStudentAdded();
                }
            }
        });
    }

    private void updateClassListStudents(String classId, String studentId) {
        DatabaseReference classListRef = databaseReference.child(classId).child("students_list");
        classListRef.child(studentId).setValue(true);
    }

    public void addStudentsToClass(String classId, List<Student> studentList, OnStudentsAddedListener listener) {
        DatabaseReference classRef = databaseReference.child(classId).child("students");
        DatabaseReference classListRef = databaseReference.child(classId).child("students_list");
        Map<String, Object> studentsMap = new HashMap<>();

        for (Student student : studentList) {
            studentsMap.put(student.getPhoneNumber(), student);
            classListRef.child(student.getPhoneNumber()).setValue(true);
        }

        classRef.updateChildren(studentsMap, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                if (error != null) {
                    listener.onError("Error adding students to class: " + error.getMessage());
                } else {
                    listener.onStudentsAdded();
                }
            }
        });
    }

}
