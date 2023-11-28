package com.example.studentmanagement.Adapter.Student;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentmanagement.Activity.WebViewActivity;
import com.example.studentmanagement.Models.Certificate;
import com.example.studentmanagement.Models.ScoreSubject;
import com.example.studentmanagement.R;
import com.example.studentmanagement.utils.DatabaseManagerStudent;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.List;

public class CertificateAdapter extends  RecyclerView.Adapter<CertificateAdapter.CertificateHolder>{

    private List<Certificate> list;
    private Context context;
    private String  phone;
    private DatabaseManagerStudent databaseManagerStudent;


    public CertificateAdapter() {
        list = new ArrayList<>();
    }

    public CertificateAdapter(List<Certificate> list, Context context, String phone) {
        this.list = list;
        this.context = context;
        this.phone = phone;
        databaseManagerStudent = new DatabaseManagerStudent();
    }

    public List<Certificate> getList() {
        return list;
    }

    public void setList(List<Certificate> list) {
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
    public CertificateHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.certificate_item,parent,false);
        return  new CertificateAdapter.CertificateHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CertificateHolder holder, int position) {
        Certificate certificate = list.get(position);
        holder.name_certificate.setText("Tên chứng chỉ: " + certificate.getName());
        holder.start_certificate.setText("Ngày nhận chứng chỉ: " + certificate.getStartCertificate());
        holder.end_Certificate.setText("Ngày hết hạn của chứng chỉ: " + certificate.getEndCertificate());
        holder.overalScore_cerficate.setText("Overall score: "+certificate.getOveralScore());
        holder.link_certificate.setText(certificate.getLink());

        holder.link_certificate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLinkInApp(certificate.getLink());
            }
        });



        holder.imageView_more_certificate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(view, certificate);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class CertificateHolder extends RecyclerView.ViewHolder{

        private CardView carview_certificate;
        private TextView name_certificate;
        private TextView start_certificate;
        private TextView end_Certificate;
        private TextView overalScore_cerficate;
        private TextView link_certificate;
        private ImageView imageView_more_certificate;

        public CertificateHolder(@NonNull View itemView) {
            super(itemView);
            carview_certificate =itemView.findViewById(R.id.carview_certificate);

            name_certificate = itemView.findViewById(R.id.name_certificate);
            start_certificate = itemView.findViewById(R.id.start_certificate);
            end_Certificate = itemView.findViewById(R.id.end_Certificate);
            overalScore_cerficate = itemView.findViewById(R.id.overalScore_cerficate);
            link_certificate = itemView.findViewById(R.id.link_certificate);

            imageView_more_certificate = itemView.findViewById(R.id.imageView_more_certificate);

        }
    }

    private void showPopupMenu(View view,  Certificate certificate) {
        PopupMenu popupMenu = new PopupMenu(context, view);
        popupMenu.inflate(R.menu.menu_more_certificate); // Replace with your menu resource

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                int itemId = menuItem.getItemId();

                 if (itemId == R.id.menu_edit_cer) {
                     showAddEditCertificateDialog(certificate);
                     return true;
                }else if (itemId == R.id.menu_delete_cer) {
                    // Handle option 2
                    showDeleteConfirmationDialog(certificate.getId());
                    return true;
                } else {
                    // Add more conditions for each menu item
                    return false;
                }
            }
        });

        popupMenu.show();
    }

    private void showDeleteConfirmationDialog(String id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Confirm Delete");
        builder.setMessage("Are you sure you want to delete this Certificate?");
        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Delete(id);
            }
        });
        builder.setNegativeButton("Cancel", null);
        builder.show();
    }

    private void Delete(String id) {
        Task<Void> deleteCertificateTask = databaseManagerStudent.deleteCertificate(phone, id);

// Handle the task result as needed
        deleteCertificateTask.addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(getContext(),"certificate deleted successfully",Toast.LENGTH_SHORT).show();
                for (java.util.Iterator<Certificate> iterator = list.iterator(); iterator.hasNext();) {
                    Certificate subject = iterator.next();
                    if (subject.getId().equals(id)) {
                        iterator.remove(); // Use iterator's remove method to avoid ConcurrentModificationException
                    }
                }
                notifyDataSetChanged();
            } else {
                // Handle the exception
                Exception exception = task.getException();
                if (exception != null) {
                    exception.printStackTrace();
                    Toast.makeText(getContext(),"certificate delete Fail",Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    public void showAddEditCertificateDialog(Certificate certificate) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.dialog_add_edit_certificate, null);
        builder.setView(dialogView);

        final EditText editTextCertificateName = dialogView.findViewById(R.id.editTextCertificateName);
        final EditText editTextStartCertificate = dialogView.findViewById(R.id.editTextStartCertificate);
        final EditText editTextEndCertificate = dialogView.findViewById(R.id.editTextEndCertificate);
        final EditText editTextOverallScore = dialogView.findViewById(R.id.editTextOverallScore);
        final EditText editTextDescribe = dialogView.findViewById(R.id.editTextDescribe);
        final EditText editTextLink = dialogView.findViewById(R.id.editTextLink);

        if (certificate != null) {
            editTextCertificateName.setText(certificate.getName());
            editTextStartCertificate.setText(certificate.getStartCertificate());
            editTextEndCertificate.setText(certificate.getEndCertificate());
            editTextOverallScore.setText(String.valueOf(certificate.getOveralScore()));
            editTextDescribe.setText(certificate.getDescribe());
            editTextLink.setText(certificate.getLink());
        }

        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String certificateName = editTextCertificateName.getText().toString();
                String startCertificate = editTextStartCertificate.getText().toString();
                String endCertificate = editTextEndCertificate.getText().toString();
                double overallScore = Double.parseDouble(editTextOverallScore.getText().toString());
                String describe = editTextDescribe.getText().toString();
                String link = editTextLink.getText().toString();

                Certificate updatedCertificate = new Certificate(certificate.getId(), certificateName, startCertificate, endCertificate, overallScore, describe, link);

                updateCertificate(updatedCertificate);
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

    private void updateCertificate(Certificate updatedCertificate) {
// Call the updateCertificate method
        Task<Void> updateCertificateTask = databaseManagerStudent.updateCertificate(phone, updatedCertificate.getId(), updatedCertificate);

// Handle the task result as needed
        updateCertificateTask.addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                // The certificate was updated successfully
                for (int i = 0; i < list.size(); i++) {
                    Certificate certificate = list.get(i);
                    if (certificate.getId().equals(updatedCertificate.getId())) {
                        // Update the certificate in the list
                        list.set(i, updatedCertificate);

                        // Notify the adapter that the data has changed
                        notifyDataSetChanged();
                        break; // Exit the loop since we found the certificate
                    }
                }
                Toast.makeText(getContext(),"certificate updated successfully",Toast.LENGTH_SHORT).show();

                System.out.println("Certificate updated successfully");
            } else {
                // Handle the exception
                Exception exception = task.getException();
                if (exception != null) {
                    exception.printStackTrace();
                    Toast.makeText(getContext(),"certificate updated ERROR",Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    private void openLinkInApp(String url) {
        // You can handle the click event here, for example, by navigating to a new activity
        // or fragment to display the web content within the app.

        // For example, assuming you have a WebViewActivity:
        Intent webViewIntent = new Intent(context, WebViewActivity.class);
        webViewIntent.putExtra("url", url);
        context.startActivity(webViewIntent);
    }



}
