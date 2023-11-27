package com.example.studentmanagement.Adapter.Student;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentmanagement.Activity.StudentActivity;
import com.example.studentmanagement.Models.Student;
import com.example.studentmanagement.R;
import com.example.studentmanagement.utils.DatabaseManagerClass;
import com.example.studentmanagement.utils.DatabaseManagerStudent;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class StudentAdapter extends  RecyclerView.Adapter<StudentAdapter.StudentHolder>{

    private List<Student> list;
    private Context context;

    public StudentAdapter(List<Student> list, Context context) {
        this.list = list;
        this.context = context;
    }

    public StudentAdapter() {
        list = new ArrayList<>();
    }

    public List<Student> getList() {
        return list;
    }

    public void setList(List<Student> list) {
        this.list = list;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public StudentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_item,parent,false);
        return  new StudentAdapter.StudentHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentHolder holder, int position) {
        Student user =  list.get(position);

        Picasso.with(context)
                .load(user.getAvatar())
                .placeholder(R.mipmap.ic_launcher)
                .error(R.drawable.user)
                .into(holder.imageView);

        holder.birth.setText("Ngày sinh: " + user.getBirthDay());

        holder.name.setText("Tên: " + user.getName());

        holder.phone.setText("SĐT: " + user.getPhoneNumber());

        holder.gpa_student.setText("GPA: " + user.getGPA());

        holder.class_.setText("Lớp: "+user.getClass_().getName());
        holder.startlearnyearn_student.setText("Năm học: " +user.getStartSchool());
        if(user.getSex()){
            holder.sex_student.setText("Giới tính: Nam");
        }else {
            holder.sex_student.setText("Giới tính: Nữ");
        }

        if(user.getStatus()){
            holder.sex_student.setText("Trạng thái: còn học");
        }else {
            holder.sex_student.setText("Trạng thái: thôi học");
        }
        holder.imageView_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(view, user.getPhoneNumber(), user.getClass_().getId());
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class StudentHolder extends RecyclerView.ViewHolder{

        private CardView cardView;
        private ImageView imageView;
        private TextView name;
        private TextView phone;
        private TextView class_;
        private TextView birth;
        private TextView sex_student;
        private TextView gpa_student;
        private TextView startlearnyearn_student;
        private ImageView imageView_more;

        public StudentHolder(@NonNull View itemView) {
            super(itemView);
            cardView =itemView.findViewById(R.id.carview_student);
            imageView = itemView.findViewById(R.id.image_student);

            name = itemView.findViewById(R.id.name_student);
            gpa_student = itemView.findViewById(R.id.gpa_student);
            phone = itemView.findViewById(R.id.phone_student);
            birth = itemView.findViewById(R.id.birth_student);
            class_ = itemView.findViewById(R.id.class_student);
            sex_student = itemView.findViewById(R.id.sex_student);
            startlearnyearn_student = itemView.findViewById(R.id.startlearnyearn_student);

            imageView_more = itemView.findViewById(R.id.imageView_more_student);

        }
    }

    private void showPopupMenu(View view, String phone, String idclass) {
        PopupMenu popupMenu = new PopupMenu(context, view);
        popupMenu.inflate(R.menu.menu_more_student); // Replace with your menu resource

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                int itemId = menuItem.getItemId();

                if (itemId == R.id.menu_sedetail_student) {
                    Intent intent = new Intent(context, StudentActivity.class);
                    intent.putExtra("phone", phone); // Truyền dữ liệu qua Intent
                    intent.putExtra("type", "see");
                    context.startActivity(intent);
                    return true;

                } else if (itemId == R.id.menu_edit_student) {
                    Intent intent = new Intent(context, StudentActivity.class);
                    intent.putExtra("phone", phone); // Truyền dữ liệu qua Intent
                    intent.putExtra("type", "edit");
                    context.startActivity(intent);
                    return true;

                }else if (itemId == R.id.menu_delete_student) {
                    // Handle option 2
                    showDeleteConfirmationDialog(phone, idclass);
                    return true;
                } else {
                    // Add more conditions for each menu item
                    return false;
                }
            }
        });

        popupMenu.show();
    }

    private void showDeleteConfirmationDialog(String phone, String idclass) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Confirm Delete");
        builder.setMessage("Are you sure you want to delete this student?");
        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteStudent(phone, idclass);
            }
        });
        builder.setNegativeButton("Cancel", null);
        builder.show();
    }

    private void deleteStudent(String phone, String idclass) {

        DatabaseManagerStudent databaseManagerStudent = new DatabaseManagerStudent();
        DatabaseManagerClass databaseManagerClass = new DatabaseManagerClass();


        databaseManagerStudent.deleteStudentById(phone)
                .addOnSuccessListener(aVoid -> {


                    databaseManagerClass.deleteStudentFromClass(idclass, phone)
                            .addOnSuccessListener(aVoid1 -> {
                                removeItem(phone);
                                Toast.makeText(getContext(),"DeleteStudentFromClass, Student document successfully deleted from class!",Toast.LENGTH_SHORT).show();
                            })
                            .addOnFailureListener(e -> {
                                Toast.makeText(getContext(),"DeleteStudentFromClass, Error deleting student document from class",Toast.LENGTH_SHORT).show();
                            });
                    // Deletion successful
                    Log.d("DeleteStudent", "Student document successfully deleted!");
                })
                .addOnFailureListener(e -> {
                    // Handle errors
                    Toast.makeText(getContext(),"DeleteStudent, Error deleting student document",Toast.LENGTH_SHORT).show();
                    Log.e("DeleteStudent", "Error deleting student document", e);
                });


    }

    public void removeItem(String email) {
        int position = findPositionByEmail(email);
        if (position != -1) {
            list.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, getItemCount());
        }
    }

    private int findPositionByEmail(String email) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getPhoneNumber().equals(email)) {
                return i;
            }
        }
        return -1;
    }

}
