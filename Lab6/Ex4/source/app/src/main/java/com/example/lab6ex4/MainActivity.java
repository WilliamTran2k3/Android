package com.example.lab6ex4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.FileUtils;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Button btnBack;
    RecyclerView recyclerView;
    RecyclerView.Adapter<MyViewHolder> adapter;
    File[] files;
    File path, root;
    Uri uri;
    ArrayList<Boolean> checked;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnBack = findViewById(R.id.btnBack);
        recyclerView = findViewById(R.id.recyclerView);
        root = Environment.getExternalStorageDirectory();
        path = root;
        // Nếu chưa set quyền truy cập thì hiển thị để người dùng cho chọn
        if (!Environment.isExternalStorageManager()) {
            uri = Uri.parse("package:" + BuildConfig.APPLICATION_ID);
            Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION, uri);
            this.startActivity(intent);
        }

        files = path.listFiles();
        checked = new ArrayList<Boolean>();
        for (File file : files) {
            checked.add(false);
        }

        adapter = new RecyclerView.Adapter<MyViewHolder>() {
            @NonNull
            @Override
            public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = getLayoutInflater().inflate(R.layout.row, null, false);
                view.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT));
                return new MyViewHolder(view);
            }

            @Override
            public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
                holder.tvName.setText(files[position].getName());
                if (!files[position].isDirectory()) {
                    holder.ivIcon.setImageResource(R.drawable.ic_baseline_insert_drive_file_24);
                }
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (path.toPath().resolve(holder.tvName.getText().toString()).toFile().listFiles() != null) {
                            path = path.toPath().resolve(holder.tvName.getText().toString()).toFile();
                            files = path.listFiles();
                            checked = new ArrayList<Boolean>();
                            for (File file : files) {
                                checked.add(false);
                            }
                            notifyDataSetChanged();
                        }
                    }
                });

                holder.checkBox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        checked.set(holder.getAdapterPosition(), true);
                    }
                });
            }

            @Override
            public int getItemCount() {
                return files.length;
            }
        };

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Sự kiện click vào nút back
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!path.equals(root)) {
                    path = path.getParentFile();
                    files = path.listFiles();
                    checked = new ArrayList<Boolean>();
                    for (File file : files) {
                        checked.add(false);
                    }

                    adapter.notifyDataSetChanged();
                }
            }
        });

    }

    private class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView ivIcon;
        TextView tvName;
        CheckBox checkBox;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            ivIcon = itemView.findViewById(R.id.ivIcon);
            tvName = itemView.findViewById(R.id.tvName);
            checkBox = itemView.findViewById(R.id.checkBox);
        }
    }

    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        View v;
        AlertDialog.Builder builder;
        switch (item.getItemId()) {
            case R.id.btnAddFile:
                v = getLayoutInflater().inflate(R.layout.add_file, null, false);
                EditText edtName = v.findViewById(R.id.edtFileName);
                EditText edtCotent = v.findViewById(R.id.edtFileContent);
                builder = new AlertDialog.Builder(this);
                builder.setView(v);
                builder.setTitle("Create a file");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String fileName = edtName.getText().toString();
                        String fileContent = edtCotent.getText().toString();
                        if (fileName.equals(""))
                            return;
                        File newFile = new File(path.getPath() + "/" + fileName + ".txt");
                        if (!newFile.exists()) {
                            try {
                                newFile.createNewFile();
                                files = path.listFiles();
                                checked = new ArrayList<Boolean>();
                                for (File file : files) {
                                    checked.add(false);
                                }
                                adapter.notifyDataSetChanged();
                            }
                            catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });

                builder.setNegativeButton("CANCEL",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();
                break;
            case R.id.btnAddFolder:
                v = getLayoutInflater().inflate(R.layout.add_folder, null, false);
                EditText edtFolderName = v.findViewById(R.id.edtFolderName);
                builder = new AlertDialog.Builder(this);
                builder.setView(v);
                builder.setTitle("Create a folder");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                            String folderName = edtFolderName.getText().toString();
                            if (folderName.equals(""))
                                return;
                            File newDir = new File(path.getPath() + "/" + folderName);
                            if (!newDir.exists()) {
                                newDir.mkdirs();
                                files = path.listFiles();
                                checked = new ArrayList<Boolean>();
                                for (File file : files) {
                                    checked.add(false);
                                }
                                adapter.notifyDataSetChanged();
                            }
                    }
                });

                builder.setNegativeButton("CANCEL",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                dialog = builder.create();
                dialog.show();
                break;
            case R.id.btnRemoveSelecteed:
                builder = new AlertDialog.Builder(this);
                builder.setTitle("Remove all files ?");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        for (int k = checked.size()-1; k >=0 ; k--) {
                            if (checked.get(k)) {
                                files[k].delete();
                            }
                        }
                        files = path.listFiles();
                        adapter.notifyDataSetChanged();
                    }
                });

                builder.setNegativeButton("CANCEL",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                dialog = builder.create();
                dialog.show();

                break;
            case R.id.btnRemoveAll:
                builder = new AlertDialog.Builder(this);
                builder.setTitle("Remove all files ?");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        for (File file : files) {
                            deleteDirectory(file);
                        }
                        files = path.listFiles();
                        checked = new ArrayList<Boolean>();
                        for (File file : files) {
                            checked.add(false);
                        }
                        adapter.notifyDataSetChanged();
                    }
                });

                builder.setNegativeButton("CANCEL",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                dialog = builder.create();
                dialog.show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    // Thực hiện việc gọi đệ quy để xóa các file
    private boolean deleteDirectory(File directoryToBeDeleted) {
        File[] allContents = directoryToBeDeleted.listFiles();
        if (allContents != null) {
            for (File file : allContents) {
                deleteDirectory(file);
            }
        }
        return directoryToBeDeleted.delete();
    }
}