package com.example.studentmanagement.Fragment.student;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.text.TextUtils;
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
import com.example.studentmanagement.utils.DatabaseManagerClass;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
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
    private List<Class_> classList;
    private Button sort_student;
    private Button find_student;
    private  Button add_student;
    private String role;
    private  Button export_list_student;
    private DatabaseManagerClass databaseManagerClass;
    private  Button import_list_student;
    private DatabaseManagerStudent databaseManagerStudent;
    private static final int PICK_EXCEL_FILE_REQUEST = 123;
    public StudentFragment() {
        // Required empty public constructor
    }

    private static final String ARG_CLASS_ID = "classId";

    // Add a static method to create a new instance of StudentFragment with the classId parameter
    public static StudentFragment newInstance(String classId) {
        StudentFragment fragment = new StudentFragment();
        Bundle args = new Bundle();
        args.putString(ARG_CLASS_ID, classId);
        fragment.setArguments(args);
        return fragment;
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
        databaseManagerClass = new DatabaseManagerClass();
        classList  = new ArrayList<>();
        studentList_ = new ArrayList<>();
        sort_student = view.findViewById(R.id.sort_student);
        find_student = view.findViewById(R.id.find_student);
        add_student = view.findViewById(R.id.add_student);
        import_list_student = view.findViewById(R.id.import_list_student);
        export_list_student = view.findViewById(R.id.export_list_student);

        SharedPreferences preferences = requireContext().getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        role = preferences.getString("role", "");

        if(role.equals("EMPLOYEE")){
            import_list_student.setVisibility(View.GONE);
            export_list_student.setVisibility(View.GONE);
            add_student.setVisibility(View.GONE);
        }else {

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
                    ExportFileStudent.exportToExcel(studentList,requireContext());

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



        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        studentList = new ArrayList<>();

        studentAdapter = new StudentAdapter(studentList, getContext());
        recyclerView.setAdapter(studentAdapter);


        getAllClass();
        getAllStudent();

        init();

        if (getArguments() != null) {
            String classId = getArguments().getString(ARG_CLASS_ID);
            List<Student> list = filterStudentsByClass(studentList,classId);
            studentAdapter.setFilteredStudentList(list);
        }
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
                studentList_.addAll(tempUserList);
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


    }


    public void showSortDialog() {
        CustomSortDialogFragment sortDialog = new CustomSortDialogFragment();
        sortDialog.setSortDialogListener(this); // Set the listener
        sortDialog.show(requireFragmentManager(), "CustomSortDialogFragment");
    }
    public void showSearchDialog() {
        SearchDialogFragment searchDialog = new SearchDialogFragment();
        searchDialog.setSearchDialogListener(this);
        searchDialog.setClassList(classList);
        searchDialog.show(requireFragmentManager(), "SearchDialogFragment");
    }

    @Override
    public void onSortApplied(boolean sortByName, boolean sortByDate, boolean sortByGPA, boolean sortByStartLearn, boolean sortByName_,boolean sortByDate_,boolean sortByGPA_, boolean sortByStartLearn_) {
        // Example:
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        Collections.sort(studentList, new Comparator<Student>() {
            @Override
            public int compare(Student student1, Student student2) {
                if (sortByName) {
                    // Compare by name logic
                    return student1.getName().compareTo(student2.getName()); // Adjust the comparison based on your Student model
                } else if (sortByDate) {
                    try {
                        // Chuyển đổi ngày sinh từ chuỗi sang đối tượng Date
                        Date date1 = dateFormat.parse(student1.getBirthDay());
                        Date date2 = dateFormat.parse(student2.getBirthDay());

                        // So sánh ngày sinh
                        if (date1 != null && date2 != null) {
                            return date1.compareTo(date2);
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    // Mặc định trả về 0 nếu có lỗi
                    return 0;
                } else if (sortByGPA) {
                    // Compare by GPA logic
                    return Double.compare(student1.getGPA(), student2.getGPA());
                } else if (sortByStartLearn) {
                    // Compare by start learn logic
                    try {
                        // Chuyển đổi ngày sinh từ chuỗi sang đối tượng Date
                        Date date1 = dateFormat.parse(student1.getStartSchool());
                        Date date2 = dateFormat.parse(student2.getStartSchool());

                        // So sánh ngày sinh
                        if (date1 != null && date2 != null) {
                            return date1.compareTo(date2);
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    // Mặc định trả về 0 nếu có lỗi
                    return 0;

                }else if (sortByName_) {
                    // Compare by name logic
                    return student2.getName().compareTo(student1.getName()); // Adjust the comparison based on your Student model
                } else if (sortByDate_) {
                    try {
                        // Chuyển đổi ngày sinh từ chuỗi sang đối tượng Date
                        Date date1 = dateFormat.parse(student1.getBirthDay());
                        Date date2 = dateFormat.parse(student2.getBirthDay());

                        // So sánh ngày sinh
                        if (date1 != null && date2 != null) {
                            return date2.compareTo(date1);
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    // Mặc định trả về 0 nếu có lỗi
                    return 0;
                } else if (sortByGPA_) {
                    // Compare by GPA logic
                    return Double.compare(student2.getGPA(), student1.getGPA());
                } else if (sortByStartLearn_) {
                    try {
                        // Chuyển đổi ngày sinh từ chuỗi sang đối tượng Date
                        Date date1 = dateFormat.parse(student1.getStartSchool());
                        Date date2 = dateFormat.parse(student2.getStartSchool());

                        // So sánh ngày sinh
                        if (date1 != null && date2 != null) {
                            return date2.compareTo(date1);
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    // Mặc định trả về 0 nếu có lỗi
                    return 0;
                }
                // Add more criteria as needed

                return 0; // Default comparison, adjust based on your needs
            }
        });

        // Update the adapter after sorting
        studentAdapter.notifyDataSetChanged();
    }


    @Override
    public void onSearchApplied(Boolean checkbox, String searchByName, String searchByPhone, String searchByEmail, String selectedClass, String searchByStartLearn, String searchByBirthDay, Boolean gender) {
        List<Student> result = new ArrayList<>(studentList_);

        if(checkbox){
            studentAdapter.setFilteredStudentList(studentList_);
        }else {
            result = filterStudentsByName(result, searchByName);
            result = filterStudentsByPhone(new ArrayList<>(result), searchByPhone);
            result = filterStudentsByEmail(new ArrayList<>(result), searchByEmail);
            result = filterStudentsByStartLearn(new ArrayList<>(result), searchByStartLearn);
            result = filterStudentsByBirthDay(new ArrayList<>(result), searchByBirthDay);
            result = filterStudentsByGender(new ArrayList<>(result), gender);
            result = filterStudentsByClass(new ArrayList<>(result), selectedClass);


            // Update the adapter with the filtered list
            studentAdapter.setFilteredStudentList(result);
        }


    }

    private List<Student> filterStudentsByName(List<Student> list, String searchByName) {
        List<Student> result = new ArrayList<>();
        if(searchByName.isEmpty()){
            return list;
        }
        for (Student student : list) {
            if (student.getName().toLowerCase().contains(searchByName.toLowerCase())) {
                result.add(student);
            }
        }
        return result;
    }

    private List<Student> filterStudentsByPhone(List<Student> list,String searchByPhone) {
        List<Student> result = new ArrayList<>();
        if(searchByPhone.isEmpty()){
            return list;
        }
        for (Student student : list) {
            if (student.getPhoneNumber().toLowerCase().contains(searchByPhone.toLowerCase())) {
                result.add(student);
            }
        }
        return result;
    }

    private List<Student> filterStudentsByEmail(List<Student> list,String searchByEmail) {
        if(searchByEmail.isEmpty()){
            return list;
        }
        List<Student> result = new ArrayList<>();
        for (Student student : list) {
            if (student.getEmail().toLowerCase().contains(searchByEmail.toLowerCase())) {
                result.add(student);
            }
        }
        return result;
    }

    private List<Student> filterStudentsByClass(List<Student> list,String selectedClass) {
        if(selectedClass.equals("tìm theo lớp")){
            return list;
        }
        List<Student> result = new ArrayList<>();
        for (Student student : list) {
            if (student.getClass_().getName().equals(selectedClass)) {
                result.add(student);
            }
        }
        return result;
    }

    private List<Student> filterStudentsByStartLearn(List<Student> list,String searchByStartLearn) {
        if(searchByStartLearn.isEmpty()){
            return list;
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        List<Student> result = new ArrayList<>();
        for (Student student : studentList) {
            try {
                Date date = dateFormat.parse(student.getStartSchool());
                SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
                String year = yearFormat.format(date);
                if(year.equals(searchByStartLearn)){
                    result.add(student);
                }

            } catch (ParseException e) {
                e.printStackTrace();
                continue;
            }
        }
        return result;
    }

    private List<Student> filterStudentsByBirthDay(List<Student> list,String searchByBirthDay) {
        if(searchByBirthDay.isEmpty()){
            return list;
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        List<Student> result = new ArrayList<>();
        for (Student student : studentList) {
            try {
                Date date = dateFormat.parse(student.getBirthDay());
                SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
                String year = yearFormat.format(date);
                if(year.equals(searchByBirthDay)){
                    result.add(student);
                }

            } catch (ParseException e) {
                e.printStackTrace();
                continue;
            }
        }
        return result;
    }

    private List<Student> filterStudentsByGender(List<Student> list,Boolean gender) {
        if(gender == null){
            return list;
        }
        List<Student> result = new ArrayList<>();
        for (Student student : list) {
            if (student.getSex() == gender) {
                result.add(student);
            }
        }
        return result;
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

                    addFromDataBase(list);
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

    private void getAllClass() {
        Task<QuerySnapshot> getAllClassesTask = databaseManagerClass.getAllClasses();
        getAllClassesTask.addOnSuccessListener(queryDocumentSnapshots -> {
            List<Class_> tempList = new ArrayList<>();

            for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                // Convert each document to a Class_ object and add it to the list
                Class_ myClass = document.toObject(Class_.class);
                tempList.add(myClass);
                Log.d("Class", myClass.toString());
            }

            // Clear and add to the main list
            classList.clear();
            classList.addAll(tempList);
            // Notify any adapter or UI component that relies on this data to update
            // For example, if you're using an ArrayAdapter, you would call adapter.notifyDataSetChanged();
        }).addOnFailureListener(e -> {
            // Handle failure
            Log.e("TAG", "Error getting classes: ", e);
        });
    }

    private void addFromDataBase(List<StudentDTO> list){
        List<Student> studentList = new ArrayList<>();

        for(StudentDTO studentDTO: list){

            Class_ class_ = null;
            for(Class_ class_1 : classList){
                if(class_1.getId().equals(studentDTO.getClass_())){
                    class_ = class_1;
                    break;
                }
            }
            Student student = Student.fromStudentDTO(studentDTO,class_);
            studentList.add(student);
        }

        addList(studentList);
    }

    private void addList(List<Student> studentList_) {
        List<Student> list = new ArrayList<>();
        for(Student student: studentList_){
            if(student.getPhoneNumber() ==null){
                continue;
            }
            list.add(student);
        }

        Log.d("OK","OK");

        // Create an instance of DatabaseManagerStudent

// Call the method to add students and associate them with the class
        Task<Void> addStudentsTask = databaseManagerStudent.addListStudentsAndAssociateWithClass(list);

// Add a continuation to handle the result of the task
        addStudentsTask.addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                studentList.addAll(list);
                studentAdapter.notifyDataSetChanged();
                Toast.makeText(getContext(),"Upload List Student Success", Toast.LENGTH_SHORT);
            } else {
                // Error: Handle the error appropriately
                Exception exception = task.getException();
                if (exception != null) {
                    exception.printStackTrace();
                    Toast.makeText(getContext(),"Upload List Student Fail", Toast.LENGTH_SHORT);

                }
            }
        });


    }

}