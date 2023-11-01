package com.example.ex1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    TextView openCountText;
    Button saveButton;
    EditText textColorEditText, backgroundColorEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Tham chiếu đến các đối tượng ở View
        openCountText = findViewById(R.id.openCount_text);
        saveButton = findViewById(R.id.save_btn);
        textColorEditText = findViewById(R.id.color_edt);
        backgroundColorEditText = findViewById(R.id.background_edt);
        sharedPreferences = getSharedPreferences("Data", Context.MODE_PRIVATE);
        // Object hỗ trợ thay đổi giá trị
        editor = sharedPreferences.edit();
        // Lấy giá trị ra từ sharedPreferences nếu đã tồn tại
        // Ngược lại khởi điểm là 1
        int openCount = sharedPreferences.getInt("OpenCount", 1);
        // Màu mặc định nếu TextColor chưa tồn tại trong sharedPreferences
        String textColor = sharedPreferences.getString("TextColor", "#FFFFFF");
        // Màu nền mặc định
        String backgroundColor = sharedPreferences.getString("BackgroundColor", "#2222FF");

        // Set các giá trị cho các thành phần ở View
        openCountText.setText(openCount + "");
        // Chuyển đổi mã màu hex thành màu integer
        int color = Color.parseColor(textColor);
        openCountText.setTextColor(color);
        // Set text cho text color editText
        textColorEditText.setText(textColor);
        // Tương tự với background
        int colorBg = Color.parseColor(backgroundColor);
        openCountText.setBackgroundColor(colorBg);
        backgroundColorEditText.setText(backgroundColor);

        // Tạo sự kiện nhấn lưu
        saveButton.setOnClickListener(view -> {
            // Lấy giá trị màu chữ và background hiện tại
            String newTextColor = textColorEditText.getText().toString();
            String newBackgroundColor = backgroundColorEditText.getText().toString();
            // Thay đổi giá trị trong sharedPreferences
            editor.putString("TextColor", newTextColor);
            editor.putString("BackgroundColor", newBackgroundColor);
            // Lưu thay đổi
            editor.apply();
            // Cập nhật lại giao diện
            int updateColor = Color.parseColor(newTextColor);
            int updateBackground = Color.parseColor(newBackgroundColor);
            openCountText.setTextColor(updateColor);
            openCountText.setBackgroundColor(updateBackground);
        });

        // Tăng lên 1 để lần mở app tiếp theo thì giá trị tăng
        openCount++;
        // Set giá trị mới
        editor.putInt("OpenCount", openCount);
        // Lưu lại thông qua method apply()
        editor.apply();
    }
}