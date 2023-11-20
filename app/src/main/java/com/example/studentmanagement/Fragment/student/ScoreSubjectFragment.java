package com.example.studentmanagement.Fragment.student;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.studentmanagement.Adapter.Student.CertificateAdapter;
import com.example.studentmanagement.Adapter.Student.ScoreSubjectAdapter;
import com.example.studentmanagement.Models.Certificate;
import com.example.studentmanagement.Models.ScoreSubject;
import com.example.studentmanagement.Models.Subject;
import com.example.studentmanagement.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ScoreSubjectFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ScoreSubjectFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView recyclerView;
    private ScoreSubjectAdapter scoreSubjectAdapter;
    private List<ScoreSubject> scoreSubjects;

    private FloatingActionButton menu_add_scoresubject;

    public ScoreSubjectFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ScoreSubjectFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ScoreSubjectFragment newInstance(String param1, String param2) {
        ScoreSubjectFragment fragment = new ScoreSubjectFragment();
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
        View view =  inflater.inflate(R.layout.fragment_score_subject, container, false);
        recyclerView = view.findViewById(R.id.scoresubject_recycleview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        scoreSubjects = new ArrayList<>();
        scoreSubjectAdapter = new ScoreSubjectAdapter(scoreSubjects, getContext());

        recyclerView.setAdapter(scoreSubjectAdapter);

        getscore();


        menu_add_scoresubject = view.findViewById(R.id.menu_add_scoresubject);
        menu_add_scoresubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showAddEditCertificateDialog();
            }
        });
        return view;
    }

    private void getscore() {
        Subject subject1 = new Subject("Math", "1", "math_image_url");
        Subject subject2 = new Subject("English", "2", "english_image_url");
        ScoreSubject scoreSubject1 = new ScoreSubject("1", 90.5, subject1, "2022-01-01");
        ScoreSubject scoreSubject2 = new ScoreSubject("2", 85.0, subject2, "2022-02-01");
        scoreSubjects.add(scoreSubject1);
        scoreSubjects.add(scoreSubject2);
    }

    public static List<Subject> getSampleSubjects() {
        List<Subject> subjects = new ArrayList<>();

        Subject subject1 = new Subject("Math", "1", "math_image_url");
        Subject subject2 = new Subject("English", "2", "english_image_url");

        subjects.add(subject1);
        subjects.add(subject2);

        return subjects;
    }

    public void showAddEditCertificateDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_add_edit_score_add, null);
        builder.setView(dialogView);
        List<Subject> subjects = getSampleSubjects();
        ArrayAdapter<Subject> subjectAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, subjects);
        subjectAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        final Spinner subjectSpinner = dialogView.findViewById(R.id.subject_spinner);

// Set the adapter to the Spinner
        subjectSpinner.setAdapter(subjectAdapter);

// Handle the selection event
        subjectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Get the selected subject
                Subject selectedSubject = (Subject) parentView.getItemAtPosition(position);

                // Do something with the selected subject
                Toast.makeText(requireContext(), "Selected Subject: " + selectedSubject.getName(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing here
            }
        });

        final EditText day = dialogView.findViewById(R.id.daylearn_score_subject_add);


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