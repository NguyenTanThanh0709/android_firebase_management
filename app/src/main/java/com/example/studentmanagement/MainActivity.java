package com.example.studentmanagement;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;

import com.example.studentmanagement.FireBase.FirebaseHelperCLASS;
import com.example.studentmanagement.FireBase.FirebaseHelperStudent;
import com.example.studentmanagement.FireBase.FirebaseHelperSubject;
import com.example.studentmanagement.FireBase.FirebaseHelperUSER;
import com.example.studentmanagement.Models.Class_;
import com.example.studentmanagement.Models.Role;
import com.example.studentmanagement.Models.Student;
import com.example.studentmanagement.Models.Subject;
import com.example.studentmanagement.Models.User;
import com.example.studentmanagement.Repo.Class.OnClassAddedListener;
import com.example.studentmanagement.Repo.Class.OnClassReceivedListener;
import com.example.studentmanagement.Repo.Class.OnClassesAddedListener;
import com.example.studentmanagement.Repo.Class.OnClassesReceivedListener;
import com.example.studentmanagement.Repo.Class.OnStudentAddedListener;
import com.example.studentmanagement.Repo.Class.OnStudentsAddedListener;
import com.example.studentmanagement.Repo.Student.OnScoreSubjectAddedListener;
import com.example.studentmanagement.Repo.Subject.OnSubjectAddedListener;
import com.example.studentmanagement.Repo.Subject.OnSubjectRetrievedListener;
import com.example.studentmanagement.Repo.Subject.OnSubjectsAddedListener;
import com.example.studentmanagement.Repo.Subject.OnSubjectsRetrievedListener;
import com.example.studentmanagement.Repo.User.OnUserAddedListener;
import com.example.studentmanagement.Repo.User.OnUserDataReceivedListener;
import com.example.studentmanagement.Repo.User.OnUsersAddedListener;
import com.example.studentmanagement.Repo.User.OnUsersDataReceivedListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.studentmanagement.databinding.ActivityMainBinding;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    // Write a message to the database
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private List<User> listUsers;
    private FirebaseHelperUSER firebaseHelper;
    private FirebaseHelperCLASS firebaseHelperCLASS;
    private FirebaseHelperSubject firebaseHelperSubject;
    private FirebaseHelperStudent firebaseHelperStudent;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        database = FirebaseDatabase.getInstance();
        firebaseHelper = new FirebaseHelperUSER();
        firebaseHelperCLASS = new FirebaseHelperCLASS();
        firebaseHelperSubject = new FirebaseHelperSubject();
        firebaseHelperStudent = new FirebaseHelperStudent();

        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Init();


        insert();
    }

    private void Init(){
        setSupportActionBar(binding.appBarMain.toolbar);
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;

        BottomNavigationView navView = findViewById(R.id.nav_bottom);


        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow, R.id.nav_logout, R.id.nav_myprofile
                , R.id.tab1,  R.id.tab2, R.id.tab3, R.id.tab4
        )
                .setOpenableLayout(drawer)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        NavigationUI.setupWithNavController(navView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private  void insert(){
        firebaseHelperStudent.addScoreSubjectToStudent("123456789", "-NjSoz-pGU1s_Yez5zY8", 8.0, "11-11-2023", new OnScoreSubjectAddedListener() {
            @Override
            public void onScoreSubjectAdded(String scoreSubjectId) {
                Toast.makeText(MainActivity.this,"Ok", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(String errorMessage) {

            }
        });

    }



}