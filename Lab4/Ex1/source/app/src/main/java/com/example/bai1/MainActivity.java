package com.example.bai1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<Item> itemList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        // Tạo ngẫu nhiên danh sách gồm 5-30 phần tử
        itemList = generateArrayList(5, 30);

        CustomAdapter adapter = new CustomAdapter(itemList, this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
    }

    private ArrayList<Item> generateArrayList(int min, int max) {
        Random random = new Random();
        int numItem = random.nextInt((max - min) + 1) + min;
        ArrayList<Item> arrayList = new ArrayList<>();
        for(int i = 1; i <= numItem; i++) {
            arrayList.add(new Item("Item" + i));
        }
        return arrayList;
    }
}