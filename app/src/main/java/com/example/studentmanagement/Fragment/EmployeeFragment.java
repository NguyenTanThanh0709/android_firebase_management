package com.example.studentmanagement.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.studentmanagement.Activity.EmployeeActivity;
import com.example.studentmanagement.Activity.MainActivity;
import com.example.studentmanagement.Adapter.User.UserAdapter;
import com.example.studentmanagement.Models.Role;
import com.example.studentmanagement.Models.User;
import com.example.studentmanagement.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

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
        userList.add(new User("String" , "String" , "String" , "String" , true , "https://daotao.muasamcong.gov.vn/learning/File/User/Avt/avatar-default.png" , Role.EMPLOYEE , "String" ));
        userList.add(new User("String" , "String" , "String" , "String" , true , "https://daotao.muasamcong.gov.vn/learning/File/User/Avt/avatar-default.png" , Role.EMPLOYEE , "String" ));
        userList.add(new User("String" , "String" , "String" , "String" , true , "https://daotao.muasamcong.gov.vn/learning/File/User/Avt/avatar-default.png" , Role.EMPLOYEE , "String" ));
    }

}