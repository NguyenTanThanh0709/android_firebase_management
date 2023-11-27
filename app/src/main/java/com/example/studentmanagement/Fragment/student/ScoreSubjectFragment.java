package com.example.studentmanagement.Fragment.student;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
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
import com.example.studentmanagement.Models.Class_;
import com.example.studentmanagement.Models.ScoreSubject;
import com.example.studentmanagement.Models.Subject;
import com.example.studentmanagement.R;
import com.example.studentmanagement.utils.DatabaseManagerStudent;
import com.example.studentmanagement.utils.DatabaseManagerSubject;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    private  Spinner subjectSpinner;
    private RecyclerView recyclerView;
    private ScoreSubjectAdapter scoreSubjectAdapter;
    private List<ScoreSubject> scoreSubjects;

    private List<Subject> subjectList;

    private FloatingActionButton menu_add_scoresubject;

    private DatabaseManagerSubject databaseManagerSubject;
    private DatabaseManagerStudent databaseManagerStudent;
    private String phone = "";

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

        Bundle args = getArguments();

        if (args != null) {
            phone = args.getString("phone", "");
        }


        databaseManagerSubject = new DatabaseManagerSubject();
        databaseManagerStudent = new DatabaseManagerStudent();
        subjectList = new ArrayList<>();
        recyclerView = view.findViewById(R.id.scoresubject_recycleview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        scoreSubjects = new ArrayList<>();
        scoreSubjectAdapter = new ScoreSubjectAdapter(scoreSubjects, getContext(), phone);

        recyclerView.setAdapter(scoreSubjectAdapter);



        getAllScoreSubjectOffStudent();

        menu_add_scoresubject = view.findViewById(R.id.menu_add_scoresubject);
        menu_add_scoresubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showAddEditCertificateDialog();
            }
        });
        return view;
    }



    private  void getALlSubject(){
        // Call the getAllSubjects method
        databaseManagerSubject.getAllSubjects()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<Subject> list = new ArrayList<>();
                    // Handle the success case
                    for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots.getDocuments()) {
                        // Access subject data from the documentSnapshot
                        Subject subject = documentSnapshot.toObject(Subject.class);
                        if (subject != null) {
                            list.add(subject);
                        }
                    }
                    subjectList.clear();
                    subjectList.addAll(list);
                    setupSpinner();
                })

                .addOnFailureListener(e -> {
                    // Handle the failure case
                    Log.e("GetAllSubjects", "Error getting subjects", e);
                });
    }

    private void setupSpinner() {
        List<String> classNames = new ArrayList<>();
        for (Subject class_ : subjectList) {
            classNames.add(class_.getName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, classNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        subjectSpinner.setAdapter(adapter);
    }

    public void showAddEditCertificateDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_add_edit_score_add, null);
        builder.setTitle("Thêm danh sách điểm môn học của sinh viên");
        subjectSpinner = dialogView.findViewById(R.id.subject_spinner);
        builder.setView(dialogView);
        getALlSubject();
        final EditText day = dialogView.findViewById(R.id.daylearn_score_subject_add);


        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                String selectedSubjectName = subjectSpinner.getSelectedItem().toString();

                // Find the Subject object corresponding to the selected subject name
                Subject selectedSubject = findSubjectByName(selectedSubjectName);

                // Get the day from the EditText
                String dayLearn = day.getText().toString();

                // Now you have the selected subject and day, you can use them as needed
                if (selectedSubject != null) {

                    ScoreSubject subject = new ScoreSubject(generateCustomPushId(),0.0,selectedSubject,dayLearn);
                    UploadFireBaseScoreSubject(phone,subject);
                } else {
                    Toast.makeText(getContext(),"Error Find Subject", Toast.LENGTH_SHORT).show();
                    Log.e("SelectedSubject", "Selected subject is null");
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

    private void UploadFireBaseScoreSubject(String phone, ScoreSubject subject) {

        databaseManagerStudent.addSubjectScore(phone, subject)
                .addOnSuccessListener(aVoid -> {
                    // Handle success
                    scoreSubjects.add(subject);
                    scoreSubjectAdapter.notifyDataSetChanged();
                    Toast.makeText(getContext(),"AddSubjectScore, ScoreSubject added successfully!", Toast.LENGTH_SHORT).show();
                    Log.d("AddSubjectScore", "ScoreSubject added successfully!");
                })
                .addOnFailureListener(e -> {
                    // Handle failure
                    Toast.makeText(getContext(),"AddSubjectScore, Error adding ScoreSubject!", Toast.LENGTH_SHORT).show();

                    Log.e("AddSubjectScore", "Error adding ScoreSubject", e);
                });

    }

    private String generateCustomPushId() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy-HH-mm-ss");
        LocalDateTime now = LocalDateTime.now();
        return formatter.format(now).toString().replace("-","");
    }

    private void getAllScoreSubjectOffStudent(){
        Task<QuerySnapshot> getAllSubjectScoresTask = databaseManagerStudent.getAllSubjectScores(phone);

        getAllSubjectScoresTask.addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                // The task was successful, and you can now process the QuerySnapshot
                QuerySnapshot querySnapshot = task.getResult();
                List<ScoreSubject> subjects = new ArrayList<>();
                // Iterate through the documents in the QuerySnapshot
                for (QueryDocumentSnapshot document : querySnapshot) {
                    ScoreSubject subject = ScoreSubject.convertDocumentToScoreSubject(document);
                    subjects.add(subject);
                }
                scoreSubjects.clear();
                scoreSubjects.addAll(subjects);
                scoreSubjectAdapter.notifyDataSetChanged();
            } else {
                // The task failed, handle the exception
                Exception exception = task.getException();
                if (exception != null) {
                    exception.printStackTrace();
                }
            }
        });
    }

    private Subject findSubjectByName(String subjectName) {
        for (Subject subject : subjectList) {
            if (subject.getName().equals(subjectName)) {
                return subject;
            }
        }
        return null;
    }


}