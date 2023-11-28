package com.example.studentmanagement.Fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.studentmanagement.Adapter.ClassAdapter;
import com.example.studentmanagement.Adapter.SubjectAdapter;
import com.example.studentmanagement.Models.Class_;
import com.example.studentmanagement.Models.Student;
import com.example.studentmanagement.Models.Subject;
import com.example.studentmanagement.R;
import com.example.studentmanagement.utils.DatabaseManagerClass;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

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
    private String role;

    private FloatingActionButton menu_add_class;
    private DatabaseManagerClass databaseManager;

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

        databaseManager =   new DatabaseManagerClass();

        SharedPreferences preferences = requireContext().getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        role = preferences.getString("role", "");

        recyclerView = view.findViewById(R.id.class_recycleview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        classList = new ArrayList<>();
        classAdapter = new ClassAdapter(classList, getContext());

        recyclerView.setAdapter(classAdapter);


        // Fetch classes from Firestore
        fetchClassesFromFirestore();

        menu_add_class = view.findViewById(R.id.menu_add_class);

        if(role.equals("EMPLOYEE")){
            menu_add_class.setVisibility(View.GONE);
        }else {

            menu_add_class.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showAddEditCertificateDialog();
                }
            });

        }


        return view;
    }

    private void fetchClassesFromFirestore() {
        // Fetch classes from Firestore
        Task<QuerySnapshot> getAllClassesTask = databaseManager.getAllClasses();

        getAllClassesTask.addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    // Process the QuerySnapshot and update the RecyclerView
                    updateRecyclerView(task.getResult());
                } else {
                    // Handle the error
                    Exception exception = task.getException();
                    // Log or display an error message
                }
            }
        });
    }

    private void updateRecyclerView(QuerySnapshot querySnapshot) {
        classList.clear();
        for (DocumentSnapshot document : querySnapshot.getDocuments()) {
            Class_ class_ = document.toObject(Class_.class);
            if (class_ != null) {
                classList.add(class_);
            }
        }
        classAdapter.notifyDataSetChanged();
    }

    private String generateCustomPushId() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy-HH-mm-ss");
        LocalDateTime now = LocalDateTime.now();
        return formatter.format(now).toString().replace("-","");
    }

    public void showAddEditCertificateDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_add_class, null);
        builder.setView(dialogView);

        final EditText name = dialogView.findViewById(R.id.name_classadd);

        builder.setTitle("Add New Class");

        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if(name.getText().toString() != "" && name.getText() != null){
                    Class_ class_   = new Class_(generateCustomPushId(),name.getText().toString());
                    saveNewClass(class_);
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

    private void saveNewClass(Class_ aClass) {
        Task<Void> addClassTask = databaseManager.addClass(aClass);

        addClassTask.addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getContext(),"Upload Class Success!", Toast.LENGTH_SHORT).show();
                    classList.add(aClass);
                    int position = classList.size() - 1; // Get the position of the added item
                    classAdapter.notifyItemInserted(position);
                } else {
                    // Handle the error if the task was not successful
                    Exception exception = task.getException();
                    Toast.makeText(getContext(),"Upload Class Fail!", Toast.LENGTH_SHORT).show();
                    // Log or display an error message
                }
            }
        });
    }
}