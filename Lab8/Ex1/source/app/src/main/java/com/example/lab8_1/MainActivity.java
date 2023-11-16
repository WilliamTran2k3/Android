package com.example.lab8_1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    ArrayList<Student> data = new ArrayList<>();
    RecyclerviewAdapter adapter;
    RecyclerView recyclerView;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Connection
        recyclerView = findViewById(R.id.recyclerview);

        adapter = new RecyclerviewAdapter(data,this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        loadStudent();
    }

    // Action menu bar
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_menu, menu);
        // Connect to button add
        MenuItem add = menu.findItem(R.id.action_add);
        return true;
    }

    // Add event
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_add){
            Intent intent = new Intent(this, MainActivity2.class);
            Log.d("Last index: ", String.valueOf(data.get(data.size()-1).getId()+1));
            intent.putExtra("Id", String.valueOf(data.get(data.size()-1).getId()+1));
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Create menu on long click
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
        this.position = recyclerView.getChildAdapterPosition(v);
    }

    // Create menu on long click
    @Override
    public boolean onContextItemSelected(MenuItem item){
        // Edit event
        if (item.getItemId() == R.id.action_edit) {
            editEvent();
        }
        // Delete event
        else if (item.getItemId() == R.id.action_delete) {
            deleteDialog();
        }
        return true;
    }

    private void deleteDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Deleting events");
        builder.setMessage("Are you sure you want to delete this student?");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                deleteEvent();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    private void deleteEvent() {
        Student d = (Student) data.get(this.position);
        OkHttpClient client = new OkHttpClient();
        String createStudentURL = "http://10.0.2.2:8080/api/delete-student.php";
        RequestBody formBody = new FormBody.Builder()
                .add("id", String.valueOf(d.getId()))
                .build();
        Request request = new Request.Builder()
                .url(createStudentURL)
                .post(formBody)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {}
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String responseData = response.body().string();
                Log.d("Delete data success: " , responseData);
            }
        });
        data.remove(position);
        adapter.notifyDataSetChanged();
    }

    private void editEvent() {
        Student d = (Student) data.get(this.position);
        Intent intent = new Intent(this, MainActivity2.class);
        intent.putExtra("Method", 1); // Mark this is an edit method
        intent.putExtra("Id", String.valueOf(d.getId()));
        intent.putExtra("Name", d.getName());
        intent.putExtra("Email", d.getEmail());
        intent.putExtra("Phone", d.getPhone());
        startActivity(intent);
    }

    private void loadStudent() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url("http://10.0.2.2:8080/api/get-students.php").build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.d("Load student: ", "Error: " + e.getMessage());
            }
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String content = response.body().string();
                Log.d("Load student success :" , content);
                try {
                    JSONObject root = new JSONObject(content);
                    boolean status = root.getBoolean("status");
                    if (status){
                        data.clear();
                        JSONArray arr = root.getJSONArray("data");
                        for (int i=0; i<arr.length(); i++){
                            JSONObject a = arr.getJSONObject(i);
                            int id = a.getInt("id");
                            String name = a.getString("name");
                            String email = a.getString("email");
                            String phone = a.getString("phone");
                            Student st = new Student(id,name,email,phone);
                            Log.d("Data in each loop: " , st.toString());
                            data.add(st);
                        }
                        adapter.notifyDataSetChanged();
                    }
                    else {
                        Toast.makeText(MainActivity.this, "Error while loading data", Toast.LENGTH_SHORT).show();
                    }
                }
                catch (JSONException e){
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}