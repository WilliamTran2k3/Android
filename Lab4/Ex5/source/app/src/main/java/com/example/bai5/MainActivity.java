package com.example.bai5;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    TextView textView;
    Button add;
    Button remove;
    MyAdapter adapter;
    ArrayList<Item> data;
    int total;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        textView = findViewById(R.id.textView);
        add = findViewById(R.id.add5users);
        remove = findViewById(R.id.remove5users);
        data = new ArrayList<>();
        total = 0;

        adapter = new MyAdapter(this, data);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int total_add = total + 5;
                for (int i = total + 1; i <= total_add; i++) {
                    String name = "User " + i;
                    String email = "user"+i+"@tdtu.edu.vn";
                    data.add(new Item(name,email));
                }
                adapter.notifyDataSetChanged();
                total = total_add;
                textView.setText("Total users: "+total);
            }
        });

        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (total != 0) {
                    int total_remove = total - 5;
                    for (int i = total; i > total_remove; i--) {
                        data.remove(i-1);
                    }
                    adapter.notifyDataSetChanged();
                    total = total_remove;
                    textView.setText("Total users: "+total);
                }
                else {
                    Toast.makeText(MainActivity.this, "List of users is empty", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}