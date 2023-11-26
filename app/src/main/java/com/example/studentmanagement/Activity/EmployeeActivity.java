package com.example.studentmanagement.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.studentmanagement.Fragment.user.FormEmployeeFragment;
import com.example.studentmanagement.Fragment.user.HistoryLoginFragment;
import com.example.studentmanagement.R;

public class EmployeeActivity extends AppCompatActivity {

    private static  final int FRAGMENT_FORM = 1;
    private static  final int FRAGMENT_HIS = 0;

    private static  int FRAGMENT_CURRENT = FRAGMENT_FORM;

    private String email;
    private String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);

        Intent intent = getIntent();
        if (intent != null) {
             email = intent.getStringExtra("email");
             type = intent.getStringExtra("type");

            // Now you have the email and type values, you can use them as needed
            Log.d("EmployeeActivity", "Email: " + email);
            Log.d("EmployeeActivity", "Type: " + type);

            // Add your logic based on the email and type values
        }


        rePlaceFragment(new FormEmployeeFragment());

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_employee, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_back) {
            // Handle "Quay lại" action


            if(FRAGMENT_CURRENT == FRAGMENT_HIS){
                rePlaceFragment(new FormEmployeeFragment());
                FRAGMENT_CURRENT = FRAGMENT_FORM;
                return true;
            }else {
                finish();
                return true;
            }


        } else if (item.getItemId() == R.id.action_view_login_history) {


            if(FRAGMENT_CURRENT != FRAGMENT_HIS){
                rePlaceFragment(new HistoryLoginFragment());
                FRAGMENT_CURRENT = FRAGMENT_HIS;
            }

            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    private  void rePlaceFragment(Fragment fragment){
        Bundle bundle = new Bundle();
        bundle.putString("email", email); // Replace "value" with the actual email value
        bundle.putString("type", type); // Replace "value" with the actual type value
        fragment.setArguments(bundle);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_employee,fragment);
        transaction.commit();
    }

    public void switchFragment() {
        if (FRAGMENT_CURRENT == FRAGMENT_FORM) {
            rePlaceFragment(new HistoryLoginFragment());
            FRAGMENT_CURRENT = FRAGMENT_HIS;
        } else {
            rePlaceFragment(new FormEmployeeFragment());
            FRAGMENT_CURRENT = FRAGMENT_FORM;
        }
    }
}