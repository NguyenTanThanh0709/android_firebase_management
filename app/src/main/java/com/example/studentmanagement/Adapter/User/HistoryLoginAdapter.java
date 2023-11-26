package com.example.studentmanagement.Adapter.User;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentmanagement.Models.HistoryLogin;
import com.example.studentmanagement.Models.User;
import com.example.studentmanagement.R;

import java.util.ArrayList;
import java.util.List;

public class HistoryLoginAdapter extends RecyclerView.Adapter<HistoryLoginAdapter.HistoryLoginHolder>{

    List<HistoryLogin> list;
    private Context context;
    private String name;

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HistoryLoginAdapter(List<HistoryLogin> list, Context context) {
        this.list = list;
        this.context = context;
    }

    public HistoryLoginAdapter(List<HistoryLogin> list) {
        this.list = list;
    }

    public HistoryLoginAdapter() {
        list = new ArrayList<>();
    }

    public List<HistoryLogin> getList() {
        return list;
    }

    public void setList(List<HistoryLogin> list) {
        this.list = list;
    }



    @NonNull
    @Override
    public HistoryLoginHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.historylogin_item,parent,false);
        return  new HistoryLoginAdapter.HistoryLoginHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryLoginHolder holder, int position) {
        HistoryLogin user_ =  list.get(position);

        holder.name.setText(name);

        holder.idaddress.setText(user_.getIdAdress());
        holder.locate.setText(user_.getLocate());
        holder.startlogin.setText(user_.getStartLogin());
        holder.startlogout.setText(user_.getStartLogout());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class HistoryLoginHolder extends RecyclerView.ViewHolder{

        private CardView cardView;

        private TextView name;
        private TextView idaddress;
        private TextView locate;
        private TextView startlogin;
        private TextView startlogout;

        public HistoryLoginHolder(@NonNull View itemView) {
            super(itemView);
            cardView =itemView.findViewById(R.id.carview_historylogin);
            name = itemView.findViewById(R.id.name_user_his);
            idaddress = itemView.findViewById(R.id.idaddress);
            locate = itemView.findViewById(R.id.locate);
            startlogin = itemView.findViewById(R.id.startlogin);
            startlogout = itemView.findViewById(R.id.startlogout);

        }
    }
}
