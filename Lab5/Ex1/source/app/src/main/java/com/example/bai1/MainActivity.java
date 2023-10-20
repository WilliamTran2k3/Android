package com.example.bai1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private PhoneAdapter adapter;
    private List<Phone> phoneList;

    private boolean isCheck = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        listView = findViewById(R.id.listView);

        phoneList = new ArrayList<>();
        phoneList.add(new Phone("Apple"));
        phoneList.add(new Phone("Xiaomi"));
        phoneList.add(new Phone("Nokia"));
        phoneList.add(new Phone("Oppo"));
        phoneList.add(new Phone("Samsung"));

        adapter = new PhoneAdapter(this, R.layout.list_item, phoneList);
        listView.setAdapter(adapter);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.check) {
            handleCheckUncheck(item);
            return true;
        } else if(id == R.id.delAll) {
            handleDeleteAll();
            return true;
        } else if(id == R.id.delSelected) {
            handleDeleteSelected();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void handleCheckUncheck(MenuItem item) {
        // Set lại trạng thái isCheck
        isCheck = !isCheck;
        // Nếu đang check thì đổi text thành Uncheck và nếu đang Uncheck thì đổi thành Check
        if(isCheck) {
            item.setTitle("Uncheck all");
        } else {
            item.setTitle("Check all");
        }
        // Đổi trạng thái check của các phone trong list
        for(Phone phone : phoneList) {
            phone.setChecked(isCheck);
        }
        // Thông báo cho adapter
        adapter.notifyDataSetChanged();
    }
    private void handleDeleteAll() {
        // Xóa hết các item trong list
        phoneList.clear();
        adapter.notifyDataSetChanged();
    }

    private void handleDeleteSelected() {
        // Danh sách các item check
        List<Phone> checkList = new ArrayList<>();
        for(Phone phone : phoneList) {
            if(phone.isChecked()) {
                checkList.add(phone);
            }
        }
        phoneList.removeAll(checkList);
        adapter.notifyDataSetChanged();
    }
}