package com.example.studentmanagement.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.studentmanagement.Fragment.student.CertificateFragment;
import com.example.studentmanagement.Fragment.student.FormStudentFragment;
import com.example.studentmanagement.Fragment.user.FormEmployeeFragment;
import com.example.studentmanagement.Fragment.user.HistoryLoginFragment;
import com.example.studentmanagement.R;

public class StudentActivity extends AppCompatActivity {

    private static  final int FRAGMENT_FORM = 0;
    private static  final int FRAGMENT_CER = 1;
    private static  final int FRAGMENT_SCORESUBJECT = 2;

    private static  int FRAGMENT_CURRENT = FRAGMENT_FORM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
        rePlaceFragment(new FormStudentFragment());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_student, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_backstudent) {
            // Handle "Quay lại" action
            if(FRAGMENT_CURRENT == FRAGMENT_CER){
                rePlaceFragment(new FormStudentFragment());
                FRAGMENT_CURRENT = FRAGMENT_FORM;
                return true;
            }else {
                finish();
                return true;
            }

        } else if (item.getItemId() == R.id.action_view_certificate) {

                if(FRAGMENT_CURRENT != FRAGMENT_CER){
                    rePlaceFragment(new CertificateFragment());
                    FRAGMENT_CURRENT = FRAGMENT_CER;
                    return true;
                }


            return true;
        }else if (item.getItemId() == R.id.action_view_subjectscore) {




            return true;
        }
        else if (item.getItemId() == R.id.action_view_export_certificate) {




            return true;
        }
        else if (item.getItemId() == R.id.action_view_import_certificate) {




            return true;
        }else {
            return super.onOptionsItemSelected(item);
        }
    }

    private  void rePlaceFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_student,fragment);
        transaction.commit();
    }
}