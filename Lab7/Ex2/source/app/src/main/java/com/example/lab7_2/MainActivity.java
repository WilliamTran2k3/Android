package com.example.lab7_2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.loader.content.CursorLoader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    MediaAdapter adapter;
    RecyclerView rv;
    ArrayList<MediaData> data = new ArrayList<MediaData>();
    Button btnSort;
    int status = 0; // "DESC"
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Connection:
        rv = findViewById(R.id.recyclerview);
        btnSort = findViewById(R.id.btn_sort);

        int isAccepted = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (isAccepted != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }
        isAccepted = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (isAccepted != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
        }

        loadMedia(" DESC");
        Log.i("=== DATA ===", data.toString());

        // Set event for sort buttom
        btnSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (status==0){
                    loadMedia(" ASC");
                    status =1;
                }
                else {
                    loadMedia(" DESC");
                    status=0;
                }
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "PERMISSION REQUIRED FOR READ", Toast.LENGTH_SHORT).show();
            }
        }
        if (requestCode == 2) {
            if (grantResults.length > 0 && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "PERMISSION REQUIRED WRITE", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Create action menu
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_menu, menu);
        return true;
    }

    // Set event for action_menu Item:
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Remove selected files/folders
        if (item.getItemId() == R.id.delete_selected){
            adapter.removeSelected();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadMedia(String sort) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (!Environment.isExternalStorageManager()) {
                Log.i("=== IS EXTERNALSTORAGEMANAGER ===", "TRUE");
                // Request the MANAGE_EXTERNAL_STORAGE permission
                Intent intent = new Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                startActivity(intent);
            }
        }

        ArrayList<MediaData> tmp = new ArrayList<>();
        // Get relevant columns for use later.
        String[] projection = {
                MediaStore.Files.FileColumns._ID,
                MediaStore.Files.FileColumns.DATA,
                MediaStore.Files.FileColumns.DATE_ADDED,
                MediaStore.Files.FileColumns.MEDIA_TYPE,
                MediaStore.Files.FileColumns.MIME_TYPE,
                MediaStore.Files.FileColumns.TITLE
        };

// Return only video and image metadata.
        String selection = MediaStore.Files.FileColumns.MEDIA_TYPE + "="
                + MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE
                + " OR "
                + MediaStore.Files.FileColumns.MEDIA_TYPE + "="
                + MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO;

        Uri queryUri = MediaStore.Files.getContentUri("external");
        Log.i("=== URI ===", String.valueOf(queryUri));

        CursorLoader cursorLoader = new CursorLoader(
                this,
                queryUri,
                projection,
                selection,
                null, // Selection args (none).
                MediaStore.Files.FileColumns.DATE_ADDED + sort // Sort order.
        );
        Log.i("=== CURSOR LOADER ===", String.valueOf(cursorLoader));

        Cursor cursor = cursorLoader.loadInBackground();
        Log.i("=== CURSOR ===", String.valueOf(cursor.getCount()));
        if (cursor != null && cursor.moveToFirst()) {
            Log.i("=== ENTER CURSOR ===", "");
            int dataColIndex = cursor.getColumnIndex(MediaStore.Files.FileColumns.DATA);
            int titleColIndex = cursor.getColumnIndex(MediaStore.Files.FileColumns.TITLE);
            int mimeTypeColIndex = cursor.getColumnIndex(MediaStore.Files.FileColumns.MIME_TYPE);
            int dateAddedColIndex = cursor.getColumnIndex(MediaStore.Files.FileColumns.DATE_ADDED);

            do {
                String filePath = cursor.getString(dataColIndex);
                String title = cursor.getString(titleColIndex);
                String mimeType = cursor.getString(mimeTypeColIndex);
                String dateAdded = cursor.getString(dateAddedColIndex);

                // Do something with the metadata (filePath, title, mimeType, dateAdded)
                String str[] = mimeType.split("/");
                MediaData media;
                if (str[0].equals("video")){
                    media = new MediaData(filePath, title, dateAdded, true, false);
                }
                else {
                    media = new MediaData(filePath, title, dateAdded, false, false);
                }
                tmp.add(media);
//                adapter.notifyDataSetChanged();
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        data = tmp;
        adapter = new MediaAdapter(data, this);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);
    }
}