package com.example.ex2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    private static final String INTERNAL_FILE_NAME = "internal_file.txt";

    private static final String EXTERNAL_FILE_NAME = "external_file.txt";

    private Button btnReadInternal;
    private Button btnReadExternal;
    private Button btnWriteInternal;
    private Button btnWriteExternal;
    private EditText edtContent;
    private String mySdPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Tham chiếu đến các object bên view
        btnReadInternal = findViewById(R.id.btnReadInternal);
        btnReadExternal = findViewById(R.id.btnReadExternal);
        btnWriteInternal = findViewById(R.id.btnWriteInternal);
        btnWriteExternal = findViewById(R.id.btnWriteExternal);
        edtContent = findViewById(R.id.edtContent);
        // Đường dẫn đến file external
        mySdPath = Environment.getExternalStorageDirectory().getAbsolutePath();
        // Gán sự kiện onclick cho các nút
        btnReadInternal.setOnClickListener(v -> handleClick(v));
        btnReadExternal.setOnClickListener(v -> handleClick(v));
        btnWriteInternal.setOnClickListener(v -> handleClick(v));
        btnWriteExternal.setOnClickListener(v -> handleClick(v));

    }
    // Hàm xử lý sự kiện click cho các button
    private void handleClick(View v) {
        int id = v.getId();
        if (id == R.id.btnWriteInternal) {
            try {
                writeInternalFileContent();
                Toast.makeText(getBaseContext(), "Ghi internal file thành công: "
                                + INTERNAL_FILE_NAME,
                        Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        } else if(id == R.id.btnReadInternal) {
            try {
                String internalFileContent = readInternalFileContent();
                edtContent.setText(internalFileContent);
                Toast.makeText(getApplicationContext(), "Đọc internal file thành công",
                                Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                Toast.makeText(getApplicationContext(), e.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        } else if(id == R.id.btnWriteExternal) {
            try {
                writeExternalFile();
                Toast.makeText(getBaseContext(), "Ghi external file thành công: "
                                + EXTERNAL_FILE_NAME,
                        Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                Log.e("ERROR", e.getMessage());
                Toast.makeText(getBaseContext(), e.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        } else if(id == R.id.btnReadExternal) {
            try {
                String externalFileContent = readExternalFileContent();
                edtContent.setText(externalFileContent);
                Toast.makeText(getApplicationContext(), "Đọc external file thành công "
                                + EXTERNAL_FILE_NAME,
                        Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                Log.e("ERROR", e.getMessage());
                Toast.makeText(getApplicationContext(), e.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    private String readExternalFileContent() throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader myReader = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(
                                new File(mySdPath + "/" + EXTERNAL_FILE_NAME))));

        String aDataRow = "";
        while ((aDataRow = myReader.readLine()) != null) {
            stringBuilder.append(aDataRow).append("\n");
        }
        myReader.close();
        return stringBuilder.toString();
    }

    private void writeInternalFileContent() throws IOException {
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(
                openFileOutput(INTERNAL_FILE_NAME, 0));
        outputStreamWriter.write(edtContent.getText().toString());
        outputStreamWriter.close();
    }

    private void writeExternalFile() throws IOException {
        File myFile = new File(mySdPath + "/" + EXTERNAL_FILE_NAME);
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(myFile));

        outputStreamWriter.append(edtContent.getText().toString());
        outputStreamWriter.close();
    }

    private String readInternalFileContent() throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        InputStream inputStream = openFileInput(INTERNAL_FILE_NAME);
        if (inputStream != null) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String aDataRow = "";

            while ((aDataRow = reader.readLine()) != null) {
                stringBuilder.append(aDataRow).append("\n");
            }
            inputStream.close();
        }
        return stringBuilder.toString();
    }


}