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

    //--------------------------我是分割线------------------------------------------------------------------------------------------
    /**
     * android 提供了非常方便的API用于数据库操作,不过总会有一些人不习惯去使用这些辅助性的方法,而事更青睐于直接使用SQL 来操作数据库.
     * android也提供了一系列的方法,可以直接使用SQL语句来完成操作数据库.
     */
    //1. 添加数据的方法
    public void addData2(){
        mDb.execSQL("insert into book(name,author,pages,price) values(?,?,?,?)",new String[]{"Java,gao,454,16.99"});

        mDb.execSQL("insert into book(name,author,pages,price) values(?,?,?,?)",new String[]{"Android2,gao2,4123,56.6"});
    }

    //2. 更新数据的方法
    private void updateData2(){
        mDb.execSQL("update book set price=? where name=?",new String[]{"10.66","Java"} );
        mDb.execSQL("update book set name=? where author=?",new String[]{"Android2,gao2222"});
    }

    //删除数据的方法
    public void deleteData2(){
        mDb.execSQL("delete from book where pages >?",new String[]{"1000"});
    }

    //4. 查询数据的方法
    public void queryData2(){
        mDb.rawQuery("select * from book",null);
    }
}
