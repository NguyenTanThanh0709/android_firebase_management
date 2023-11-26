package com.example.studentmanagement.Fragment.user;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.studentmanagement.Activity.EmployeeActivity;
import com.example.studentmanagement.Adapter.User.UserAdapter;
import com.example.studentmanagement.Models.Role;
import com.example.studentmanagement.Models.User;
import com.example.studentmanagement.R;
import com.example.studentmanagement.utils.DatabaseManagerUser;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EmployeeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EmployeeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView recyclerView;
    private UserAdapter userAdapter;
    private List<User> userList;

    private FloatingActionButton menu_add_employee;
    private DatabaseManagerUser databaseManagerUser;



    public EmployeeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EmployeeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EmployeeFragment newInstance(String param1, String param2) {
        EmployeeFragment fragment = new EmployeeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public static EmployeeFragment newInstance() {
        return new EmployeeFragment();
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
        View view = inflater.inflate(R.layout.fragment_employee, container, false);

        databaseManagerUser = new DatabaseManagerUser();

        recyclerView = view.findViewById(R.id.employee_recycleview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        userList = new ArrayList<>();
        userAdapter = new UserAdapter(userList, getContext());
        recyclerView.setAdapter(userAdapter);

        getListUser();
        menu_add_employee = view.findViewById(R.id.menu_add_employee);
        menu_add_employee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), EmployeeActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }

    private void getListUser(){
        Task<QuerySnapshot> task = databaseManagerUser.getAllUsers();
        List<User> tempUserList = new ArrayList<>();

        task.addOnSuccessListener(queryDocumentSnapshots -> {
            // Xử lý kết quả thành công
            for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                User user = User.fromQueryDocumentSnapshot(document);
                Log.d("User", user.toString());
                tempUserList.add(user);
            }
            // Cập nhật userList khi có dữ liệu mới từ Firestore
            userList.clear();
            userList.addAll(tempUserList);
            userAdapter.notifyDataSetChanged();
        }).addOnFailureListener(e -> {
            // Xử lý lỗi
            Log.e("Firestore", "Error getting users", e);
        });


    }

}