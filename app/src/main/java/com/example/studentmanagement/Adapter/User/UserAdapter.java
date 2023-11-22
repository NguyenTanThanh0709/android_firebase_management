package com.example.studentmanagement.Adapter.User;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentmanagement.Activity.EmployeeActivity;
import com.example.studentmanagement.Models.User;
import com.example.studentmanagement.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class UserAdapter extends  RecyclerView.Adapter<UserAdapter.UserHolder> {
    private List<User> list;
    private Context context;

    public List<User> getList() {
        return list;
    }

    public void setList(List<User> list) {
        this.list = list;
    }

    public UserAdapter(List<User> list) {
        this.list = list;
    }

    public UserAdapter(List<User> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public UserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item,parent,false);
        return  new UserHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserHolder holder, int position) {
        User user =  list.get(position);
        // Use Picasso to load the image into the ImageView

        Picasso.with(context)
                .load(user.getAvatar())
                .placeholder(R.mipmap.ic_launcher)
                .error(R.drawable.user)
                .into(holder.imageView);


        holder.email.setText(user.getEmail());
        holder.name.setText(user.getName());
        holder.role.setText(user.getRole().toString());

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

    class UserHolder extends RecyclerView.ViewHolder{

        private CardView cardView;
        private ImageView imageView;
        private TextView name;
        private TextView email;
        private TextView role;
        private ImageView imageView_more;

        public UserHolder(@NonNull View itemView) {
            super(itemView);
            cardView =itemView.findViewById(R.id.carview_user);
            imageView = itemView.findViewById(R.id.image_user);
            name = itemView.findViewById(R.id.name_user);
            email = itemView.findViewById(R.id.email_user);
            role = itemView.findViewById(R.id.role_user);
            imageView_more = itemView.findViewById(R.id.imageView_more);

        }
    }

    private void showPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(context, view);
        popupMenu.inflate(R.menu.menu_more_employee); // Replace with your menu resource

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                int itemId = menuItem.getItemId();

                if (itemId == R.id.menu_sedetail_employee) {
                    // Handle option 1
                    Intent intent = new Intent(context, EmployeeActivity.class);
                    context.startActivity(intent);
                    return true;
                } else if (itemId == R.id.menu_edit_employee) {
                    // Handle option 2
                    Intent intent = new Intent(context, EmployeeActivity.class);
                    context.startActivity(intent);
                    return true;
                }else if (itemId == R.id.menu_delete_employee) {
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
        builder.setMessage("Are you sure you want to delete this employee?");
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
