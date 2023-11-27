package com.example.studentmanagement.Fragment.student;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.studentmanagement.Activity.EmployeeActivity;
import com.example.studentmanagement.Adapter.Student.CertificateAdapter;
import com.example.studentmanagement.Adapter.Student.StudentAdapter;
import com.example.studentmanagement.Models.Certificate;
import com.example.studentmanagement.Models.Student;
import com.example.studentmanagement.R;
import com.example.studentmanagement.Repository.CertificateClickListener;
import com.example.studentmanagement.utils.DatabaseManagerStudent;
import com.example.studentmanagement.utils.DatabaseManagerSubject;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.apache.poi.ss.formula.functions.T;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CertificateFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CertificateFragment extends Fragment{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView recyclerView;
    private CertificateAdapter certificateAdapter;
    private List<Certificate> certificateList;

    private FloatingActionButton menu_add_certificate;

    private DatabaseManagerStudent databaseManagerStudent;
    private String phone = "";

    public CertificateFragment() {
        // Required empty public constructor
    }


    public static CertificateFragment newInstance(String param1, String param2) {
        CertificateFragment fragment = new CertificateFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_certificate, container, false);

        Bundle args = getArguments();

        if (args != null) {
            phone = args.getString("phone", "");
        }
        databaseManagerStudent = new DatabaseManagerStudent();

        recyclerView = view.findViewById(R.id.certificate_recycleview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        certificateList = new ArrayList<>();
        certificateAdapter = new CertificateAdapter(certificateList, getContext(), phone);

        recyclerView.setAdapter(certificateAdapter);

        getCertificate();


        menu_add_certificate = view.findViewById(R.id.menu_add_certificate);
        menu_add_certificate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddEditCertificateDialog(null);
            }
        });
        return view;
    }

    public  void getCertificate(){

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

                certificateList.clear();
                certificateList.addAll(certificates);
                certificateAdapter.notifyDataSetChanged();
            } else {
                // The task failed, handle the exception
                Exception exception = task.getException();
                if (exception != null) {
                    exception.printStackTrace();
                }
            }
        });

    }

    public void showAddEditCertificateDialog(Certificate certificate) {
        if(certificate != null){
            Toast.makeText(getContext(),certificate.getId(),Toast.LENGTH_SHORT).show();
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_add_edit_certificate, null);
        builder.setView(dialogView);
        builder.setTitle("Thêm Chứng chỉ cho sinh viên");

        final EditText editTextCertificateName = dialogView.findViewById(R.id.editTextCertificateName);
        final EditText editTextStartCertificate = dialogView.findViewById(R.id.editTextStartCertificate);
        final EditText editTextEndCertificate = dialogView.findViewById(R.id.editTextEndCertificate);
        final EditText editTextOverallScore = dialogView.findViewById(R.id.editTextOverallScore);
        final EditText editTextDescribe = dialogView.findViewById(R.id.editTextDescribe);
        final EditText editTextLink = dialogView.findViewById(R.id.editTextLink);

        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


                if (isValidInput(editTextCertificateName, editTextStartCertificate, editTextEndCertificate,
                        editTextOverallScore, editTextDescribe, editTextLink)) {

                    Certificate certificate1 = new Certificate(
                            generateCustomPushId(),editTextCertificateName.getText().toString(),
                            editTextStartCertificate.getText().toString(),
                            editTextEndCertificate.getText().toString(),
                            Double.parseDouble(editTextOverallScore.getText().toString()),
                            editTextDescribe.getText().toString(),
                            editTextLink.getText().toString()
                    );
                    saveCertificate(certificate1);
                } else {
                    // Show a message or handle the case where input is not valid
                    Toast.makeText(getContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
                }
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void saveCertificate(Certificate certificate1) {
        Task<Void> addCertificateTask = databaseManagerStudent.addCertificate(phone, certificate1);

// Handle the task result as needed
        addCertificateTask.addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                // The certificate was added successfully
                certificateList.add(certificate1);
                certificateAdapter.notifyDataSetChanged();
                Toast.makeText(getContext(),"Certificate added successfully",Toast.LENGTH_SHORT).show();
                System.out.println("Certificate added successfully");
            } else {
                // Handle the exception
                Exception exception = task.getException();
                if (exception != null) {
                    Toast.makeText(getContext(),"Certificate added Fail",Toast.LENGTH_SHORT).show();

                    exception.printStackTrace();
                }
            }
        });

    }

    private String generateCustomPushId() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy-HH-mm-ss");
        LocalDateTime now = LocalDateTime.now();
        return formatter.format(now).toString().replace("-","");
    }



    private boolean isValidInput(EditText... editTexts) {
        for (EditText editText : editTexts) {
            if (editText.getText().toString().trim().isEmpty()) {
                return false; // Return false if any EditText is empty
            }
        }
        return true; // All EditTexts are non-empty
    }


}