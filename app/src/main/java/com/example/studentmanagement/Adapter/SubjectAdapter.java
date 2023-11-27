package com.example.studentmanagement.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentmanagement.Models.Subject;
import com.example.studentmanagement.R;
import com.example.studentmanagement.utils.DatabaseManagerSubject;
import com.google.android.gms.tasks.Task;
import com.squareup.picasso.Picasso;

import org.apache.poi.ss.formula.functions.T;

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

        Picasso.with(context)
                .load(subject.getImg())
                .placeholder(R.mipmap.ic_launcher)
                .error(R.drawable.user)
                .into(holder.imgImageView);


        holder.imageView_more_subject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDeleteConfirmationDialog(subject.getId());
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

    public void removeItem(String id) {
        int position = findPositionByEmail(id);
        if (position != -1) {
            list.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, getItemCount());
        }
    }

    private int findPositionByEmail(String email) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getId().equals(email)) {
                return i;
            }
        }
        return -1;
    }

    private  void deleteSubject(String id){
        // Create an instance of DatabaseManagerSubject
        DatabaseManagerSubject databaseManagerSubject = new DatabaseManagerSubject();

        Task<Void> deleteSubjectTask = databaseManagerSubject.deleteSubject(id);

        deleteSubjectTask.addOnCompleteListener(task -> {
            if (task.isSuccessful()) {


                removeItem(id);
                System.out.println("Subject deleted successfully!");
                Toast.makeText(getContext(),"Subject deleted successfully!", Toast.LENGTH_SHORT).show();

            } else {
                // Handle the error if the task was not successful
                Exception exception = task.getException();
                // Log or display an error message
                Toast.makeText(getContext(),"Delete Subject Fail", Toast.LENGTH_SHORT).show();
                System.err.println("Error deleting subject: " + exception.getMessage());
            }
        });

    }

    private void showDeleteConfirmationDialog(String id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Confirm Delete");
        builder.setMessage("Are you sure you want to delete this Subject?");
        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteSubject(id);
            }
        });
        builder.setNegativeButton("Cancel", null);
        builder.show();
    }

}
