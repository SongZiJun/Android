package com.example.xiti8.myapplication_listview;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by xiti8 on 2018/2/24.
 * 利用SQLite实现本地储存
 */
//用户数据表单
public class DBHelper extends SQLiteOpenHelper {
    /*
     制定数据库名称，版本信息
     */
    public DBHelper(Context context) {
        super(context, "userdb.db", null, 3);
    }
    /*
    创建数据库中的表
    */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table users(id integer primary key ,name text,pwd text)");
        String sql = "insert into users values (0,0,0)";
        sqLiteDatabase.execSQL(sql);
    }
    //版本更新方法
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

    }
}
