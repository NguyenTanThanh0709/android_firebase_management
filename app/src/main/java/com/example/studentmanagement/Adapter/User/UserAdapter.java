package com.example.studentmanagement.Adapter.User;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentmanagement.Models.User;
import com.example.studentmanagement.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class UserAdapter extends  RecyclerView.Adapter<UserAdapter.UserHolder> {
    private List<User> list;

    public List<User> getList() {
        return list;
    }

    public void setList(List<User> list) {
        this.list = list;
    }

    public UserAdapter(List<User> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public UserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item,parent,false);
        return  new UserHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserHolder holder, int position) {
        User user =     list.get(position);
        // Use Picasso to load the image into the ImageView
        Picasso.get().load(user.getAvatar()).into(holder.imageView);
        holder.email.setText(user.getEmail());
        holder.name.setText(user.getName());
        holder.role.setText(user.getRole().toString());

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
}
