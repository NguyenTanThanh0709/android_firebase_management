package com.example.studentmanagement.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.studentmanagement.Adapter.Student.ScoreSubjectAdapter;
import com.example.studentmanagement.Models.ScoreSubject;
import com.example.studentmanagement.Models.Subject;
import com.example.studentmanagement.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class SubjectAdapter extends RecyclerView.Adapter<SubjectAdapter.SubjectHolder>{

    private List<Subject> list;
    private Context context;

    public SubjectAdapter(List<Subject> list, Context context) {
        this.list = list;
        this.context = context;
    }

    public SubjectAdapter() {
        list = new ArrayList<>();
    }

    public List<Subject> getList() {
        return list;
    }

    public void setList(List<Subject> list) {
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
    public SubjectHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.subject_item,parent,false);
        return  new SubjectAdapter.SubjectHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull SubjectHolder holder, int position) {
        Subject subject= list.get(position);

        holder.idSubject.setText("Mã Môn Học: "+subject.getId());
        holder.nameSubject.setText("Tên Môn Học: " +subject.getName());

        Picasso.get()
                .load(subject.getImg())
                .placeholder(R.drawable.user)
                .error(R.drawable.user)
                .into(holder.imgImageView);

        holder.imageView_more_subject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDeleteConfirmationDialog();
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class SubjectHolder extends RecyclerView.ViewHolder{

        private CardView carview_subject;
        private TextView idSubject;
        private TextView nameSubject;
        private CircleImageView imgImageView;
        private ImageView imageView_more_subject;

        public SubjectHolder(@NonNull View itemView) {
            super(itemView);
            carview_subject =itemView.findViewById(R.id.carview_subject);

            idSubject = itemView.findViewById(R.id.idSubject);
            nameSubject = itemView.findViewById(R.id.nameSubject);
            imgImageView = itemView.findViewById(R.id.imgImageView);
            imageView_more_subject = itemView.findViewById(R.id.imageView_more_subject);



        }
    }

    private void showDeleteConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Confirm Delete");
        builder.setMessage("Are you sure you want to delete this Subject?");
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
