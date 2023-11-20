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

import com.example.studentmanagement.Adapter.ClassAdapter;
import com.example.studentmanagement.Adapter.SubjectAdapter;
import com.example.studentmanagement.Models.Class_;
import com.example.studentmanagement.Models.Student;
import com.example.studentmanagement.Models.Subject;
import com.example.studentmanagement.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ClassFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ClassFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView recyclerView;
    private ClassAdapter classAdapter;
    private List<Class_> classList;

    private FloatingActionButton menu_add_class;

    public ClassFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ClassFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ClassFragment newInstance(String param1, String param2) {
        ClassFragment fragment = new ClassFragment();
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
        View view =  inflater.inflate(R.layout.fragment_class, container, false);
        recyclerView = view.findViewById(R.id.class_recycleview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        classList = new ArrayList<>();
        classAdapter = new ClassAdapter(classList, getContext());

        recyclerView.setAdapter(classAdapter);


        createSampleClasses();

        menu_add_class = view.findViewById(R.id.menu_add_class);
        menu_add_class.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddEditCertificateDialog();
            }
        });

        return view;
    }

    public void createSampleClasses() {
        List<Class_> classes = new ArrayList<>();

        // Class 1
        List<Student> studentsClass1 = Arrays.asList(
                new Student("John Doe", "123456789", "john.doe@example.com", "1995-05-15", true, "avatar1.jpg", "2020-09-01", "2024-06-30"),
                new Student("Jane Doe", "987654321", "jane.doe@example.com", "1996-08-22", true, "avatar2.jpg", "2020-09-01", "2024-06-30")
        );
        Class_ class1 = new Class_("Class 1", studentsClass1);
        classList.add(class1);

        // Class 2
        List<Student> studentsClass2 = Arrays.asList(
                new Student("Alice Johnson", "111222333", "alice.johnson@example.com", "1997-02-10", true, "avatar3.jpg", "2020-09-01", "2024-06-30"),
                new Student("Bob Smith", "444555666", "bob.smith@example.com", "1998-11-05", true, "avatar4.jpg", "2020-09-01", "2024-06-30")
        );
        Class_ class2 = new Class_("Class 2", studentsClass2);
        classList.add(class2);

        // Class 3
        List<Student> studentsClass3 = Arrays.asList(
                new Student("Eva Brown", "777888999", "eva.brown@example.com", "1999-07-18", true, "avatar5.jpg", "2020-09-01", "2024-06-30"),
                new Student("David White", "222333444", "david.white@example.com", "2000-04-30", true, "avatar6.jpg", "2020-09-01", "2024-06-30")
        );
        Class_ class3 = new Class_("Class 3", studentsClass3);
        classList.add(class3);

    }

    public void showAddEditCertificateDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_add_class, null);
        builder.setView(dialogView);

        final EditText name = dialogView.findViewById(R.id.name_classadd);


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