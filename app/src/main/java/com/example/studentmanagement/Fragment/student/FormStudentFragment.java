package com.example.studentmanagement.Fragment.student;

import static androidx.fragment.app.FragmentManager.TAG;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.MediaStore;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.studentmanagement.Models.Class_;
import com.example.studentmanagement.Models.Student;
import com.example.studentmanagement.R;
import com.example.studentmanagement.utils.DatabaseManagerClass;
import com.example.studentmanagement.utils.DatabaseManagerStudent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.apache.poi.ss.formula.functions.T;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FormStudentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FormStudentFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private TextInputEditText editTextDate;
    private ImageView image_student;
    private EditText name_student;
    private EditText phone_student;
    private EditText email_student;
    private EditText gpa_student;
    private EditText startSchool_student;
    private EditText endSchool_student;
    final Class_[] classContainer = new Class_[1];

    private RadioGroup radioGroupSex;
    private RadioButton radioFemale;
    private RadioButton radioMale;

    private RadioGroup radiogroupStatus;
    private RadioButton radioNormal;
    private RadioButton radioLocked;

    private Spinner spinnerClass;

    private List<Class_> classList;

    private DatabaseManagerClass databaseManagerClass;
    private DatabaseManagerStudent databaseManagerStudent;

    private Button onSubmitClick;
    private Button onClear;

    private TextView text_img_student;

    StorageReference storageReference;

    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int PERMISSION_REQUEST_CODE = 100;
    private static final int CAMERA_REQUEST_CODE = 101;
    private static final int GALLERY_REQUEST_CODE = 102;

    private String phone = "";
    private String type = "";

    private  void getOne(String phone){

        Task<DocumentSnapshot> getStudentTask = databaseManagerStudent.getStudentById(phone);

        getStudentTask.addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    // Successfully retrieved the document snapshot
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        // Document exists, you can now retrieve the Student object
                        Student student = document.toObject(Student.class);
                        if (student != null) {
                            FullInfo(student,type);
                        }
                    } else {
                        // Document doesn't exist
                        Log.d("YourActivity", "Student document not found");
                    }
                } else {
                    // Failed to retrieve the document
                    Exception exception = task.getException();
                    Log.e("YourActivity", "Error getting student document", exception);
                }
            }
        });

    }

    private void FullInfo(Student student, String type) {
        Picasso.with(requireContext())
                .load(student.getAvatar())
                .placeholder(R.mipmap.ic_launcher)
                .error(R.drawable.user)
                .into(image_student, new Callback() {
                    @Override
                    public void onSuccess() {
                        // Set the URL as a tag for the ImageView
                        image_student.setTag(student.getAvatar());
                    }

                    @Override
                    public void onError() {
                        // Handle error if needed
                    }
                });
        name_student.setText(student.getName());
        phone_student.setText(student.getPhoneNumber());
        email_student.setText(student.getEmail());
        gpa_student.setText(String.valueOf(student.getGPA()));
        startSchool_student.setText(student.getStartSchool());
        editTextDate.setText(student.getBirthDay());
        endSchool_student.setText(student.getEndSchool());
        if (student.getSex() != null) {
            if (student.getSex()) {
                // Male
                radioGroupSex.check(R.id.radioMale);
            } else {
                // Female
                radioGroupSex.check(R.id.radioFemale);
            }
        }
        if (student.getStatus() != null) {
            if (student.getStatus()) {
                // Normal
                radiogroupStatus.check(R.id.radioNormal);
            } else {
                // Locked
                radiogroupStatus.check(R.id.radioLocked);
            }
        }

        if (student.getClass_() != null) {
            String selectedClassId = student.getClass_().getId();

            for (int i = 0; i < classList.size(); i++) {
                if (classList.get(i).getId().equals(selectedClassId)) {
                    spinnerClass.setSelection(i);
                    break;
                }
            }
        }

        phone_student.setEnabled(false);

        spinnerClass.setEnabled(false);

        if ("see".equals(type)) {
            name_student.setEnabled(false);
            phone_student.setEnabled(false);

            email_student.setEnabled(false);
            editTextDate.setEnabled(false);
            radioFemale.setEnabled(false);
            radioMale.setEnabled(false);
            gpa_student.setEnabled(false);
            startSchool_student.setEnabled(false);
            endSchool_student.setEnabled(false);
            radioNormal.setEnabled(false);
            radioLocked.setEnabled(false);
            spinnerClass.setEnabled(false);
            onSubmitClick.setEnabled(false);
            onClear.setEnabled(false);
        }
    }


    public FormStudentFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FormStudentFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FormStudentFragment newInstance(String param1, String param2) {
        FormStudentFragment fragment = new FormStudentFragment();
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
            setupSpinner();
            // Notify any adapter or UI component that relies on this data to update
            // For example, if you're using an ArrayAdapter, you would call adapter.notifyDataSetChanged();
        }).addOnFailureListener(e -> {
            // Handle failure
            Log.e("TAG", "Error getting classes: ", e);
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_form_student, container, false);

        databaseManagerClass  = new DatabaseManagerClass();
        databaseManagerStudent = new DatabaseManagerStudent();
        classList  = new ArrayList<>();

        FirebaseApp.initializeApp(getContext().getApplicationContext());
        storageReference = FirebaseStorage.getInstance().getReference();

        TextInputLayout textInputLayout = view.findViewById(R.id.textInputLayoutDate);
        editTextDate = textInputLayout.findViewById(R.id.editTextDate);
        image_student = view.findViewById(R.id.image_student);
        text_img_student = view.findViewById(R.id.text_img_student);
        text_img_student.setVisibility(View.GONE);
        name_student = view.findViewById(R.id.name_student);
        phone_student = view.findViewById(R.id.phone_student);
        email_student = view.findViewById(R.id.email_student);
        gpa_student = view.findViewById(R.id.gpa_student);
        startSchool_student = view.findViewById(R.id.startSchool_student);
        endSchool_student = view.findViewById(R.id.endSchool_student);

        radioGroupSex = view.findViewById(R.id.radioGroupSex);
        radioFemale = view.findViewById(R.id.radioFemale);
        radioMale = view.findViewById(R.id.radioMale);

        radiogroupStatus = view.findViewById(R.id.radiogroupStatus);
        radioNormal = view.findViewById(R.id.radioNormal);
        radioLocked = view.findViewById(R.id.radioLocked);

        spinnerClass = view.findViewById(R.id.spinnerClass);

        onClear = view.findViewById(R.id.onClear);
        onSubmitClick = view.findViewById(R.id.onSubmitClick);
        getAllClass();


        Bundle args = getArguments();

        if (args != null) {
            phone = args.getString("phone", "");
            type = args.getString("type", "");

            // Now you have the email and type values, you can use them as needed
            Log.d("FormEmployeeFragment", "phone: " + phone);
            Log.d("FormEmployeeFragment", "Type: " + type);

            if(type != "" && phone != ""){
                getOne(phone);
            }


        }


        event();
        eventButton(view);

        editTextDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(v);
            }
        });

        image_student.setOnClickListener(v -> openCameraOrGallery());

        return view;
    }

    private void eventButton(View view) {


        onClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editTextDate.setText("");
                name_student.setText("");
                phone_student.setText("");
                email_student.setText("");
                gpa_student.setText("");
                startSchool_student.setText("");
                endSchool_student.setText("");

// Clearing RadioGroup selections
                radioGroupSex.clearCheck();
                radiogroupStatus.clearCheck();
            }
        });

    }

    private boolean validateForm() {
        boolean isValid = true;

        String date = editTextDate.getText().toString().trim();
        String name = name_student.getText().toString().trim();
        String phone = phone_student.getText().toString().trim();
        String email = email_student.getText().toString().trim();
        String gpa = gpa_student.getText().toString().trim();
        String startSchool = startSchool_student.getText().toString().trim();
        String endSchool = endSchool_student.getText().toString().trim();

        if (endSchool.isEmpty()) {
            isValid = false;
            editTextDate.setError("Date is required");
        }

        if (phone.isEmpty()) {
            isValid = false;
            editTextDate.setError("Date is required");
        }

        if (email.isEmpty()) {
            isValid = false;
            editTextDate.setError("Date is required");
        }

        if (gpa.isEmpty()) {
            isValid = false;
            editTextDate.setError("Date is required");
        }

        if (startSchool.isEmpty()) {
            isValid = false;
            editTextDate.setError("Date is required");
        }

        if (date.isEmpty()) {
            isValid = false;
            editTextDate.setError("Date is required");
        }

        if (name.isEmpty()) {
            isValid = false;
            name_student.setError("Name is required");
        }

        // Perform similar checks for other fields

        // Check if a gender is selected
        if (radioGroupSex.getCheckedRadioButtonId() == -1) {
            isValid = false;
            // Show an error message for gender selection
            // (You might want to display a Snackbar, Toast, or set an error on a TextView)
        }

        // Check if a status is selected
        if (radiogroupStatus.getCheckedRadioButtonId() == -1) {
            isValid = false;
            // Show an error message for status selection
            // (You might want to display a Snackbar, Toast, or set an error on a TextView)
        }

        // Check if a class is selected
        if (spinnerClass.getSelectedItem() == null) {
            isValid = false;
            // Show an error message for class selection
            // (You might want to display a Snackbar, Toast, or set an error on a TextView)
        }

        return isValid;
    }


    private void setupSpinner() {
        // Assuming Class_ has a method getName() that returns the class name
        List<String> classNames = new ArrayList<>();
        for (Class_ class_ : classList) {
            classNames.add(class_.getName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, classNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerClass.setAdapter(adapter);
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


    private void event(){
        onSubmitClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String selectedDate = editTextDate.getText().toString();
                String studentName = name_student.getText().toString();
                String phoneNumber = phone_student.getText().toString();
                String email = email_student.getText().toString();
                String gpa = gpa_student.getText().toString();
                String startSchool = startSchool_student.getText().toString();
                String endSchool = endSchool_student.getText().toString();
                String img = text_img_student.getText().toString();

                if(img.equals("") || img == ""){
                    img = "https://firebasestorage.googleapis.com/v0/b/tsimple-384dd.appspot.com/o/image%2Fdb933cd2-a849-422c-9ac2-944a1fb2d718?alt=media&token=59841470-affd-45bf-8ff9-925a2f5f6875";
                }
                // Get the selected gender from RadioGroup
                int selectedGenderId = radioGroupSex.getCheckedRadioButtonId();
                boolean isMale = (selectedGenderId == R.id.radioMale);


                int selectedStatusId = radiogroupStatus.getCheckedRadioButtonId();
                boolean isNormal = (selectedStatusId == R.id.radioNormal);

                // Get the selected class from the Spinner
                int selectedClassPosition = spinnerClass.getSelectedItemPosition();
                String selectedClassId = "";
                Class_ class___ = null;
                if (selectedClassPosition != Spinner.INVALID_POSITION) {
                    // The position in the spinner corresponds to the position in the classList
                    Class_ selectedClass = classList.get(selectedClassPosition);
                    selectedClassId = selectedClass.getId();

                    for (Class_  class_ : classList){
                        if(class_.getId().equals(selectedClassId)){
                            class___ = class_;
                            break;
                        }
                    }


                }

                Student student = new Student(
                        class___.getId(),
                        studentName,
                        phoneNumber,
                        email,
                        selectedDate, isMale, isNormal, img,startSchool,endSchool,Double.parseDouble(gpa),class___
                );

                if(validateForm()){

                    if(type.equals("edit")){
                        student.setAvatar((String) image_student.getTag());
                        voidUpdate(student);
                    }else {
                        uploadStudent(student);
                    }
                }


                Log.d("STUDENT", "ok");
            }
        });

        onClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void voidUpdate(Student student){

        databaseManagerStudent.updateStudent(student.getPhoneNumber(), student)
                .addOnSuccessListener(aVoid -> {

                    databaseManagerClass.updateStudentInclClass(student.getClass_id(), student)
                            .addOnSuccessListener(aVoid1 -> {
                                // Update successful
                                Toast.makeText(getContext(),"UpdateStudentInClass, DocumentSnapshot successfully updated!", Toast.LENGTH_SHORT).show();
                                Log.d("UpdateStudentInClass", "DocumentSnapshot successfully updated!");
                            })
                            .addOnFailureListener(e -> {
                                Toast.makeText(getContext(),"Error Student in Class", Toast.LENGTH_SHORT).show();
                            });

                })
                .addOnFailureListener(e -> {
                    Toast.makeText(getContext(),"Error Student", Toast.LENGTH_SHORT).show();
                });

    }

    private void uploadStudent(Student student) {
        Task<Void> addStudentTask = databaseManagerStudent.addStudentToClassAndAddClassToStudent(student.getClass_().getId(), student);

        addStudentTask.addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                // Successfully added student to class and updated student document
                Toast.makeText(getContext(),"Upload Student Success", Toast.LENGTH_SHORT).show();
                Log.d("AddStudent", "Success");
            } else {
                // Handle the failure
                Exception exception = task.getException();
                if (exception != null) {
                    Log.e("AddStudent", "Error: " + exception.getMessage());
                    Toast.makeText(getContext(),"Upload Student Fail", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }


    private void openCameraOrGallery() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(requireActivity(),
                    new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    PERMISSION_REQUEST_CODE);
        } else {
            openCameraOrGalleryIntent();
        }
    }

    private void openCameraOrGalleryIntent() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Choose an option")
                .setItems(new CharSequence[]{"Chụp ảnh từ camera", "Lấy ảnh trong thư viện ảnh"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // The 'which' parameter indicates which item is clicked
                        switch (which) {
                            case 0:
                                // Camera option clicked
                                openCamera();
                                break;
                            case 1:
                                // Gallery option clicked
                                openGallery();
                                break;
                        }
                    }
                });

        builder.create().show();
    }

    private  void upload(Uri image){
        StorageReference storageReference1 = storageReference.child("image/" + UUID.randomUUID().toString());
        storageReference1.putFile(image).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(getContext(),"OK",Toast.LENGTH_SHORT).show();
                storageReference1.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri downloadUri) {
                        // Handle the download URL
                        String imageUrl = downloadUri.toString();
                        Log.d("URL", imageUrl);
                        text_img_student.setText(imageUrl);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Handle any errors getting the download URL
                        Toast.makeText(getContext(), "Error getting download URL", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(),"ERROR",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void openCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(requireContext().getPackageManager()) != null) {
            startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE);
        }
    }

    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, GALLERY_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == getActivity().RESULT_OK) {
            if (requestCode == CAMERA_REQUEST_CODE) {
                Bundle extras = data.getExtras();
                if (extras != null) {
                    Bitmap imageBitmap = (Bitmap) extras.get("data");
                    image_student.setImageBitmap(imageBitmap);
                    Uri imageUri = saveImageToGallery(imageBitmap);
                    // Now you can use 'imageUri' for uploading or any other purpose
                    upload(imageUri);
                }
            } else if (requestCode == GALLERY_REQUEST_CODE && data != null) {
                Uri selectedImageUri = data.getData();
                image_student.setImageURI(selectedImageUri);
                upload(selectedImageUri);
            }
        }
    }

    private Uri saveImageToGallery(Bitmap imageBitmap) {
        // You can customize the file path and name
        String filePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/your_app_images/";
        String fileName = "image_" + System.currentTimeMillis() + ".jpg";

        File file = new File(filePath, fileName);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }

        try {
            FileOutputStream fos = new FileOutputStream(file);
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Return the Uri of the saved image
        return Uri.fromFile(file);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                    grantResults[1] == PackageManager.PERMISSION_GRANTED &&
                    grantResults[2] == PackageManager.PERMISSION_GRANTED) {
                openCameraOrGalleryIntent();
            } else {
                Toast.makeText(requireContext(), "Permissions are required to open the camera or gallery", Toast.LENGTH_SHORT).show();
            }
        }
    }

}