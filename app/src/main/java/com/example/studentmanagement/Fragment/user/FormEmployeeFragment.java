package com.example.studentmanagement.Fragment.user;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.studentmanagement.Models.Role;
import com.example.studentmanagement.Models.User;
import com.example.studentmanagement.R;
import com.example.studentmanagement.utils.DatabaseManagerUser;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.DocumentSnapshot;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FormEmployeeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FormEmployeeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private TextInputEditText editTextDate;
    private EditText name_employee;
    private EditText phone_employee;
    private EditText email_employee;
    private EditText password_employee;

    private ImageView image_user;

    private RadioGroup radioGroup;
    private RadioButton radioFemale ;
    private  RadioButton radioMale;

    private RadioGroup radiogroupStatus;
    private RadioButton radioNormal ;
    private  RadioButton radioLocked;

    private Spinner spinnerRole;

    private boolean isFemale;
    private boolean isStatus;

    private DatabaseManagerUser databaseManagerUser;
    private  Button submit;
    private  Button clear;


    private String email = "";
    private String type = "";

    public FormEmployeeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FormEmployeeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FormEmployeeFragment newInstance(String param1, String param2) {
        FormEmployeeFragment fragment = new FormEmployeeFragment();
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

    private void getOne(String email){

        Task<DocumentSnapshot> task = databaseManagerUser.getUserById(email);

// Add success and failure listeners to handle the result
        task.addOnSuccessListener(documentSnapshot -> {
            // Check if the document exists
            if (documentSnapshot.exists()) {
                // Document exists, you can get the User object
                User user = documentSnapshot.toObject(User.class);
                if (user != null) {

                    FullInfo(user,type);

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

    private void FullInfo(User user, String type){

        Picasso.with(requireContext())
                .load(user.getAvatar())
                .placeholder(R.mipmap.ic_launcher)
                .error(R.drawable.user)
                .into(image_user, new Callback() {
                    @Override
                    public void onSuccess() {
                        // Set the URL as a tag for the ImageView
                        image_user.setTag(user.getAvatar());
                    }

                    @Override
                    public void onError() {
                        // Handle error if needed
                    }
                });

        name_employee.setText(user.getName());
        phone_employee.setText(user.getPhoneNumber());
        email_employee.setText(user.getEmail());
        password_employee.setText(user.getPassword());
        editTextDate.setText(user.getBirthDay());

        if (user.getSex() != null) {
            if (user.getSex()) {
                // Male
                radioGroup.check(R.id.radioMale);
            } else {
                // Female
                radioGroup.check(R.id.radioFemale);
            }
        }

        if (user.getStatus() != null) {
            if (user.getStatus()) {
                // Normal
                radiogroupStatus.check(R.id.radioNormal);
            } else {
                // Locked
                radiogroupStatus.check(R.id.radioLocked);
            }
        }

        if(user.getRole().toString().equals("ADMIN")){
            spinnerRole.setSelection(0);

        }else if(user.getRole().toString().equals("MANAGER")){
            spinnerRole.setSelection(1);

        }else {
            spinnerRole.setSelection(2);

        }
        email_employee.setEnabled(false);
        if ("see".equals(type)) {
            name_employee.setEnabled(false);
            phone_employee.setEnabled(false);

            password_employee.setEnabled(false);
            editTextDate.setEnabled(false);
            radioFemale.setEnabled(false);
            radioMale.setEnabled(false);
            radioNormal.setEnabled(false);
            radioLocked.setEnabled(false);
            spinnerRole.setEnabled(false);
            submit.setEnabled(false);
            clear.setEnabled(false);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_form_employee, container, false);





        databaseManagerUser = new DatabaseManagerUser();
        String[] roles = {"ADMIN", "MANAGER", "EMPLOYEE"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, roles);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRole = view.findViewById(R.id.spinnerRole);

        spinnerRole.setAdapter(adapter);


        TextInputLayout textInputLayout = view.findViewById(R.id.textInputLayoutDate);
        editTextDate = textInputLayout.findViewById(R.id.editTextDate);

        name_employee = view.findViewById(R.id.name_employee);
        phone_employee = view.findViewById(R.id.phone_employee);
        email_employee = view.findViewById(R.id.email_employee);
        password_employee = view.findViewById(R.id.password_employee);

        radioGroup = view.findViewById(R.id.radioGroupSexForm);
        radioFemale = view.findViewById(R.id.radioFemale);
        radioMale = view.findViewById(R.id.radioMale);

        radiogroupStatus = view.findViewById(R.id.radiogroupStatus);
        radioNormal = view.findViewById(R.id.radioNormal);
        radioLocked = view.findViewById(R.id.radioLocked);
        image_user = view.findViewById(R.id.image_user);

        submit = view.findViewById(R.id.submit);
        clear = view.findViewById(R.id.clear);

        Bundle args = getArguments();

        if (args != null) {
            email = args.getString("email", "");
            type = args.getString("type", "");

            // Now you have the email and type values, you can use them as needed
            Log.d("FormEmployeeFragment", "Email: " + email);
            Log.d("FormEmployeeFragment", "Type: " + type);

            if(type != "" && email != ""){
                getOne(email);
            }


        }


        editTextDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(v);
            }
        });

        eventButton(view);



        return view;
    }

    private  void eventButton(View view){
        Button submit = view.findViewById(R.id.submit);
        Button clear = view.findViewById(R.id.clear);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                add();
            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name_employee.setText("");
                phone_employee.setText("");
                email_employee.setText("");
                password_employee.setText("");
                editTextDate.setText("");  // Clear date field

                // Clear RadioGroup selections
                radioGroup.clearCheck();
                radiogroupStatus.clearCheck();
            }
        });
    }


    private boolean validateForm() {
        String name = name_employee.getText().toString().trim();
        String phone = phone_employee.getText().toString().trim();
        String email = email_employee.getText().toString().trim();
        String password = password_employee.getText().toString().trim();
        String date = editTextDate.getText().toString().trim();

        boolean isValid = true;

        if (TextUtils.isEmpty(name)) {
            name_employee.setError("Name is required");
            isValid = false;
        }

        if (TextUtils.isEmpty(phone)) {
            phone_employee.setError("Phone is required");
            isValid = false;
        }

        if (TextUtils.isEmpty(email)) {
            email_employee.setError("Email is required");
            isValid = false;
        }

        if (TextUtils.isEmpty(password)) {
            password_employee.setError("Password is required");
            isValid = false;
        }

        if (TextUtils.isEmpty(date)) {
            // Assuming that your date field is not empty
            // If it's optional, you can remove this block
            editTextDate.setError("Date is required");
            isValid = false;
        }

        int genderId = radioGroup.getCheckedRadioButtonId();
        if (genderId == -1) {
            // No gender selected
            Toast.makeText(getContext(), "Please select gender", Toast.LENGTH_SHORT).show();
            isValid = false;
        }

        int statusId = radiogroupStatus.getCheckedRadioButtonId();
        if (statusId == -1) {
            // No status selected
            Toast.makeText(getContext(), "Please select status", Toast.LENGTH_SHORT).show();
            isValid = false;
        }

        return isValid;
    }

    private Role convertToUserRole(String roleString) {
        if ("ADMIN".equals(roleString)) {
            return Role.ADMIN;
        } else if ("MANAGER".equals(roleString)) {
            return Role.MANAGER;
        } else if ("EMPLOYEE".equals(roleString)) {
            return Role.EMPLOYEE;
        } else {
            // Handle unknown role or return a default value
            return Role.EMPLOYEE;
        }
    }


    private void add(){
        if (validateForm()) {
            // Get data and proceed
            String name = name_employee.getText().toString().trim();
            String phone = phone_employee.getText().toString().trim();
            String email = email_employee.getText().toString().trim();
            String password = password_employee.getText().toString().trim();
            String date = editTextDate.getText().toString().trim();
            String selectedRole_ = spinnerRole.getSelectedItem().toString();
            Role  selectedRole = convertToUserRole(selectedRole_);
            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    if (checkedId == R.id.radioFemale) {
                        // Female radio button checked
                        isFemale = true;
                    } else if (checkedId == R.id.radioMale) {
                        // Male radio button checked
                        isFemale = false;
                    }
                }
            });
            radiogroupStatus.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    if (checkedId == R.id.radioNormal) {
                        // Female radio button checked
                        isStatus = true;
                    } else if (checkedId == R.id.radioLocked) {
                        // Male radio button checked
                        isStatus = false;
                    }
                }
            });

            User user = new User(
                    name,
                    phone,
                    email,
                    date,
                    isStatus,
                    "https://firebasestorage.googleapis.com/v0/b/tsimple-384dd.appspot.com/o/image%2Fdb933cd2-a849-422c-9ac2-944a1fb2d718?alt=media&token=59841470-affd-45bf-8ff9-925a2f5f6875",
                    selectedRole,
                    password,
                    isFemale

            );

            if(type.equals("edit")){
                user.setAvatar((String) image_user.getTag());
                updateUser(user);
            }else {
                addUserToFirestore(user);
            }

            Log.d("User",user.toString());


        } else {
            Toast.makeText(getContext(), "Form không hợp lệ", Toast.LENGTH_SHORT).show();
        }

    }

    private void updateUser(User user){
        Task<Void> updateTask = databaseManagerUser.updateUser(user.getEmail(), user);

// Add an onCompleteListener to handle the result of the task
        updateTask.addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(getContext(), "Update User Success", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Update User Fail", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addUserToFirestore(User user) {
        // Call the addUser method from DatabaseManagerUser
        Task<Void> addUserTask = databaseManagerUser.addUser(user);

        // Add an onCompleteListener to handle the result of the task
        addUserTask.addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                // User added successfully
                Toast.makeText(getContext(), "User added successfully", Toast.LENGTH_SHORT).show();
                name_employee.setText("");
                phone_employee.setText("");
                email_employee.setText("");
                password_employee.setText("");
                editTextDate.setText("");  // Clear date field

                // Clear RadioGroup selections
                radioGroup.clearCheck();
                radiogroupStatus.clearCheck();
            } else {
                // Failed to add user
                Toast.makeText(getContext(), "Failed to add user", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void showDatePickerDialog(View view) {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                requireContext(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        String selectedDate = day + "/" + (month + 1) + "/" + year;
                        editTextDate.setText(selectedDate);
                    }
                },
                year, month, day
        );
        datePickerDialog.show();
    }
}