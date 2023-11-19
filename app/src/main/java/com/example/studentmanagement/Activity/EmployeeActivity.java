package com.example.studentmanagement.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;

import com.example.studentmanagement.Fragment.EmployeeFragment;
import com.example.studentmanagement.Fragment.FormEmployeeFragment;
import com.example.studentmanagement.Fragment.HistoryLoginFragment;
import com.example.studentmanagement.Fragment.MyProfileFragment;
import com.example.studentmanagement.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;

public class EmployeeActivity extends AppCompatActivity {

    private static  final int FRAGMENT_FORM = 1;
    private static  final int FRAGMENT_HIS = 0;

    private static  int FRAGMENT_CURRENT = FRAGMENT_FORM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);
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