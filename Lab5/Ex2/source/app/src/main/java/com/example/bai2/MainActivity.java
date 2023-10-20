package com.example.bai2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    ArrayAdapter<EventManage> adapter;
    ArrayAdapter<EventManage> enabled_adapter;
    ArrayList<EventManage> data;
    ArrayList<EventManage> enabled_data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#FF009688"));
        actionBar.setBackgroundDrawable(colorDrawable);

        listView = findViewById(R.id.listView);
        data = new ArrayList<>();
        adapter = new ArrayAdapter<EventManage>(this,R.layout.list_item,R.id.tv_name,data) {
            TextView name, place, datetime;
            Switch aSwitch;
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View itemView = super.getView(position, convertView, parent);
                name = itemView.findViewById(R.id.tv_name);
                place = itemView.findViewById(R.id.tv_place);
                datetime = itemView.findViewById(R.id.tv_datetime);
                aSwitch = itemView.findViewById(R.id.switch1);

                name.setText(data.get(position).getName());
                place.setText(data.get(position).getPlace());
                datetime.setText(data.get(position).getDatetime());
                aSwitch.setChecked(data.get(position).isEnabled());

                aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        data.get(position).setEnabled(b);
                        adapter.notifyDataSetChanged();
                    }
                });

                return itemView;
            }
        };

        enabled_data = new ArrayList<>();
        enabled_adapter = new ArrayAdapter<EventManage>(this,R.layout.list_item,R.id.tv_name,enabled_data) {
            TextView name, place, datetime;
            Switch aSwitch;
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View itemView = super.getView(position, convertView, parent);
                name = itemView.findViewById(R.id.tv_name);
                place = itemView.findViewById(R.id.tv_place);
                datetime = itemView.findViewById(R.id.tv_datetime);
                aSwitch = itemView.findViewById(R.id.switch1);

                name.setText(enabled_data.get(position).getName());
                place.setText(enabled_data.get(position).getPlace());
                datetime.setText(enabled_data.get(position).getDatetime());
                aSwitch.setChecked(enabled_data.get(position).isEnabled());

                aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        enabled_data.get(position).setEnabled(b);
                        enabled_adapter.notifyDataSetChanged();
                    }
                });
                return itemView;
            }
        };
        listView.setAdapter(adapter);
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
                    enabled_data.clear();
                    for (EventManage e:
                         data) {
                        if (e.isEnabled()) {
                            enabled_data.add(e);
                        }
                    }
                    enabled_adapter.notifyDataSetChanged();
                    listView.setAdapter(enabled_adapter);
                }
                else {
                    listView.setAdapter(adapter);
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
                        data.clear();
                        adapter.notifyDataSetChanged();
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
        String name = data.getStringExtra("name");
        String place = data.getStringExtra("place");
        String date = data.getStringExtra("date");
        String time = data.getStringExtra("time");
        this.data.add(new EventManage(name,place,date,time));
        adapter.notifyDataSetChanged();
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
}