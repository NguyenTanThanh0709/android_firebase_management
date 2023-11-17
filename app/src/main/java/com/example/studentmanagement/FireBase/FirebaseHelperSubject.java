package com.example.studentmanagement.FireBase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.studentmanagement.Models.Class_;
import com.example.studentmanagement.Models.Subject;
import com.example.studentmanagement.Repo.Class.OnClassReceivedListener;
import com.example.studentmanagement.Repo.Class.OnClassesReceivedListener;
import com.example.studentmanagement.Repo.Subject.OnSubjectAddedListener;
import com.example.studentmanagement.Repo.Subject.OnSubjectRetrievedListener;
import com.example.studentmanagement.Repo.Subject.OnSubjectsAddedListener;
import com.example.studentmanagement.Repo.Subject.OnSubjectsRetrievedListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FirebaseHelperSubject {
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;

    public FirebaseHelperSubject() {
        database = FirebaseDatabase.getInstance();
        this.databaseReference = database.getReference("list_subjects");
    }

    public void getsubjects(OnSubjectsRetrievedListener listener) {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Subject> subjectList = new ArrayList<>();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Subject subject = snapshot.getValue(Subject.class);
                    subjectList.add(subject);
                }

                listener.onSubjectsRetrieved(subjectList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                listener.onError("Error retrieving classes: " + databaseError.getMessage());
            }
        });
    }

    public void getsubjectById(String classId, OnSubjectRetrievedListener listener) {
        DatabaseReference classRef = databaseReference.child(classId);

        classRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Subject subject = dataSnapshot.getValue(Subject.class);
                    listener.onSubjectRetrieved(subject);
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

    public void addSubject(Subject subject, OnSubjectAddedListener listener) {
        String subjectId = databaseReference.push().getKey();
        subject.setId(subjectId);

        // Add the subject to the subjects list
        databaseReference.child(subjectId).setValue(subject, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                if (error != null) {
                    listener.onError("Error adding subject: " + error.getMessage());
                } else {
                    listener.onSubjectAdded(subjectId);
                }
            }
        });
    }

    public void addSubjects(List<Subject> subjectList, OnSubjectsAddedListener listener) {
        Map<String, Object> subjectsMap = new HashMap<>();

        for (Subject subject : subjectList) {
            String subjectId = databaseReference.push().getKey();
            subject.setId(subjectId);
            subjectsMap.put(subjectId, subject);
        }

        // Add the list of subjects to the subjects list
        databaseReference.updateChildren(subjectsMap, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                if (error != null) {
                    listener.onError("Error adding subjects: " + error.getMessage());
                } else {
                    listener.onSubjectsAdded();
                }
            }
        });
    }
}
