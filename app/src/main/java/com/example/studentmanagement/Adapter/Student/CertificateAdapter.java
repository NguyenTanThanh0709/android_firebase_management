package com.example.studentmanagement.Adapter.Student;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
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

import com.example.studentmanagement.Activity.StudentActivity;
import com.example.studentmanagement.Activity.WebViewActivity;
import com.example.studentmanagement.Fragment.student.CertificateFragment;
import com.example.studentmanagement.Models.Certificate;
import com.example.studentmanagement.Models.Student;
import com.example.studentmanagement.R;
import com.example.studentmanagement.Repository.CertificateClickListener;

import java.util.ArrayList;
import java.util.List;

public class CertificateAdapter extends  RecyclerView.Adapter<CertificateAdapter.CertificateHolder>{

    private List<Certificate> list;
    private Context context;



    public CertificateAdapter() {
        list = new ArrayList<>();
    }

    public CertificateAdapter(List<Certificate> list, Context context) {
        this.list = list;
        this.context = context;
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
        builder.setMessage("Are you sure you want to delete this Certificate?");
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

        // Set values from the certificate parameter to the EditText fields if needed

        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Handle save action, you can get values from EditText fields
                String startCertificate = editTextStartCertificate.getText().toString();
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

    private void openLinkInApp(String url) {
        // You can handle the click event here, for example, by navigating to a new activity
        // or fragment to display the web content within the app.

        // For example, assuming you have a WebViewActivity:
        Intent webViewIntent = new Intent(context, WebViewActivity.class);
        webViewIntent.putExtra("url", url);
        context.startActivity(webViewIntent);
    }

}
