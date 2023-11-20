package com.example.studentmanagement.Adapter.Student;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentmanagement.Activity.EmployeeActivity;
import com.example.studentmanagement.Adapter.User.UserAdapter;
import com.example.studentmanagement.Models.Student;
import com.example.studentmanagement.Models.User;
import com.example.studentmanagement.R;
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

        Picasso.get()
                .load(user.getAvatar())
                .placeholder(R.drawable.user)
                .error(R.drawable.user)
                .into(holder.imageView);
        holder.birth.setText("Ngày sinh: " + user.getBirthDay());
        holder.name.setText("Tên: " + user.getName());
        holder.phone.setText("SĐT: " + user.getPhoneNumber());
        holder.class_.setText("Lớp: "+user.getClass_().getName());
        holder.startlearnyearn_student.setText("Năm học: " +user.getStartSchool());
        holder.imageView_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(view);
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
        private TextView startlearnyearn_student;
        private ImageView imageView_more;

        public StudentHolder(@NonNull View itemView) {
            super(itemView);
            cardView =itemView.findViewById(R.id.carview_student);
            imageView = itemView.findViewById(R.id.image_student);

            name = itemView.findViewById(R.id.name_student);
            phone = itemView.findViewById(R.id.phone_student);
            birth = itemView.findViewById(R.id.birth_student);
            class_ = itemView.findViewById(R.id.class_student);
            startlearnyearn_student = itemView.findViewById(R.id.startlearnyearn_student);

            imageView_more = itemView.findViewById(R.id.imageView_more_student);

        }
    }

    private void showPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(context, view);
        popupMenu.inflate(R.menu.menu_more_student); // Replace with your menu resource

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                int itemId = menuItem.getItemId();

                if (itemId == R.id.menu_sedetail_student) {
                    // Handle option 1

                    return true;
                } else if (itemId == R.id.menu_edit_student) {
                    // Handle option 2

                    return true;
                }else if (itemId == R.id.menu_delete_student) {
                    // Handle option 2
                    showDeleteConfirmationDialog();
                    return true;
                }else if (itemId == R.id.menu_certificate_student) {
                    // Handle option 2
                    return true;
                } else {
                    // Add more conditions for each menu item
                    return false;
                }
            }
        });

        popupMenu.show();
    }

    private void showDeleteConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Confirm Delete");
        builder.setMessage("Are you sure you want to delete this student?");
        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Handle the delete action here
                // You can call a method to delete the employee or perform any other action
                // For example: deleteEmployee();
            }
        });
        builder.setNegativeButton("Cancel", null);
        builder.show();
    }
}
