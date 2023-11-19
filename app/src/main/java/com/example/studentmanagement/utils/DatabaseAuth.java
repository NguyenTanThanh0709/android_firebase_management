package com.example.studentmanagement.utils;

import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.studentmanagement.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class DatabaseAuth {
    private FirebaseAuth mAuth;
    public DatabaseAuth(){
        mAuth = FirebaseAuth.getInstance();
    }

    public DatabaseAuth(FirebaseAuth mAuth){
        mAuth = mAuth;
    }

    private void login(String email, String pass){
        mAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                }else {

                }
            }
        });
    }

    private void CreateNewUser(String email, String pass){
        mAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                }else {

                }
            }
        });
    }

    private void resetPass(String email){
        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                        }else {

                        }
                    }
                });
    }

    private void signOut(){
        mAuth.signOut();
    }
}
