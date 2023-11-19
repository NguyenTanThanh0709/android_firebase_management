package com.example.studentmanagement;

import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


import com.example.studentmanagement.Fragment.ClassFragment;
import com.example.studentmanagement.Fragment.EmployeeFragment;
import com.example.studentmanagement.Fragment.HomeFragment;
import com.example.studentmanagement.Fragment.MyProfileFragment;
import com.example.studentmanagement.Fragment.StudentFragment;
import com.example.studentmanagement.Fragment.SubjectFragment;
import com.example.studentmanagement.Models.Certificate;
import com.example.studentmanagement.Models.Class_;
import com.example.studentmanagement.Models.HistoryLogin;
import com.example.studentmanagement.Models.Role;
import com.example.studentmanagement.Models.ScoreSubject;
import com.example.studentmanagement.Models.Student;
import com.example.studentmanagement.Models.Subject;
import com.example.studentmanagement.Models.User;
import com.example.studentmanagement.utils.DatabaseManagerClass;
import com.example.studentmanagement.utils.DatabaseManagerStudent;
import com.example.studentmanagement.utils.DatabaseManagerSubject;
import com.example.studentmanagement.utils.DatabaseManagerUser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.studentmanagement.databinding.ActivityMainBinding;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{



    private DrawerLayout mDrawerLayout;
    private static  final int FRAGMENT_HOME = 0;
    private static  final int FRAGMENT_CLASS = 3;
    private static  final int FRAGMENT_SUBJECT = 4;
    private static  final int FRAGMENT_STUDENT = 2;
    private static  final int FRAGMENT_EMPLOYEE = 1;
    private static  final int FRAGMENT_MYPROFILE = 5;

    private static  int FRAGMENT_CURRENT = FRAGMENT_HOME;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,toolbar,
                R.string.open_nav, R.string.close_nav);
        mDrawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);
        rePlaceFragment(new HomeFragment());
        navigationView.getMenu().findItem(R.id.nav_home).setChecked(true);

        Student student1 = new Student("John Doe", "21", "john.doe@example.com", "2000-01-01", true, "avatar1.jpg", "2020-01-01", "2023-01-01");
        Student student2 = new Student("John Doe", "2122", "john.doe@example.com", "2000-01-01", true, "avatar1.jpg", "2020-01-01", "2023-01-01");
        Student student3 = new Student("John Doe", "1234533689", "john.doe@example.com", "2000-01-01", true, "avatar1.jpg", "2020-01-01", "2023-01-01");
        List<Student> list = new ArrayList<>();
        list.add(student3);
        list.add(student2);
        list.add(student1);
        DatabaseManagerStudent databaseManagerStudent = new DatabaseManagerStudent();
        databaseManagerStudent.addListStudentsAndAssociateWithClass("18112023224431", list)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Adding student to class was successful
                        // You can perform any additional actions here
                    } else {
                        // Handle the error
                        Exception exception = task.getException();
                        if (exception != null) {
                            // Log or display the exception message
                            exception.printStackTrace();
                        }
                    }
                });



    }


    private String generateCustomPushId() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy-HH-mm-ss");
        LocalDateTime now = LocalDateTime.now();
        return formatter.format(now).toString();
    }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.nav_home){

            if(FRAGMENT_CURRENT != FRAGMENT_HOME){
                rePlaceFragment(new HomeFragment());
                FRAGMENT_CURRENT = FRAGMENT_HOME;
            }

        }else  if(id == R.id.nav_employee){

            if(FRAGMENT_CURRENT != FRAGMENT_EMPLOYEE){
                rePlaceFragment(new EmployeeFragment());
                FRAGMENT_CURRENT = FRAGMENT_EMPLOYEE;
            }

        }else  if(id == R.id.nav_student){

            if(FRAGMENT_CURRENT != FRAGMENT_STUDENT){
                rePlaceFragment(new StudentFragment());
                FRAGMENT_CURRENT = FRAGMENT_STUDENT;
            }

        }else  if(id == R.id.nav_class){

            if(FRAGMENT_CURRENT != FRAGMENT_CLASS){
                rePlaceFragment(new ClassFragment());
                FRAGMENT_CURRENT = FRAGMENT_CLASS;
            }

        }else  if(id == R.id.nav_subject){

            if(FRAGMENT_CURRENT != FRAGMENT_SUBJECT){
                rePlaceFragment(new SubjectFragment());
                FRAGMENT_CURRENT = FRAGMENT_SUBJECT;
            }

        }else  if(id == R.id.nav_myprofile){

            if(FRAGMENT_CURRENT != FRAGMENT_MYPROFILE){
                rePlaceFragment(new MyProfileFragment());
                FRAGMENT_CURRENT = FRAGMENT_MYPROFILE;
            }

        }else  if(id == R.id.nav_logout){

           Toast.makeText(MainActivity.this, "Log Out", Toast.LENGTH_SHORT).show();

        }
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if(mDrawerLayout.isDrawerOpen(GravityCompat.START)){
            mDrawerLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }

    private  void rePlaceFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_,fragment);
        transaction.commit();
    }
}