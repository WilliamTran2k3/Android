package com.example.lab7_2;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;

public class MediaAdapter extends RecyclerView.Adapter<MediaAdapter.MyViewHolder> {
    ArrayList<MediaData> data;
    Context context;

    public MediaAdapter(ArrayList<MediaData> data, Context context) {
        this.data = data;
        this.context = context;
        Log.i("===ADAPTER DATA ===", data.toString());
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_layout, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        Log.i("=== ADAPTER HOLDER ===", String.valueOf(holder));
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        MediaData d = data.get(position);
        Log.i("===FROM ADAPTER===", d.toString());
        if  (d.getVideo()){
            holder.nameVid.setText(d.getName());
            holder.dateVid.setText(d.getDate());
            holder.video.setVideoURI(Uri.parse(d.getPath()));
            holder.video.start();
            CardView layout = holder.itemView.findViewById(R.id.card_image);
            layout.setVisibility(View.GONE);
        }
        else {
            holder.name.setText(d.getName());
            holder.date.setText(d.getDate());
            Bitmap imgBitmap = BitmapFactory.decodeFile(d.getPath());
            holder.img.setImageBitmap(imgBitmap);
            CardView layout = holder.itemView.findViewById(R.id.card_video);
            layout.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView name, date, nameVid, dateVid;
        CheckBox cb, cb2;
        ImageView img;
        VideoView video;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            Log.i("=== ENTER MYVIEWHOLDER", itemView.toString());
            name = itemView.findViewById(R.id.tv_name);
            date = itemView.findViewById(R.id.tv_date);
            nameVid = itemView.findViewById(R.id.name_vid);
            dateVid = itemView.findViewById(R.id.date_vid);
            img = (ImageView) itemView.findViewById(R.id.img_pic);
            video = (VideoView) itemView.findViewById(R.id.videoView);
            cb = itemView.findViewById(R.id.cb_delete);
            cb2 = itemView.findViewById(R.id.cb2_delete);

            Log.i("=== find name===", name.toString());
            // Set event for checkbox on change
            cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                    MediaData d = data.get(getPosition());
                    d.setChecked(isChecked);
                }
            });
            cb2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                    MediaData d = data.get(getPosition());
                    d.setChecked(isChecked);
                }
            });
        }
    }

    public void removeAt(int i){
        File fileOrFolder = new File (data.get(i).getPath());
        if (fileOrFolder.exists()) {
            boolean isDelete = fileOrFolder.delete();
            Log.i("=== FILE EXIST ===", String.valueOf(isDelete));
        }
        data.remove(i);
        notifyItemRemoved(i);
        notifyItemRangeChanged(i, data.size());
    }

    public void removeAll(){
        for (MediaData d :data) {
            File f = new File(d.getPath());
            f.delete();
        }
        int size = data.size();
        data.clear();
        notifyItemRangeChanged(0, size);
    }

    public void removeSelected(){
        int i=0;
        while(i<data.size()){
            if(data.get(i).getChecked()){
                removeAt(i);
            }
            else i++;
        }

    }
}
