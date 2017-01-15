package com.gao.databasetest;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.DropBoxManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

/**
 * SQLite 数据库数据存储 的学习
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button btn_create, btnAdd, btnUpdate, btnDelete, btnQuery;
    MyDatabaseHelper mDatabaseHelper;
    private SQLiteDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDatabaseHelper = new MyDatabaseHelper(this, "Book.db", null, 2);
        initView();

    }

    private void initView() {
        mDb = mDatabaseHelper.getReadableDatabase();
        btn_create = (Button) findViewById(R.id.btn_create);
        btn_create.setOnClickListener(this);
        btnAdd = (Button) findViewById(R.id.btn_add);
        btnAdd.setOnClickListener(this);
        btnUpdate = (Button) findViewById(R.id.btn_update);
        btnUpdate.setOnClickListener(this);
        btnDelete = (Button) findViewById(R.id.btn_delete);
        btnDelete.setOnClickListener(this);
        btnQuery = (Button) findViewById(R.id.btn_query);
        btnQuery.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_create:
                mDatabaseHelper.getWritableDatabase(); //创建数据库
                break;
            case R.id.btn_add:
                addData(); //添加数据
                break;
            case R.id.btn_update:
                updateData(); //更新数据
                break;
            case R.id.btn_delete:
                btnDeleteData(); //删除数据
                break;
            case R.id.btn_query:
                btnQueryData(); //查询数据
                break;

        }
    }

    /**
     * 查询数据库 数据
     */
                  private void btnQueryData() {
        Cursor cursor = mDb.query("book", null, null, null, null, null, null);
        if (cursor.moveToFirst()){
            while (cursor.moveToNext()){
                String name = cursor.getString(cursor.getColumnIndex("name"));
                double price = cursor.getDouble(cursor.getColumnIndex("price"));
                int pages = cursor.getInt(cursor.getColumnIndex("pages"));
                String author = cursor.getString(cursor.getColumnIndex("author"));

                Log.d("MainActivity", "书名是: " + name);
                Log.d("MainActivity", "书价是: " + price);
                Log.d("MainActivity", "页码是: " + pages);
                Log.d("MainActivity", "作者是: " + author);

            }
        }
        cursor.close();

    }

    private void btnDeleteData(){
        mDb.delete("book", "pages>?", new String[]{"457"});
        mDb.delete("book", "name=?", new String[]{"乾坤大挪移"});
    }

    private void updateData() {
        ContentValues values = new ContentValues();
        values.put("price", 9.99);

        mDb.update("book", values, "name = ? ", new String[]{"Android"});
    }

    //添加数据
    private void addData() {

        ContentValues values = new ContentValues();
        //开始组装第一个数据
        values.put("name", "Java");
        values.put("author", "Gaosling");
        values.put("pages", 450);
        values.put("price", 39.8);
        mDb.insert("book", null, values);  //插入第一条数据

        values.clear();

        //开始组装第二条数据
        values.put("name", "Android");
        values.put("author", "Jams");
        values.put("pages", 48);
        values.put("price", 23.8);
        mDb.insert("book", null, values);  //插入第一条数据
    }
}
