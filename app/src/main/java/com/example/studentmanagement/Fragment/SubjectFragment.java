package com.example.studentmanagement.Fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.studentmanagement.Adapter.Student.CertificateAdapter;
import com.example.studentmanagement.Adapter.SubjectAdapter;
import com.example.studentmanagement.Models.Certificate;
import com.example.studentmanagement.Models.Class_;
import com.example.studentmanagement.Models.Subject;
import com.example.studentmanagement.R;
import com.example.studentmanagement.utils.DatabaseManagerSubject;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.apache.poi.ss.formula.functions.T;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SubjectFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SubjectFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int PERMISSION_REQUEST_CODE = 100;
    private static final int CAMERA_REQUEST_CODE = 101;
    private static final int GALLERY_REQUEST_CODE = 102;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView recyclerView;
    private SubjectAdapter subjectAdapter;
    private List<Subject> subjectList;
    private  String role;

    private FloatingActionButton menu_add_subject;

    StorageReference storageReference;
    Uri image;
    private  EditText linkImage;
    private EditText name;
    private ImageView imageViewSubject;

    private DatabaseManagerSubject databaseManagerSubject ;

    public SubjectFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SubjectFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SubjectFragment newInstance(String param1, String param2) {
        SubjectFragment fragment = new SubjectFragment();
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
        View view =  inflater.inflate(R.layout.fragment_subject, container, false);
        recyclerView = view.findViewById(R.id.subject_recycleview);

        FirebaseApp.initializeApp(getContext().getApplicationContext());
        storageReference = FirebaseStorage.getInstance().getReference();
        SharedPreferences preferences = requireContext().getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        role = preferences.getString("role", "");
        databaseManagerSubject =  new DatabaseManagerSubject();

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        subjectList = new ArrayList<>();
        subjectAdapter = new SubjectAdapter(subjectList, getContext());

        recyclerView.setAdapter(subjectAdapter);

        screateSampleSubjects();


        menu_add_subject = view.findViewById(R.id.menu_add_subject);

        if(role.equals("EMPLOYEE")){
            menu_add_subject.setVisibility(View.GONE);
        }else {
            menu_add_subject.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showAddEditCertificateDialog();
                }
            });
        }


        return view;
    }

    public void screateSampleSubjects() {

        Task<QuerySnapshot> getAllSubjectsTask = databaseManagerSubject.getAllSubjects();

// Add a listener to handle the result when the task is complete
        getAllSubjectsTask.addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                // The task was successful
                QuerySnapshot querySnapshot = task.getResult();

                subjectList.clear();

                // Now you can process the QuerySnapshot, for example, iterate through documents
                for (DocumentSnapshot document : querySnapshot.getDocuments()) {
                    Subject subject = document.toObject(Subject.class);
                    if (subject != null) {
                        subjectList.add(subject);
                    }
                }
                subjectAdapter.notifyDataSetChanged();
            } else {
                // Handle the error if the task was not successful
                Exception exception = task.getException();
                // Log or display an error message
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
                        linkImage.setText(imageUrl);
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
                    imageViewSubject.setImageBitmap(imageBitmap);
                    Uri imageUri = saveImageToGallery(imageBitmap);
                    // Now you can use 'imageUri' for uploading or any other purpose
                    image = imageUri;
                    upload(imageUri);
                }
            } else if (requestCode == GALLERY_REQUEST_CODE && data != null) {
                Uri selectedImageUri = data.getData();
                image = selectedImageUri;
                imageViewSubject.setImageURI(selectedImageUri);
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

    private String generateCustomPushId() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy-HH-mm-ss");
        LocalDateTime now = LocalDateTime.now();
        return formatter.format(now).toString().replace("-","");
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

    public void showAddEditCertificateDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_add_subject, null);
        builder.setView(dialogView);

        EditText name = dialogView.findViewById(R.id.name_subject_add);
        linkImage = dialogView.findViewById(R.id.image_subject_add);
        imageViewSubject = dialogView.findViewById(R.id.imageViewSubject);

        imageViewSubject.setOnClickListener(v -> openCameraOrGallery());


        builder.setTitle("Thêm Thông tin môn học!");


        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Subject subject = new Subject(name.getText().toString(),
                        generateCustomPushId(),
                        linkImage.getText().toString()
                );

                Log.d("Subject", subject.toString());
                add(subject);
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

    private  void add(Subject subject){




// Call the addSubject method to add the Subject to Firestore
        Task<Void> addSubjectTask = databaseManagerSubject.addSubject(subject);

// Add a listener to handle the result when the task is complete
        addSubjectTask.addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                subjectList.add(subject);
                int position = subjectList.size() - 1; // Get the position of the added item
                subjectAdapter.notifyItemInserted(position);

                Toast.makeText(getContext(),"Upload Subject Success", Toast.LENGTH_SHORT).show();;
            } else {
                // Handle the error if the task was not successful
                Exception exception = task.getException();
                Toast.makeText(getContext(),"Upload Subject Fail", Toast.LENGTH_SHORT).show();;
                // Log or display an error message
            }
        });
    }
}