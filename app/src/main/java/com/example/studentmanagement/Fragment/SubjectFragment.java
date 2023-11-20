package com.example.studentmanagement.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.studentmanagement.Adapter.Student.CertificateAdapter;
import com.example.studentmanagement.Adapter.SubjectAdapter;
import com.example.studentmanagement.Models.Certificate;
import com.example.studentmanagement.Models.Subject;
import com.example.studentmanagement.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SubjectFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SubjectFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView recyclerView;
    private SubjectAdapter subjectAdapter;
    private List<Subject> subjectList;

    private FloatingActionButton menu_add_subject;

    public SubjectFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SubjectFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SubjectFragment newInstance(String param1, String param2) {
        SubjectFragment fragment = new SubjectFragment();
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
        View view =  inflater.inflate(R.layout.fragment_subject, container, false);
        recyclerView = view.findViewById(R.id.subject_recycleview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        subjectList = new ArrayList<>();
        subjectAdapter = new SubjectAdapter(subjectList, getContext());

        recyclerView.setAdapter(subjectAdapter);

        screateSampleSubjects();


        menu_add_subject = view.findViewById(R.id.menu_add_subject);
        menu_add_subject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddEditCertificateDialog();
            }
        });

        return view;
    }

    public void screateSampleSubjects() {

        subjectList.add(new Subject("Math", "1", "https://img.websosanh.vn/v10/users/review/images/pxqhr3yhfxzx3/toan-6-tap-1.jpg?compress=85"));
        subjectList.add(new Subject("English", "2", "https://img.websosanh.vn/v10/users/review/images/pxqhr3yhfxzx3/toan-6-tap-1.jpg?compress=85"));
        subjectList.add(new Subject("History", "3", "https://img.websosanh.vn/v10/users/review/images/pxqhr3yhfxzx3/toan-6-tap-1.jpg?compress=85"));
        subjectList.add(new Subject("Science", "4", "https://img.websosanh.vn/v10/users/review/images/pxqhr3yhfxzx3/toan-6-tap-1.jpg?compress=85"));
        subjectList.add(new Subject("Computer Science", "5", "https://img.websosanh.vn/v10/users/review/images/pxqhr3yhfxzx3/toan-6-tap-1.jpg?compress=85"));

    }

    public void showAddEditCertificateDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_add_subject, null);
        builder.setView(dialogView);

        final EditText name = dialogView.findViewById(R.id.name_subject_add);
        final EditText image = dialogView.findViewById(R.id.image_subject_add);


        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

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
}