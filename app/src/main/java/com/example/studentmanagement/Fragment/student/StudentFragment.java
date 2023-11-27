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
import com.example.studentmanagement.Models.User;
import com.example.studentmanagement.R;
import com.example.studentmanagement.dto.StudentDTO;
import com.example.studentmanagement.utils.DatabaseManagerStudent;
import com.example.studentmanagement.utils.File.ExportFileStudent;
import com.example.studentmanagement.utils.File.ReadFIleStudent;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

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
import java.util.Map;

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
    private DatabaseManagerStudent databaseManagerStudent;
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
        databaseManagerStudent = new DatabaseManagerStudent();
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

//        getStudent();
//        generrateData();

        getAllStudent();

        init();
        return view;
    }

    private void getAllStudent() {

        // Call the getAllStudents method
        Task<QuerySnapshot> getAllStudentsTask = databaseManagerStudent.getAllStudents();
        List<Student> tempUserList = new ArrayList<>();
// Add a listener to handle the result when the task is complete
        getAllStudentsTask.addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                // The task was successful
                // Access the QuerySnapshot containing all student documents
                QuerySnapshot querySnapshot = task.getResult();

                // Now you can process the QuerySnapshot, for example, iterate through documents
                for (DocumentSnapshot document : querySnapshot.getDocuments()) {
                    // Access each document's data
                    Map<String, Object> studentData = document.getData();
                    Student student = Student.fromQueryDocumentSnapshot((QueryDocumentSnapshot) document);
                    tempUserList.add(student);
                    // Handle the student data as needed
                }
                studentList.clear();
                studentList.addAll(tempUserList);
                studentAdapter.notifyDataSetChanged();
            } else {
                // Handle the error if the task was not successful
                Exception exception = task.getException();
                // Log or display an error message
            }
        });


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

}