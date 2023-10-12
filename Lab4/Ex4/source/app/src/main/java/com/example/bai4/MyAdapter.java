package com.example.bai4;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private Context context;
    private static ArrayList<GridViewItem> data;

    public MyAdapter(Context context, ArrayList<GridViewItem> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.grid_view_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {
        holder.textView.setText(data.get(position).getString());
        if(!data.get(holder.getAdapterPosition()).isOn()) {
            holder.imageView.setImageResource(R.drawable.ic_baseline_computer_24_off);
        } else {
            holder.imageView.setImageResource(R.drawable.ic_baseline_computer_24);
        }
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = holder.getAdapterPosition();
                if (data.get(pos).isOn()) {
                    data.get(pos).setOn(false);
                    notifyItemChanged(pos);
                }
                else {
                    data.get(pos).setOn(true);
                    notifyItemChanged(pos);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            textView = itemView.findViewById(R.id.textView);
        }
    }
}
