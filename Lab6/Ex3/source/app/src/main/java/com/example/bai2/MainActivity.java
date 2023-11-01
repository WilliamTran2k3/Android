package com.example.bai2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleCursorAdapter;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listView;

    Cursor cursor = null;
    SimpleCursorAdapter cursorAdapter;
    int[] cols;

    SQLiteDatabase db = null;
    MyDBHelper dbHelper;

    String[] KEYS = {dbHelper.KEY_NAME, dbHelper.KEY_PLACE, dbHelper.KEY_DATETIME};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#FF009688"));
        actionBar.setBackgroundDrawable(colorDrawable);

        dbHelper = new MyDBHelper(this);
        db = dbHelper.getWritableDatabase();
        cols = new int[]{R.id.tv_name, R.id.tv_place, R.id.tv_datetime};
        cursor = db.query(dbHelper.TABLE_NAME, dbHelper.COLUMNS, null, null,
                null, null, null);
        cursor.moveToFirst();
        listView = findViewById(R.id.listView);

        cursorAdapter = new SimpleCursorAdapter(
                this,
                R.layout.list_item,
                cursor,
                KEYS,
                cols
        ) {
            Switch switchView;

            @Override
            public void bindView(View view, Context context, Cursor cursor) {
                super.bindView(view, context, cursor);
                switchView = view.findViewById(R.id.switch1);
                boolean isChecked = cursor.getInt(4) != 0;
                switchView.setChecked(isChecked);

                switchView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                        int position = (Integer) buttonView.getTag();
                        Cursor cursor1 = getCursor();
                        cursor1.moveToPosition(position);
                        ContentValues contentValues = new ContentValues();
                        contentValues.put("Enabled", isChecked ? 1 : 0);
                        String whereClause = "_id=?";
                        String[] whereArgs = {String.valueOf(cursor1.getInt(0))};
                        db.update(dbHelper.TABLE_NAME, contentValues, whereClause, whereArgs);

                        Cursor NewCursor = db.query(dbHelper.TABLE_NAME, dbHelper.COLUMNS, null,
                                null, null, null, null);

                        changeCursor(NewCursor);

                    }
                });

                switchView.setTag(cursor.getPosition());
            }
        };

        listView.setAdapter(cursorAdapter);
        registerForContextMenu(listView);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        MenuItem sw = menu.findItem(R.id.app_bar_switch);
        RelativeLayout switch_layout = (RelativeLayout) sw.getActionView();
        Switch bar_switch = switch_layout.findViewById(R.id.bar_switch);
        bar_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (b) {
                    cursor = db.query(dbHelper.TABLE_NAME, dbHelper.COLUMNS, dbHelper.KEY_ENABLED + "=?",
                            new String[]{"1"}, null, null, null);
                    cursorAdapter.swapCursor(cursor);
                }
                else {
                    cursor = db.query(dbHelper.TABLE_NAME, dbHelper.COLUMNS, null,
                            null, null, null, null);
                    cursorAdapter.swapCursor(cursor);
                }
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.btn_add:
                Intent intent = new Intent(MainActivity.this,MainActivity2.class);
                startActivityForResult(intent,12);
                break;
            case R.id.removeAll:
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("Are you sure to remove all events");
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        db.delete(dbHelper.TABLE_NAME, null, null);
                        cursor = db.query(dbHelper.TABLE_NAME, dbHelper.COLUMNS, null,
                                null, null, null, null);
                        cursorAdapter.swapCursor(cursor);
                    }
                });
                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        cursor = db.query(dbHelper.TABLE_NAME, dbHelper.COLUMNS, null,
                null, null, null, null);
        cursorAdapter.swapCursor(cursor);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.context_menu,menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        return super.onContextItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cursor.close();
        db.close();
    }
}