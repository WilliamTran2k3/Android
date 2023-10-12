package com.example.bai3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ArrayList<Item> itemList, selectedItem;
    private Button removeAll, removeSelected;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        removeAll = findViewById(R.id.removeAll);
        removeSelected = findViewById(R.id.removeSelected);

        itemList = new ArrayList<>();
        itemList.add(new Item("Apple"));
        itemList.add(new Item("Samsung"));
        itemList.add(new Item("Nokia"));
        itemList.add(new Item("Oppo"));

        selectedItem = new ArrayList<>();
        CustomAdapter adapter = new CustomAdapter(itemList, this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        removeAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemList.clear();
                adapter.notifyDataSetChanged();
            }
        });

        removeSelected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Item> itemsToRemove = new ArrayList<>();

                // Duyệt qua danh sách itemList
                for (Item item : itemList) {
                    if (item.getCheck()) {
                        // Nếu thuộc tính 'check' là true, thêm phần tử vào danh sách itemsToRemove
                        itemsToRemove.add(item);
                    }
                }

                // Loại bỏ các phần tử từ itemList
                itemList.removeAll(itemsToRemove);
                adapter.notifyDataSetChanged();
            }
        });
    }

}