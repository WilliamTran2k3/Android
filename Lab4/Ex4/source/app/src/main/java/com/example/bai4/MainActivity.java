package com.example.bai4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<GridViewItem> data;
    MyAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        data = new ArrayList<>();
        recyclerView.setLayoutManager(new GridLayoutManager(this,3));

        int elementNumber = new Random().nextInt(91) + 10;
        for (int i = 1; i <= elementNumber; i++) {
            data.add(new GridViewItem("PC" + i));
        }

        adapter = new MyAdapter(this, data);
        recyclerView.setAdapter(adapter);
    }
}