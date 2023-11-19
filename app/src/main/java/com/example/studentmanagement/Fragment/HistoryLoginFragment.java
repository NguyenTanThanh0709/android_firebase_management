package com.example.studentmanagement.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.studentmanagement.Adapter.User.HistoryLoginAdapter;
import com.example.studentmanagement.Adapter.User.UserAdapter;
import com.example.studentmanagement.Models.HistoryLogin;
import com.example.studentmanagement.Models.Role;
import com.example.studentmanagement.Models.User;
import com.example.studentmanagement.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HistoryLoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HistoryLoginFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView recyclerView;
    private HistoryLoginAdapter historyLoginAdapter;
    private List<HistoryLogin> historyLogins;
    private User user;

    public HistoryLoginFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HistoryLoginFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HistoryLoginFragment newInstance(String param1, String param2) {
        HistoryLoginFragment fragment = new HistoryLoginFragment();
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
        View view = inflater.inflate(R.layout.fragment_history_login, container, false);
        recyclerView = view.findViewById(R.id.historylogins_recycleview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        historyLogins = new ArrayList<>();
        HistoryLoginAdapter  historyLoginAdapter= new HistoryLoginAdapter(historyLogins, getContext());
        recyclerView.setAdapter(historyLoginAdapter);
        getListUser();
        return view;
    }

    private void getListUser(){

        user = new User("String" , "String" , "String" , "String" , true , "https://daotao.muasamcong.gov.vn/learning/File/User/Avt/avatar-default.png" , Role.EMPLOYEE , "String" );

        historyLogins.add(new HistoryLogin("1", "2023-11-20T08:00:00", "2023-11-20T17:00:00", "Office", "123"));
        historyLogins.add(new HistoryLogin("2", "2023-11-21T09:30:00", "2023-11-21T18:30:00", "Home", "456"));
    }
}