package com.example.lab3_bai4;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;

public class MainActivity2 extends AppCompatActivity {

    ImageView image;
    TextView job;
    TextView name;
    TextView phone;
    TextView email;
    TextView address;
    TextView homepage;
    Button save_btn;
    ImageView camera_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        image = findViewById(R.id.image2);
        job = findViewById(R.id.job2);
        name = findViewById(R.id.name2);
        phone = findViewById(R.id.phone2);
        email = findViewById(R.id.email2);
        address = findViewById(R.id.address2);
        homepage = findViewById(R.id.homepage2);
        save_btn = findViewById(R.id.save_btn);
        camera_btn = findViewById(R.id.camera_btn);

        Bundle extras = getIntent().getExtras();
        byte[] byteArray = extras.getByteArray("image");

        Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        image.setImageBitmap(bitmap);

        job.setText(extras.getString("job"));
        name.setText(extras.getString("name"));
        phone.setText(extras.getString("phone"));
        email.setText(extras.getString("email"));
        address.setText(extras.getString("address"));
        homepage.setText(extras.getString("homepage"));

        camera_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, 100);
            }
        });

        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent r_intent = new Intent();
                Bitmap bitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();
                r_intent.putExtra("image", byteArray);
                r_intent.putExtra("job",job.getText().toString());
                r_intent.putExtra("name",name.getText().toString());
                r_intent.putExtra("phone",phone.getText().toString());
                r_intent.putExtra("email",email.getText().toString());
                r_intent.putExtra("address",address.getText().toString());
                r_intent.putExtra("homepage",homepage.getText().toString());
                setResult(RESULT_OK, r_intent);
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap img_bmp = (Bitmap)(data.getExtras().get("data"));
        image.setImageBitmap(img_bmp);
    }
}