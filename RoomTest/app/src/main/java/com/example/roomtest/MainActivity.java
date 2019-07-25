package com.example.roomtest;

import android.arch.persistence.room.Room;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.roomtest.db.AppDataBase;
import com.example.roomtest.entity.BookEntity;
import com.example.roomtest.entity.UserEntity;
import com.example.roomtest.thread.AppExecutors;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView addTextView, deleteTextView;
    private TextView contentView;
    private TextView newTableView,newtableContent,deleteTableContent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addTextView = findViewById(R.id.add_data);
        deleteTextView = findViewById(R.id.delete_data);
        contentView = findViewById(R.id.textView);
        newTableView= findViewById(R.id.new_table_data);
        newtableContent=findViewById(R.id.new_table_content);
        deleteTableContent=findViewById(R.id.delete_table_content);

        addTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertData();
            }
        });
        deleteTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteData();
            }

        });
        newTableView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertNewTableData();
            }
        });
        deleteTableContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppDataBase.getInstance(getApplicationContext()).getBookDao().deleteAllBook();
                final List<BookEntity> entityList = AppDataBase.getInstance(getApplicationContext()).getBookDao().queryAll();
                AppExecutors.getInstance().mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        newtableContent.setText(entityList.toString());
                    }
                });
            }
        });

    }
    // 掺入一张新的表的数据
    private void insertNewTableData(){
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                AppDataBase.getInstance(getApplicationContext()).getBookDao().insertList(DataGenertor.generateBooks());
                final List<BookEntity> entityList = AppDataBase.getInstance(getApplicationContext()).getBookDao().queryAll();
                AppExecutors.getInstance().mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        newtableContent.setText(entityList.toString());
                    }
                });
            }
        });

    }
    //插入数据
    private void insertData() {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                AppDataBase.getInstance(getApplicationContext()).getUserDao().insertList(DataGenertor.generateUsers());
                final List<UserEntity> entityList = AppDataBase.getInstance(getApplicationContext()).getUserDao().queryAll();
                AppExecutors.getInstance().mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        contentView.setText(entityList.toString());
                    }
                });
            }
        });
    }
   //删除数据
    private void deleteData() {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                List<UserEntity> userEntities = DataGenertor.generateUsers();
                for (int i = 0; i < userEntities.size(); i++) {
                    UserEntity userEntity = AppDataBase.getInstance(getApplicationContext()).getUserDao().getUserByName(userEntities.get(i).getUserName());
                    if (userEntity!=null) {
                        AppDataBase.getInstance(getApplicationContext()).getUserDao().delete(userEntity);
                    }
                }
                final List<UserEntity> entityList = AppDataBase.getInstance(getApplicationContext()).getUserDao().queryAll();
                AppExecutors.getInstance().mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        contentView.setText(entityList.toString());
                    }
                });
            }
        });
    }
}
