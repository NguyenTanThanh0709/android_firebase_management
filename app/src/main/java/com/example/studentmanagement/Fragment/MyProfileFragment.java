package com.example.studentmanagement.Fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.studentmanagement.Models.User;
import com.example.studentmanagement.R;
import com.example.studentmanagement.utils.DatabaseManagerUser;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.google.firebase.FirebaseApp;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;


import org.apache.poi.ss.formula.functions.T;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.util.Calendar;
import java.util.UUID;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyProfileFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private ImageView imageViewCamera;
    private  ImageView imageViewAvatar;
    private EditText editTextName;
    private EditText editTextPhoneNumber;
    private EditText editTextBirthDay;
    private EditText editTextEmail;
    private EditText editTextStatus;
    private EditText editTextRole;
    private EditText editTextPassword;
    RadioGroup radioGroupSex ;
    RadioButton radioButtonMale ;
    RadioButton radioButtonFemale ;

    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int PERMISSION_REQUEST_CODE = 100;
    private static final int CAMERA_REQUEST_CODE = 101;
    private static final int GALLERY_REQUEST_CODE = 102;

    StorageReference storageReference;
    Uri image;



    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private User user;
    private DatabaseManagerUser databaseManagerUser;

    public MyProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MyProfileFragment newInstance(String param1, String param2) {
        MyProfileFragment fragment = new MyProfileFragment();
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
        View view = inflater.inflate(R.layout.fragment_my_profile, container, false);
        databaseManagerUser = new DatabaseManagerUser();
        FirebaseApp.initializeApp(getContext().getApplicationContext());
        storageReference = FirebaseStorage.getInstance().getReference();

        SharedPreferences preferences = requireContext().getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        String userJson = preferences.getString("user", "");



        imageViewAvatar = view.findViewById(R.id.imageViewAvatar);
        imageViewCamera = view.findViewById(R.id.imageViewCamera);
         editTextName = view.findViewById(R.id.editTextName);
         editTextPhoneNumber = view.findViewById(R.id.editTextPhoneNumber);
         editTextBirthDay = view.findViewById(R.id.editTextBirthDay);
         editTextEmail = view.findViewById(R.id.editTextEmail);
         editTextStatus = view.findViewById(R.id.editTextStatus);
         editTextRole = view.findViewById(R.id.editTextRole);
         editTextPassword = view.findViewById(R.id.editTextPassword);
         radioGroupSex = view.findViewById(R.id.radioGroupSex);
         radioButtonMale = view.findViewById(R.id.radioButtonMale);
         radioButtonFemale = view.findViewById(R.id.radioButtonFemale);
        imageViewCamera.setOnClickListener(v -> openCameraOrGallery());
        FillInfo(userJson);

        return view;
    }

    private void FillInfo(String userJson){
        if (!userJson.isEmpty()) {
            Gson gson = new Gson();
            user = gson.fromJson(userJson, User.class);

            Picasso.with(requireContext())
                    .load(user.getAvatar())
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.drawable.user)
                    .into(imageViewAvatar);


            editTextName.setText(user.getName());
            editTextPhoneNumber.setText(user.getPhoneNumber());
            editTextEmail.setText(user.getEmail());
            editTextBirthDay.setText(user.getBirthDay());
            if(user.getStatus()){
                editTextStatus.setText("Đang hoạt động trong trường");
            }else {
                editTextStatus.setText("Đã nghỉ phép");
            }
            editTextRole.setText(user.getRole().toString());
            editTextPassword.setText(user.getPassword());

            if (user.getSex() != null) {
                if (user.getSex()) {
                    // Male
                    radioButtonMale.setChecked(true);
                } else {
                    // Female
                    radioButtonFemale.setChecked(true);
                }
            }

        }
    }

    private void updateAvatar(String userId, String newAvatarUrl) {
        databaseManagerUser.updateAvatar(userId, newAvatarUrl)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(requireActivity(),"Update Avatar Success", Toast.LENGTH_SHORT).show();
                        user.setAvatar(newAvatarUrl);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(requireActivity(),"Update Avatar Fail", Toast.LENGTH_SHORT).show();

                    }
                });
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
                        updateAvatar(user.getEmail(),imageUrl);
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
                    imageViewAvatar.setImageBitmap(imageBitmap);
                    Uri imageUri = saveImageToGallery(imageBitmap);
                    // Now you can use 'imageUri' for uploading or any other purpose
                    upload(imageUri);
                }
            } else if (requestCode == GALLERY_REQUEST_CODE && data != null) {
                Uri selectedImageUri = data.getData();
                imageViewAvatar.setImageURI(selectedImageUri);
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