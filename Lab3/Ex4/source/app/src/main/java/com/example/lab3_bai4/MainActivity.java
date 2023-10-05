package com.example.lab3_bai4;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;

public class MainActivity extends AppCompatActivity {

    ImageView editIcon;
    ImageView image;
    TextView job;
    TextView name;
    TextView phone;
    TextView email;
    TextView address;
    TextView homepage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editIcon = findViewById(R.id.edit_icon);
        image = findViewById(R.id.image1);
        job = findViewById(R.id.job1);
        name = findViewById(R.id.name1);
        phone = findViewById(R.id.phone1);
        email = findViewById(R.id.email1);
        address = findViewById(R.id.address1);
        homepage = findViewById(R.id.homepage1);

        editIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                image.setDrawingCacheEnabled(true);

                Bitmap bitmap = image.getDrawingCache();

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();

                intent.putExtra("image", byteArray);
                intent.putExtra("job",job.getText().toString());
                intent.putExtra("name",name.getText().toString());
                intent.putExtra("phone",phone.getText().toString());
                intent.putExtra("email",email.getText().toString());
                intent.putExtra("address",address.getText().toString());
                intent.putExtra("homepage",homepage.getText().toString());
                startActivityForResult(intent, 12);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        image.destroyDrawingCache();

        Bundle extras = data.getExtras();
        byte[] byteArray = extras.getByteArray("image");

        Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        image.setImageBitmap(bitmap);

        job.setText(extras.getString("job"));
        name.setText(extras.getString("name"));
        phone.setText(extras.getString("phone"));
        email.setText(extras.getString("email"));
        address.setText(extras.getString("address"));
        homepage.setText(extras.getString("homepage"));
    }
}