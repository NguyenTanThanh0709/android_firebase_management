package com.example.studentmanagement.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentmanagement.Fragment.student.StudentFragment;
import com.example.studentmanagement.Models.Certificate;
import com.example.studentmanagement.Models.Class_;
import com.example.studentmanagement.Models.Subject;
import com.example.studentmanagement.R;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ClassAdapter extends RecyclerView.Adapter<ClassAdapter.ClassHolder>{

    private List<Class_> list;
    private Context context;

    public ClassAdapter() {
        list = new ArrayList<>();
    }

    public ClassAdapter(List<Class_> list, Context context) {
        this.list = list;
        this.context = context;
    }

    public List<Class_> getList() {
        return list;
    }

    public void setList(List<Class_> list) {
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
    public ClassHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.class_item,parent,false);
        return  new ClassAdapter.ClassHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClassHolder holder, int position) {
        Class_ class_ = list.get(position);

        holder.id_class.setText(class_.getId());
        holder.name_class.setText(class_.getName());

        holder.imageView_more_class.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(view, class_);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ClassHolder extends RecyclerView.ViewHolder{

        private CardView carview_class;
        private TextView id_class;
        private TextView name_class;
        private ImageView imageView_more_class;

        public ClassHolder(@NonNull View itemView) {
            super(itemView);
            carview_class =itemView.findViewById(R.id.carview_class);

            id_class = itemView.findViewById(R.id.id_class);
            name_class = itemView.findViewById(R.id.name_class);
            imageView_more_class = itemView.findViewById(R.id.imageView_more_class);

        }
    }

    private void showPopupMenu(View view,  Class_ class_) {
        PopupMenu popupMenu = new PopupMenu(context, view);
        popupMenu.inflate(R.menu.menu_more_class); // Replace with your menu resource

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                int itemId = menuItem.getItemId();

                if (itemId == R.id.menu_delete_class) {
                    return true;
                }else if (itemId == R.id.menu_students) {
                    // Handle option 2
                    navigateToStudentFragment(class_);
                    return true;
                } else {
                    // Add more conditions for each menu item
                    return false;
                }
            }
        });

        popupMenu.show();
    }

    private void navigateToStudentFragment(Class_ class_) {
        // Replace the code below with the logic to navigate to the StudentFragment
        // You might need to use the FragmentManager to replace the current fragment with the StudentFragment
        // For example:
        StudentFragment studentFragment = new StudentFragment();
        FragmentTransaction transaction = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_, studentFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
