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
import com.example.studentmanagement.Models.Certificate;
import com.example.studentmanagement.R;
import com.example.studentmanagement.dto.CertificateDTO;
import com.example.studentmanagement.utils.DatabaseManagerStudent;
import com.example.studentmanagement.utils.File.ExportFileCertificate;
import com.example.studentmanagement.utils.File.ReadFileCertificate;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class StudentActivity extends AppCompatActivity {

    private static  final int FRAGMENT_FORM = 0;
    private static  final int FRAGMENT_CER = 1;
    private static  final int FRAGMENT_SCORESUBJECT = 2;

    private static  int FRAGMENT_CURRENT = FRAGMENT_FORM;

    private static final int PICK_EXCEL_FILE_REQUEST = 123;
    private DatabaseManagerStudent databaseManagerStudent;


    private String phone;
    private String type;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
        databaseManagerStudent = new DatabaseManagerStudent();
        Intent intent = getIntent();
        if (intent != null) {
            phone = intent.getStringExtra("phone");
            name = intent.getStringExtra("name");
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


            getAllCertificatteOfStudent(phone);
            return true;
        }
        else if (item.getItemId() == R.id.action_view_import_certificate) {



            openFilePicker();


            return true;
        }else {
            return super.onOptionsItemSelected(item);
        }
    }

    private void getAllCertificatteOfStudent(String phone) {
        Task<QuerySnapshot> getAllCertificatesTask = databaseManagerStudent.getAllCertificates(phone);

// Handle the task result
        getAllCertificatesTask.addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                // The task was successful, and you can now process the QuerySnapshot
                QuerySnapshot querySnapshot = task.getResult();
                List<Certificate> certificates = new ArrayList<>();

                // Iterate through the documents in the QuerySnapshot
                for (QueryDocumentSnapshot documentSnapshot : querySnapshot) {
                    // Access certificate data from the documentSnapshot
                    Certificate certificate = documentSnapshot.toObject(Certificate.class);
                    certificates.add(certificate);
                }
                ExportFileCertificate.exportToExcel(name,phone,certificates,this);

            } else {
                // The task failed, handle the exception
                Exception exception = task.getException();
                if (exception != null) {
                    exception.printStackTrace();
                }
            }
        });
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
                    List<CertificateDTO> list = ReadFileCertificate.readExcelFile(this, inputStream, phone);
                    saveFireBase(list);
                } catch (IOException e) {
                    e.printStackTrace();
                    // Handle the error, for example, show a Toast
                    Toast.makeText(this, "Error reading file", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void saveFireBase(List<CertificateDTO> list) {
        List<Certificate> certificates = new ArrayList<>();
        for(CertificateDTO certificateDTO: list){
            Certificate certificate = new Certificate(certificateDTO);
            certificates.add(certificate);
        }
        save(certificates);
        Log.d("TAG", "Certificates added successfully");

    }

    private void save(List<Certificate> certificates) {
        Task<Void> addCertificatesTask = databaseManagerStudent.addCertificates(phone, certificates);

// Add a continuation to handle the result of the task
        addCertificatesTask.addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(StudentActivity.this, "Certificates added successfully", Toast.LENGTH_SHORT);
                Log.d("TAG", "Certificates added successfully");
            } else {
                // Handle the error if the task was not successful
                Exception exception = task.getException();
                // Log or display an error message
                Log.e("TAG", "Error adding certificates: ", exception);
                Toast.makeText(StudentActivity.this, "Certificates added Error", Toast.LENGTH_SHORT);
            }
        });
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