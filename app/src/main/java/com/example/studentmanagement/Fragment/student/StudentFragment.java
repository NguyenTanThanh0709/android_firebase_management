package com.example.studentmanagement.Fragment.student;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.studentmanagement.Activity.StudentActivity;
import com.example.studentmanagement.Adapter.Student.StudentAdapter;
import android.Manifest;
import android.widget.Toast;

import com.example.studentmanagement.Models.Certificate;
import com.example.studentmanagement.Models.Class_;
import com.example.studentmanagement.Models.ScoreSubject;
import com.example.studentmanagement.Models.Student;
import com.example.studentmanagement.Models.Subject;
import com.example.studentmanagement.R;
import com.example.studentmanagement.dto.StudentDTO;
import com.example.studentmanagement.utils.File.ExportFileStudent;
import com.example.studentmanagement.utils.File.ReadFIleStudent;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.formula.functions.T;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StudentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StudentFragment extends Fragment  implements CustomSortDialogFragment.SortDialogListener, SearchDialogFragment.SearchDialogListener{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView recyclerView;
    private StudentAdapter studentAdapter;
    private List<Student> studentList;
    private List<Student> studentList_;

    private Button sort_student;
    private Button find_student;
    private  Button add_student;
    private  Button export_list_student;
    private  Button import_list_student;
    private static final int PICK_EXCEL_FILE_REQUEST = 123;
    public StudentFragment() {
        // Required empty public constructor
    }



    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StudentFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StudentFragment newInstance(String param1, String param2) {
        StudentFragment fragment = new StudentFragment();
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
        View view = inflater.inflate(R.layout.fragment_student, container, false);
        recyclerView = view.findViewById(R.id.student_recycleview);

        sort_student = view.findViewById(R.id.sort_student);
        find_student = view.findViewById(R.id.find_student);

        add_student = view.findViewById(R.id.add_student);
        import_list_student = view.findViewById(R.id.import_list_student);
        export_list_student = view.findViewById(R.id.export_list_student);
        studentList_ = new ArrayList<>();
        import_list_student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFilePicker();
            }
        });


        export_list_student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ExportFileStudent.exportToExcel(studentList_,requireContext());

            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        studentList = new ArrayList<>();

        studentAdapter = new StudentAdapter(studentList, getContext());
        recyclerView.setAdapter(studentAdapter);

        getStudent();
        generrateData();
        init();
        return view;
    }

    private  void init(){
        sort_student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSortDialog();
            }
        });

        find_student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSearchDialog();
            }
        });

        add_student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), StudentActivity.class);
                startActivity(intent);
            }
        });
    }

    public  void getStudent(){
        List<Student> students = new ArrayList<>();

        // Create some students
        Student student1 = new Student("John Doe", "123-456-7890", "john.doe@example.com", "2000-01-01", true, "avatar1.jpg", "2022-01-01", "2026-01-01");
        Student student2 = new Student("Jane Doe", "987-654-3210", "jane.doe@example.com", "2000-02-01", true, "avatar2.jpg", "2022-01-01", "2026-01-01");
        Student student3 = new Student("Bob Smith", "555-123-4567", "bob.smith@example.com", "2000-03-01", true, "avatar3.jpg", "2022-01-01", "2026-01-01");
        // Add students to the list
        Class_ classA = new Class_("Class A");
        student1.setClass_(classA);
        student2.setClass_(classA);
        student3.setClass_(classA);

        studentList.add(student1);
        studentList.add(student2);
        studentList.add(student3);

        classA.setStudents(studentList);

    }

    public void showSortDialog() {
        CustomSortDialogFragment sortDialog = new CustomSortDialogFragment();
        sortDialog.show(requireFragmentManager(), "CustomSortDialogFragment");
    }
    public void showSearchDialog() {
        SearchDialogFragment searchDialog = new SearchDialogFragment();
        searchDialog.show(requireFragmentManager(), "SearchDialogFragment");
    }

    @Override
    public void onSortApplied(boolean sortByDate, boolean sortByName, boolean sortByStartLearn) {

    }
    @Override
    public void onSearchApplied(String searchByName, String searchByPhone, String searchByEmail, String selectedClass, String searchByStartLearn) {

    }
    private void openFilePicker() {
        // Check if permission is granted
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {
            // Permission is already granted, open the file picker
            performFilePick();
        } else {
            // Permission is not granted, request it
            ActivityCompat.requestPermissions(requireActivity(),
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
                Toast.makeText(getActivity(), "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_EXCEL_FILE_REQUEST && resultCode == Activity.RESULT_OK) {
            Uri selectedFileUri = data.getData();
            Toast.makeText(getActivity(),selectedFileUri.toString(),Toast.LENGTH_LONG).show();

            if (selectedFileUri != null) {
                // Now you can use the selectedFileUri to read the Excel file
                try {
                    InputStream inputStream = getInputStreamFromUri(selectedFileUri);
                    Toast.makeText(getActivity(),selectedFileUri.toString(),Toast.LENGTH_SHORT).show();
                    Log.d("uri", selectedFileUri.toString());
                    List<StudentDTO> list = ReadFIleStudent.readExcelFile(getActivity(), inputStream);

                    for (StudentDTO studentDTO: list){
                        Log.d("StudentDTO", studentDTO.toString());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    // Handle the error, for example, show a Toast
                    Toast.makeText(getActivity(), "Error reading file", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
    private InputStream getInputStreamFromUri(Uri uri) throws IOException {
        // Check if the Uri scheme is content
        if ("content".equals(uri.getScheme())) {
            return getActivity().getContentResolver().openInputStream(uri);
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
    private  void generrateData(){
        // Create a class
        Class_ classA = new Class_("Class A");

        // Create subjects
        Subject math = new Subject("Math");
        Subject english = new Subject("English");

        // Create certificates
        Certificate certificate1 = new Certificate("Certificate 1", "2021-01-01", "2021-12-31", 90.5, "Excellent", "link1");
        Certificate certificate2 = new Certificate("Certificate 2", "2022-01-01", "2022-12-31", 85.0, "Very Good", "link2");

        // Create score subjects
        ScoreSubject mathScore = new ScoreSubject("1", 95.0, math, "2021-09-01");
        ScoreSubject englishScore = new ScoreSubject("2", 88.5, english, "2021-09-01");

        List<ScoreSubject> scoreSubjects = new ArrayList<>();
        scoreSubjects.add(mathScore);
        scoreSubjects.add(englishScore);

        // Create students
        Student student1 = new Student("John Doe", "123456789", "john@example.com", "2000-01-01", true,
                true, "avatar1", "2020-09-01", "2024-06-30", 3.8, classA);
        student1.setScoreSubjects(scoreSubjects);
        List<Certificate> certificatesForStudent1 = new ArrayList<>();
        certificatesForStudent1.add(certificate1);
        certificatesForStudent1.add(certificate2);
        // Add more certificates if needed

        student1.setCertificates(certificatesForStudent1);


        Student student2 = new Student("Jane Doe", "987654321", "jane@example.com", "2001-03-15", false,
                false, "avatar2", "2020-09-01", "2024-06-30", 3.5, classA);

        Student student3 = new Student("Bob Smith", "555555555", "bob@example.com", "2002-07-20", true,
                true, "avatar3", "2020-09-01", "2024-06-30", 3.2, classA);
        List<Certificate> certificatesForStudent3 = new ArrayList<>();
        certificatesForStudent3.add(certificate1);

        student3.setCertificates(certificatesForStudent3);


        // Add students to the class

        studentList_.add(student1);
        studentList_.add(student2);
        studentList_.add(student3);
        classA.setStudents(studentList_);

    }


}