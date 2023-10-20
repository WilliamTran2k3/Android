package com.example.bai2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;

public class MainActivity2 extends AppCompatActivity {

    EditText name, place, date, time;
    TextInputLayout lName, lPlace,lDate,lTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        ActionBar actionBar = getSupportActionBar();
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#FF009688"));
        actionBar.setBackgroundDrawable(colorDrawable);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);
        actionBar.setDisplayHomeAsUpEnabled(true);

        lName = findViewById(R.id.name_layout);
        lPlace = findViewById(R.id.place_layout);
        lDate = findViewById(R.id.date_layout);
        lTime = findViewById(R.id.time_layout);

        name = findViewById(R.id.edtxt_name);
        place = findViewById(R.id.edtxt_place);
        date = findViewById(R.id.edtxt_date);
        time = findViewById(R.id.edtxt_time);

        place.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] listItems = new String[]{"C201","C202","C203","C204"};
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity2.this);
                builder.setTitle("Select place");
                builder.setSingleChoiceItems(listItems, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        place.setText(listItems[i]);
                        dialogInterface.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int day, month, year;
                if (date.getText().toString().equals("")) {
                    Calendar calendar = Calendar.getInstance();
                    day = calendar.get(Calendar.DAY_OF_MONTH);
                    month = calendar.get(Calendar.MONTH);
                    year = calendar.get(Calendar.YEAR);
                }
                else {
                    String dateStr = date.getText().toString();
                    String[] dateSplit = dateStr.split("/");
                    day = Integer.parseInt(dateSplit[0]);
                    month = Integer.parseInt(dateSplit[1]) - 1;
                    year = Integer.parseInt(dateSplit[2]);
                }
                DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity2.this,
                        new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        date.setText(i2+"/"+(i1+1)+"/"+i);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int hour, minute;
                if (time.getText().toString().equals("")) {
                    Calendar calendar = Calendar.getInstance();
                    hour = calendar.get(Calendar.HOUR_OF_DAY);
                    minute = calendar.get(Calendar.MINUTE);
                }
                else {
                    String timeStr = time.getText().toString();
                    String[] timeSplit = timeStr.split(":");
                    hour = Integer.parseInt(timeSplit[0]);
                    minute = Integer.parseInt(timeSplit[1]);
                }
                TimePickerDialog timePickerDialog = new TimePickerDialog(MainActivity2.this,
                        new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        time.setText(i+":"+i1);
                    }
                }, hour, minute, true);
                timePickerDialog.show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main2,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.btn_save:
                if (validate(lName,"name") && validate(lPlace,"place")
                        && validate(lDate, "date") && validate(lTime, "time")) {
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("name",name.getText().toString());
                    returnIntent.putExtra("place",place.getText().toString());
                    returnIntent.putExtra("date",date.getText().toString());
                    returnIntent.putExtra("time",time.getText().toString());
                    setResult(Activity.RESULT_OK, returnIntent);
                    finish();
                }
        }
        return true;
    }

    private boolean validate(TextInputLayout textInputLayout, String message) {
        if (textInputLayout.getEditText().getText().toString().equals("")) {
            textInputLayout.setError("Please enter event "+message);
            return false;
        }
        else {
            textInputLayout.setError(null);
            return true;
        }
    }
}