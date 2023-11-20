package com.example.studentmanagement.Fragment.student;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.studentmanagement.Activity.EmployeeActivity;
import com.example.studentmanagement.Activity.StudentActivity;
import com.example.studentmanagement.Adapter.Student.StudentAdapter;
import com.example.studentmanagement.Models.Class_;
import com.example.studentmanagement.Models.Student;
import com.example.studentmanagement.R;

import java.util.ArrayList;
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


    private Button sort_student;
    private Button find_student;
    private  Button add_student;
    private  Button export_list_student;
    private  Button import_list_student;

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


        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        studentList = new ArrayList<>();
        studentAdapter = new StudentAdapter(studentList, getContext());
        recyclerView.setAdapter(studentAdapter);

        getStudent();

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
}