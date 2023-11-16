package com.example.lab8_1;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerviewAdapter extends RecyclerView.Adapter<RecyclerviewAdapter.MyViewHolder> {
    ArrayList<Student> data;
    Context context;
    public RecyclerviewAdapter(ArrayList<Student> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Student d = data.get(position);
        holder.name.setText(data.get(position).getName());
        holder.email.setText(data.get(position).getEmail());
        holder.phone.setText(data.get(position).getPhone());
        // This is use for long click event
        holder.itemView.setOnCreateContextMenuListener((View.OnCreateContextMenuListener) context);
        Log.i("===From recyclerViewAdapter ===", d.toString());

        // This is for double click
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            private static final long DOUBLE_CLICK_TIME_DELTA = 300; // Adjust as needed
            private long lastClickTime = 0;
            @Override
            public void onClick(View view) {
                long clickTime = System.currentTimeMillis();
                if (clickTime - lastClickTime < DOUBLE_CLICK_TIME_DELTA) {
                    editEvent(holder.getAdapterPosition());
                }
                lastClickTime = clickTime;
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name, email, phone;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tv_name);
            email = itemView.findViewById(R.id.tv_email);
            phone = itemView.findViewById(R.id.tv_phone);
        }
    }
    private void editEvent(int position) {
        Student d = (Student) data.get(position);
        Intent intent = new Intent(context, MainActivity2.class);
        intent.putExtra("Method", 1); // Mark this is an edit method
        intent.putExtra("Id", String.valueOf(d.getId()));
        intent.putExtra("Name", d.getName());
        intent.putExtra("Email", d.getEmail());
        intent.putExtra("Phone", d.getPhone());
        context.startActivity(intent);
    }
}
