package com.example.bai1;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddContactActivity extends AppCompatActivity {

    EditText firstName, lastName, phoneNumber;
    Button saveBtn;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        getSupportActionBar().setTitle("My Contacts");
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        firstName = findViewById(R.id.edtxt_firstname);
        lastName = findViewById(R.id.edtxt_lastname);
        phoneNumber = findViewById(R.id.edtxt_phonenumber);
        saveBtn = findViewById(R.id.btn_save);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("firstname", firstName.getText().toString());
                intent.putExtra("lastname", lastName.getText().toString());
                intent.putExtra("phonenumber", phoneNumber.getText().toString());
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_2, menu);
        return super.onCreateOptionsMenu(menu);
    }
}