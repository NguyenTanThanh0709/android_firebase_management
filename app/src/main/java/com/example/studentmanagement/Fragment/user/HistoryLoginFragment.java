package com.example.studentmanagement.Fragment.user;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.studentmanagement.Adapter.User.HistoryLoginAdapter;
import com.example.studentmanagement.Models.HistoryLogin;
import com.example.studentmanagement.Models.Role;
import com.example.studentmanagement.Models.User;
import com.example.studentmanagement.R;
import com.example.studentmanagement.utils.DatabaseManagerUser;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

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
    private String email = "";

    private DatabaseManagerUser databaseManagerUser;


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
        View view = inflater.inflate(R.layout.fragment_history_login, container, false);
        databaseManagerUser = new DatabaseManagerUser();

        recyclerView = view.findViewById(R.id.historylogins_recycleview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        historyLogins = new ArrayList<>();
        // Remove the type declaration, just initialize the class field
        historyLoginAdapter = new HistoryLoginAdapter(historyLogins, getContext());
        recyclerView.setAdapter(historyLoginAdapter);


        Bundle args = getArguments();
        if (args != null) {
            email = args.getString("email", "");

            // Now you have the email and type values, you can use them as needed
            Log.d("FormEmployeeFragment", "Email: " + email);

            if (email != "") {
                getOne(email);
                getListHistorys(email);
            }
        }


        return view;
    }


    private void getOne(String email){

        Task<DocumentSnapshot> task = databaseManagerUser.getUserById(email);

        task.addOnSuccessListener(documentSnapshot -> {
            // Check if the document exists
            if (documentSnapshot.exists()) {
                // Document exists, you can get the User object
                User user = documentSnapshot.toObject(User.class);
                if (user != null) {
                    historyLoginAdapter.setName(user.getName());
                    historyLoginAdapter.notifyDataSetChanged();
                }
            } else {
                Toast.makeText(getContext(), "User Not Found", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(e -> {
            // Handle any errors that occurred during the operation
            // For example, log the error or show a Toast message
            Toast.makeText(getContext(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        });

    }



    private void getListHistorys(String email){

        Task<QuerySnapshot> historyListTask = databaseManagerUser.getListHistory(email);

        historyListTask.addOnSuccessListener(queryDocumentSnapshots -> {
            historyLogins.clear(); // Clear the class field directly
            for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                HistoryLogin historyLogin = document.toObject(HistoryLogin.class);
                historyLogins.add(historyLogin);
            }
            historyLoginAdapter.notifyDataSetChanged();
        }).addOnFailureListener(e -> {
            // Handle any errors that occurred during the operation
            // For example, log the error or show a Toast message
            Toast.makeText(getContext(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        });
    }


}