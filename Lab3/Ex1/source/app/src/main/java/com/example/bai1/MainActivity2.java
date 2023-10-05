package com.example.bai1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity2 extends AppCompatActivity {

    TextView textView;
    EditText editText;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        textView = findViewById(R.id.textView2);
        editText = findViewById(R.id.Name);
        button = findViewById(R.id.luuvathoat);

        Intent intent = getIntent();
        String email = intent.getStringExtra("email");
        textView.setText("Xin chào, "+email+". Vui lòng nhập tên");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!editText.getText().toString().equals("")) {
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("name", editText.getText().toString());
                    setResult(RESULT_OK, resultIntent);
                    finish();
                }
            }
        });
    }
}