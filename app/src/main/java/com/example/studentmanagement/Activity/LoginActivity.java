package com.example.studentmanagement.Activity;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.Manifest;

import com.example.studentmanagement.Models.HistoryLogin;
import com.example.studentmanagement.Models.User;
import com.example.studentmanagement.R;
import com.example.studentmanagement.utils.DatabaseManagerUser;
import com.example.studentmanagement.utils.NetworkUtils;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Date;


public class LoginActivity extends AppCompatActivity {

    private EditText log_email;
    private  EditText login_password;
    private Button login_button;
    private DatabaseManagerUser dbManager;
    private FusedLocationProviderClient fusedLocationClient;


    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        dbManager = new DatabaseManagerUser();
        uiInit();
        Event();

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        // Request location permissions when the activity is created
        requestLocationPermissions();
    }

    private void requestLocationPermissions() {
        // Check if the app has the necessary permissions
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) ==
                        PackageManager.PERMISSION_GRANTED) {
            // Permissions are granted, proceed with your logic or show a location-based UI
        } else {
            // Request permissions
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE
            );
        }
    }

    // Override onRequestPermissionsResult to handle the result of the permission request
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
//            // Check if the permission request was granted
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                // Permission granted, proceed with your logic or show a location-based UI
//                getLocation();
//            } else {
//                // Permission denied, handle accordingly (e.g., show a message to the user)
//                Toast.makeText(this, "Location permission denied", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }



    private void getUserById(String userId, String password) {
        dbManager.getUserById(userId).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    // User document snapshot retrieved successfully
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        // Convert the document snapshot to a User object (assuming User class has a constructor that accepts a Map)
                        User user = document.toObject(User.class);


                        if (user != null) {
                            if(user.getPassword().equals(password)){

                                SharedPreferences preferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = preferences.edit();

                                Gson gson = new Gson();
                                String userJson = gson.toJson(user);
                                editor.putString("user", userJson);
                                editor.putString("role",user.getRole().toString());
                                editor.apply();

                                // Kiểm tra và yêu cầu quyền vị trí nếu chưa có
                                if (ContextCompat.checkSelfPermission(
                                        LoginActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                                        PackageManager.PERMISSION_GRANTED ||
                                        ContextCompat.checkSelfPermission(
                                                LoginActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) !=
                                                PackageManager.PERMISSION_GRANTED) {
                                    ActivityCompat.requestPermissions(
                                            LoginActivity.this,
                                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                                            LOCATION_PERMISSION_REQUEST_CODE);
                                } else {
                                    // Nếu đã có quyền, lấy vị trí
                                    getLocation(userId);
                                }

                                Toast.makeText(LoginActivity.this, "Login Success", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                            }else {
                                // User document does not exist
                                Toast.makeText(LoginActivity.this, "Wrong Password", Toast.LENGTH_SHORT).show();
                            }
                        }
                    } else {
                        // User document does not exist
                        Toast.makeText(LoginActivity.this, "User not found", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // An error occurred while fetching the user document
                    Toast.makeText(LoginActivity.this, "Error getting user: " + task.getException(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Hàm để lấy vị trí
    private void getLocation(String email) {
        // Kiểm tra lại quyền (trong trường hợp người dùng đã từ chối, nhưng sau đó cấp lại)
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED) {

            // Lấy vị trí người dùng
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null) {
                                double latitude = location.getLatitude();
                                double longitude = location.getLongitude();

                                Date currentDate = new Date();
                                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

                                String ip = NetworkUtils.getIPAddress();
                                String address = "Latitude: " + latitude + ", Longitude: " + longitude;
                                String formattedDate = dateFormat.format(currentDate);
                                String timeLogout = "";



                                HistoryLogin historyLogin = new HistoryLogin(formattedDate,formattedDate,timeLogout,address,ip);
                                addHistoryLoginForUser(email,historyLogin);

                                SharedPreferences preferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putString("id_historylogin", formattedDate);
                                editor.apply();

                                Toast.makeText(LoginActivity.this,address , Toast.LENGTH_SHORT).show();
                                Toast.makeText(LoginActivity.this, ip, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    private void addHistoryLoginForUser(String userId, HistoryLogin historyLogin) {
        Task<Void> addHistoryLoginTask = dbManager.addHistoryLogin(userId, historyLogin);
        addHistoryLoginTask.addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(LoginActivity.this, "Success, time login" + historyLogin.getStartLogin(), Toast.LENGTH_SHORT).show();
            } else {
                // Handle the error
                Toast.makeText(LoginActivity.this, "Error adding history login: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void Event(){
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = log_email.getText().toString().trim();
                String password = login_password.getText().toString().trim();

//                Toast.makeText(LoginActivity.this, "Login Success", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                startActivity(intent);

                if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
                    getUserById(email,password);

                } else {
                    Toast.makeText(LoginActivity.this,"Vui lòng điền đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });
    }

    private void uiInit(){
        log_email = findViewById(R.id.log_email);
        login_password  = findViewById(R.id.login_password);
        login_button = findViewById(R.id.login_button);
    }
}