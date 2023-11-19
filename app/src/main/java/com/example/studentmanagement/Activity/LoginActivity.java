package com.example.studentmanagement.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.studentmanagement.R;

public class LoginActivity extends AppCompatActivity {

    private EditText login_phonenumber;
    private  EditText login_password;
    private Button login_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        uiInit();
        Event();
    }

    private void Event(){
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(LoginActivity.this, "Login", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void uiInit(){
        login_phonenumber = findViewById(R.id.login_phonenumber);
        login_password  = findViewById(R.id.login_password);
        login_button = findViewById(R.id.login_button);
    }
}