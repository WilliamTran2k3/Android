package com.example.bai3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    private ArrayList<Item> itemList;
    private Context context;

    public CustomAdapter(ArrayList<Item> itemList, Context context) {
        this.itemList = itemList;
        this.context = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public CheckBox checkBox;
        public ViewHolder(View view) {
            super(view);
            textView = view.findViewById(R.id.textView);
            checkBox = view.findViewById(R.id.checkBox);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Item item = itemList.get(position);
        holder.textView.setText(item.getText());
        if(item.getCheck()) {
            holder.checkBox.setChecked(true);
        } else {
            holder.checkBox.setChecked(false);
        }
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(item.getCheck()) {
                    item.setCheck(false);
                } else {
                    item.setCheck(true);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }
}