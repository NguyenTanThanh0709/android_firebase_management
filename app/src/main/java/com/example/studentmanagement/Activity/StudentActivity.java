package com.example.studentmanagement.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.studentmanagement.Fragment.student.CertificateFragment;
import com.example.studentmanagement.Fragment.student.FormStudentFragment;
import com.example.studentmanagement.Fragment.student.ScoreSubjectFragment;
import com.example.studentmanagement.R;
import com.example.studentmanagement.dto.CertificateDTO;
import com.example.studentmanagement.utils.File.ExportFileCertificate;
import com.example.studentmanagement.utils.File.ReadFileCertificate;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class StudentActivity extends AppCompatActivity {

    private static  final int FRAGMENT_FORM = 0;
    private static  final int FRAGMENT_CER = 1;
    private static  final int FRAGMENT_SCORESUBJECT = 2;

    private static  int FRAGMENT_CURRENT = FRAGMENT_FORM;

    private static final int PICK_EXCEL_FILE_REQUEST = 123;

    private String phone;
    private String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        Intent intent = getIntent();
        if (intent != null) {
            phone = intent.getStringExtra("phone");
            type = intent.getStringExtra("type");

            // Now you have the email and type values, you can use them as needed
            Log.d("EmployeeActivity", "Phone: " + phone);
            Log.d("EmployeeActivity", "Type: " + type);

            // Add your logic based on the email and type values
        }

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
            if(FRAGMENT_CURRENT == FRAGMENT_CER || FRAGMENT_CURRENT == FRAGMENT_SCORESUBJECT){
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

            if(FRAGMENT_CURRENT != FRAGMENT_SCORESUBJECT){
                rePlaceFragment(new ScoreSubjectFragment());
                FRAGMENT_CURRENT = FRAGMENT_SCORESUBJECT;
                return true;
            }


            return true;
        }
        else if (item.getItemId() == R.id.action_view_export_certificate) {

            ExportFileCertificate.exportToExcel(null,this);
            return true;
        }
        else if (item.getItemId() == R.id.action_view_import_certificate) {



            openFilePicker();


            return true;
        }else {
            return super.onOptionsItemSelected(item);
        }
    }

    private  void rePlaceFragment(Fragment fragment){
        Bundle bundle = new Bundle();
        bundle.putString("phone", phone); // Replace "value" with the actual email value
        bundle.putString("type", type); // Replace "value" with the actual type value
        fragment.setArguments(bundle);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_student,fragment);
        transaction.commit();
    }


    private void openFilePicker() {
        // Check if permission is granted
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {
            // Permission is already granted, open the file picker
            performFilePick();
        } else {
            // Permission is not granted, request it
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    PICK_EXCEL_FILE_REQUEST);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PICK_EXCEL_FILE_REQUEST) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, proceed with file picker
                openFilePicker();
            } else {
                // Permission denied, show a message or handle accordingly
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_EXCEL_FILE_REQUEST && resultCode == Activity.RESULT_OK) {
            Uri selectedFileUri = data.getData();

            if (selectedFileUri != null) {
                // Now you can use the selectedFileUri to read the Excel file
                try {
                    InputStream inputStream = getInputStreamFromUri(selectedFileUri);
                    List<CertificateDTO> list = ReadFileCertificate.readExcelFile(this, inputStream);
                    for (CertificateDTO certificateDTO: list){
                        Log.d("CertificateDTO", certificateDTO.toString());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    // Handle the error, for example, show a Toast
                    Toast.makeText(this, "Error reading file", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
    private InputStream getInputStreamFromUri(Uri uri) throws IOException {
        // Check if the Uri scheme is content
        if ("content".equals(uri.getScheme())) {
            return getContentResolver().openInputStream(uri);
        } else {
            // For other schemes, handle accordingly
            // You might need to implement additional logic based on your use case
            // For example, if the scheme is "file", you can use FileInputStream
            return new FileInputStream(uri.getPath());
        }
    }

    private void performFilePick() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"); // MIME type for Excel files

        startActivityForResult(intent, PICK_EXCEL_FILE_REQUEST);
    }
}