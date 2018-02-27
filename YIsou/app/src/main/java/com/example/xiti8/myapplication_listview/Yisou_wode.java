package com.example.xiti8.myapplication_listview;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by xiti8 on 2018/2/24.
 */

public class Yisou_wode extends Activity  implements View.OnClickListener {

    private MyMenu myMenu;
    private DBHelper helper;
    private Button sign;
    private Button reg;
    private  String  name;
    private  String  mypwd;
    private EditText user;
    private EditText password;
    int userflag ;//判断 用户名是否存在
    int loginflag ;//登录时判断用户密码是否输入正确


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.yisou_wode);
        findViewById(R.id.button1).setOnClickListener(this);
        findViewById(R.id.button2).setOnClickListener(this);
        user = (EditText)findViewById(R.id.editText1);
        password = (EditText)findViewById(R.id.editText2);
        myMenu =(MyMenu) findViewById(R.id.mymenu);
        myMenu.setMyOnMenuItemClickListener(new MyMenu.OnMenuItemClickListener() {
            @Override
            public void onClick(View view, int pos) {
                if(pos == 1)
                {   //搜索
                    Intent intent = new Intent(Yisou_wode.this,MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);

                }if(pos == 2)
                {       //课程
                    Intent intent = new Intent(Yisou_wode.this,Yisou_kecheng.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);


                }if(pos == 3)
                {       //分享
                    Intent intent = new Intent(Yisou_wode.this,Yisou_fenxiang.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);


                }if(pos == 4)
                {       //我的

                }
            }
        });
    }


    public void  insert()
    {
        helper = new DBHelper(getApplicationContext());
        SQLiteDatabase db=helper.getWritableDatabase();
        //建立打开可读可写的数据库实例

        //查询是否用户名重复
        String sql1 = "select * from users";
        Cursor cursor = db.rawQuery(sql1, null);
        while (cursor.moveToNext()) {
            //第一列为id
            name =  cursor.getString(1); //获取第2列的值,第一列的索引从0开始
            mypwd = cursor.getString(2);//获取第3列的值
            if((user.getText().toString().isEmpty())||(password.getText().toString().isEmpty())){
                Toast.makeText(this, "什么都不输，你登录个啥？", Toast.LENGTH_SHORT).show();
                break;
            }
            userflag = 1;  //不存在此用户
            if((user.getText().toString().equals(name)))
            {
                Toast.makeText(this, "此用户已存在，请重新注册", Toast.LENGTH_SHORT).show();
                userflag =0;
                break;
            }
        }
        if(userflag==1)
        {
            String sql2 = "insert into users(name,pwd) values ('"+user.getText().toString()+"','"+password.getText().toString()+"')";
            db.execSQL(sql2);
            Toast.makeText(this, "注册成功！", Toast.LENGTH_SHORT).show();
        }

    }


    public void select()
    {
        helper = new DBHelper(getApplicationContext());
        SQLiteDatabase db=helper.getWritableDatabase();

        String sql = "select * from users";

        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            //第一列为id
            name =  cursor.getString(1); //获取第2列的值,第一列的索引从0开始
            mypwd = cursor.getString(2);//获取第3列的值
            if((user.getText().toString().equals(name))&&(password.getText().toString().equals(mypwd)))
            {
                Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
                loginflag=1;

                //intent bundle传值
                Intent YisouwodeDB = new Intent();
                YisouwodeDB .setClass(this,MainActivityProblem.class);//登录成功跳转到主页
                Bundle bundle = new Bundle(); //该类用作携带数据
                bundle.putString("user", user.getText().toString());
                YisouwodeDB.putExtras(bundle);   //向MainActivity传值
                this.startActivity(YisouwodeDB);
                finish();//退出
            }
        }
        /*
            如果密码或账号等于空 输出提示信息
         */
        if((user.getText().toString().isEmpty())||(password.getText().toString().isEmpty())){
            Toast.makeText(this, "什么都不输，你登录个啥？", Toast.LENGTH_SHORT).show();
        }

        if(loginflag!=1)
        {
            Toast.makeText(this, "账号或者密码错误,请重新输入", Toast.LENGTH_SHORT).show();
        }
        cursor.close();
        db.close();//关闭数据库
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.button1:
                select();
                break;
            case R.id.button2:
                insert();
                break;
        }
    }

}
