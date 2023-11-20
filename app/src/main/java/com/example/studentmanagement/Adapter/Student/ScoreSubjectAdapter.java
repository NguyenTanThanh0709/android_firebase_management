package com.example.studentmanagement.Adapter.Student;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentmanagement.Models.Certificate;
import com.example.studentmanagement.Models.ScoreSubject;
import com.example.studentmanagement.R;

import java.util.ArrayList;
import java.util.List;

public class ScoreSubjectAdapter extends  RecyclerView.Adapter<ScoreSubjectAdapter.ScoreSubjecHolder>{

    private List<ScoreSubject> list;
    private Context context;

    public ScoreSubjectAdapter(List<ScoreSubject> list, Context context) {
        this.list = list;
        this.context = context;
    }

    public ScoreSubjectAdapter() {
        list = new ArrayList<>();
    }

    public List<ScoreSubject> getList() {
        return list;
    }

    public void setList(List<ScoreSubject> list) {
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
    public ScoreSubjecHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.scoresubject_item,parent,false);
        return  new ScoreSubjectAdapter.ScoreSubjecHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ScoreSubjecHolder holder, int position) {
        ScoreSubject subject = list.get(position);

        holder.id_score.setText("Mã Môn đang học: " + subject.getId());
        holder.name_scoresubject.setText("Tên Môn học: " + subject.getSubject().getName());
        holder.score.setText("Điểm môn học: " + subject.getSocre());
        holder.startLearn.setText("Ngày bắt đầu học: " + subject.getStartLearn());

        holder.imageView_more_score.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(view, subject);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ScoreSubjecHolder extends RecyclerView.ViewHolder{

        private CardView carview_score;
        private TextView id_score;
        private TextView name_scoresubject;
        private TextView score;
        private TextView startLearn;
        private ImageView imageView_more_score;

        public ScoreSubjecHolder(@NonNull View itemView) {
            super(itemView);
            carview_score =itemView.findViewById(R.id.carview_score);

            id_score = itemView.findViewById(R.id.id_score);
            name_scoresubject = itemView.findViewById(R.id.name_scoresubject);
            score = itemView.findViewById(R.id.score);
            startLearn = itemView.findViewById(R.id.startLearn);
            imageView_more_score = itemView.findViewById(R.id.imageView_more_score);

        }
    }

    private void showPopupMenu(View view,  ScoreSubject subject) {
        PopupMenu popupMenu = new PopupMenu(context, view);
        popupMenu.inflate(R.menu.menu_more_score); // Replace with your menu resource

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                int itemId = menuItem.getItemId();

                if (itemId == R.id.menu_edit_score) {
                    showAddEditCertificateDialog( subject);
                    return true;
                }else if (itemId == R.id.menu_delete_score) {
                    // Handle option 2
                    showDeleteConfirmationDialog();
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
        builder.setMessage("Are you sure you want to delete this Subject of Student?");
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

    public void showAddEditCertificateDialog(ScoreSubject subject) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.dialog_add_edit_score, null);
        builder.setView(dialogView);

        final EditText id = dialogView.findViewById(R.id.id_score_subject);
        final EditText name = dialogView.findViewById(R.id.name_score_subject);
        final EditText score = dialogView.findViewById(R.id.score_score_subject);
        final EditText day = dialogView.findViewById(R.id.daylearn_score_subject);


        // Set values from the certificate parameter to the EditText fields if needed

        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Handle save action, you can get values from EditText fields
                // Add your logic for saving the data or updating the certificate
                // Notify the adapter that the dataset has changed if needed
                notifyDataSetChanged();
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


}
