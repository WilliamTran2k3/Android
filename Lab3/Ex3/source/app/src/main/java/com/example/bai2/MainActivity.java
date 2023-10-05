package com.example.bai2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    EditText editText;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        editText = findViewById(R.id.Link);
        button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = editText.getText().toString();
                if (!url.equals("")) {
                    // Tạo Intent để mở BrowserActivity
                    Intent browserActivityIntent = new Intent(MainActivity.this, BrowserActivity.class);

                    // Đặt dữ liệu (URL) vào Intent
                    browserActivityIntent.putExtra("url", url);

                    // Khởi chạy BrowserActivity
                    startActivity(browserActivityIntent);
                }
            }
        });
    }
}