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
import com.google.android.material.floatingactionbutton.FloatingActionButton;

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

    public CertificateFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CertificateFragment.
     */
    // TODO: Rename and change types and number of parameters
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
        recyclerView = view.findViewById(R.id.certificate_recycleview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        certificateList = new ArrayList<>();
        certificateAdapter = new CertificateAdapter(certificateList, getContext());

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
        Certificate certificate1 = new Certificate("Certificate 1", "2022-01-01", "2022-12-31", 90.5, "Good performance", "https://docs.google.com/document/d/1b0wfxctvcoBtoG1xa0frcf4DVhSiCnyW7TLkFp6b5LM/edit");
        Certificate certificate2 = new Certificate("Certificate 2", "2022-02-01", "2022-11-30", 88.0, "Excellent performance", "https://docs.google.com/document/d/1b0wfxctvcoBtoG1xa0frcf4DVhSiCnyW7TLkFp6b5LM/edit");
        Certificate certificate3 = new Certificate("Certificate 3", "2022-03-01", "2022-10-31", 95.5, "Outstanding performance", "https://docs.google.com/document/d/1b0wfxctvcoBtoG1xa0frcf4DVhSiCnyW7TLkFp6b5LM/edit");

        certificateList.add(certificate1);
        certificateList.add(certificate2);
        certificateList.add(certificate3);
    }

    public void showAddEditCertificateDialog(Certificate certificate) {
        if(certificate != null){
            Toast.makeText(getContext(),certificate.getId(),Toast.LENGTH_SHORT).show();
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_add_edit_certificate, null);
        builder.setView(dialogView);

        final EditText editTextCertificateName = dialogView.findViewById(R.id.editTextCertificateName);
        final EditText editTextStartCertificate = dialogView.findViewById(R.id.editTextStartCertificate);
        final EditText editTextEndCertificate = dialogView.findViewById(R.id.editTextEndCertificate);
        final EditText editTextOverallScore = dialogView.findViewById(R.id.editTextOverallScore);
        final EditText editTextDescribe = dialogView.findViewById(R.id.editTextDescribe);
        final EditText editTextLink = dialogView.findViewById(R.id.editTextLink);

        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String startCertificate = editTextStartCertificate.getText().toString();

                Toast.makeText(getContext(),startCertificate,Toast.LENGTH_SHORT).show();
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

    private void saveCertificate(EditText editTextCertificateName, EditText editTextStartCertificate,
                                 EditText editTextEndCertificate, EditText editTextOverallScore,
                                 EditText editTextDescribe, EditText editTextLink) {
        String name = editTextCertificateName.getText().toString();
        String startCertificate = editTextStartCertificate.getText().toString();
        String endCertificate = editTextEndCertificate.getText().toString();
        double overallScore = Double.parseDouble(editTextOverallScore.getText().toString());
        String describe = editTextDescribe.getText().toString();
        String link = editTextLink.getText().toString();

        Certificate newCertificate = new Certificate(name, startCertificate, endCertificate, overallScore, describe, link);

        // Add the new certificate to the list
        certificateList.add(newCertificate);

        // Notify the adapter that the dataset has changed
        certificateAdapter.notifyDataSetChanged();
    }


}