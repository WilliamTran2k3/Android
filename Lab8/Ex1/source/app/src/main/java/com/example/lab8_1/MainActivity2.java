package com.example.lab8_1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity2 extends AppCompatActivity {
    TextInputEditText tvId, tvName, tvEmail, tvPhone;
    Button btnSave;
    int method = -2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        // Connection:
        tvId = findViewById(R.id.edt_id);
        tvEmail = findViewById(R.id.edt_email);
        tvName = findViewById(R.id.edt_name);
        tvPhone = findViewById(R.id.edt_phone);
        btnSave = findViewById(R.id.btn_save);

        // Retrieve data:
        Intent i = getIntent();
        String id = i.getStringExtra("Id");
        Log.d("ID RETRIEVE:", id);
        method = i.getIntExtra("Method",-2);
        Log.d("METHOD RETRIEVE: ", String.valueOf(method));
        if (method==1){
            tvName.setText(i.getStringExtra("Name"));
            tvEmail.setText(i.getStringExtra("Email"));
            tvPhone.setText(i.getStringExtra("Phone"));
        }
        Log.d("Data retrieve: ", id);
        tvId.setText(id);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = Integer.parseInt(tvId.getText().toString());
                String name = tvName.getText().toString();
                String email = tvEmail.getText().toString();
                String phone = tvPhone.getText().toString();
                // Condition 1: fields can't be empty
                if (name.isEmpty() || email.isEmpty() || phone.isEmpty()){
                    Toast.makeText(MainActivity2.this, "Please enter all fields!", Toast.LENGTH_SHORT).show();
                }
                // Condition 2: Email field must be email
                else if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
                    Toast.makeText(MainActivity2.this, "Please enter right email!", Toast.LENGTH_SHORT).show();
                }
                // Condition 3: Phone must be 10 digits
                else if (phone.replaceAll("[^0-9]", "").length()!=10) {
                    Toast.makeText(MainActivity2.this, "Please enter right phone number that have 10 digits!", Toast.LENGTH_SHORT).show();
                } else {
                    methodStudent(id, name, email, phone);
                }
            }
        });

    }

    private void methodStudent(int id, String name, String email, String phone) {
        Log.d("=== ENTER ADD NEW STUDENT===", "");
        OkHttpClient client = new OkHttpClient();
        String createStudentURL="";
        if (method==-2){
            Log.d("===ENTER METHOD ADD", "");
            createStudentURL = "http://10.0.2.2:8080/api/add-student.php";
        }
        else if(method==1){
            Log.d("===ENTER METHOD EDIT", "");
            createStudentURL = "http://10.0.2.2:8080/api/update-student.php";
        }
//        String createStudentURL = "http://10.0.2.2:8080/api/add-student.php";
        RequestBody formBody = new FormBody.Builder()
                .add("id", String.valueOf(id))
                .add("name", name)
                .add("email", email)
                .add("phone", phone)
                .build();
        Request request = new Request.Builder()
                .url(createStudentURL)
                .post(formBody)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                try {
                    String responseData = response.body().string();
                    JSONObject json = new JSONObject(responseData);
                    Log.d("Post data success: " , responseData);
                    Intent i = new Intent(MainActivity2.this, MainActivity.class);
                    startActivity(i);
                } catch (JSONException e) {
                    Log.d("onResponse", e.getMessage());
                }
            }
        });
    }
}