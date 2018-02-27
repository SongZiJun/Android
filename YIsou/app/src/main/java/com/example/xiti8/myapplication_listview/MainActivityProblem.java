package com.example.xiti8.myapplication_listview;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.opengl.ETC1;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import rx.internal.schedulers.NewThreadScheduler;

/**
 * Created by xiti8 on 2018/2/25.
 */
//提交问题上传到服务器
public class MainActivityProblem extends MainActivity{
    private Button btn_submit;
    private EditText et_leixing, et_wenti,et_phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myactivityproblem);
        //初始化BmobSDK
        Bmob.initialize(this,"a28b21e364d57673faa8b23622ce7182");

        btn_submit = findViewById(R.id.btn_submit);
        et_leixing = (EditText)findViewById(R.id.et_leixing);
        et_wenti = (EditText)findViewById(R.id.et_wenti);
        et_phone = (EditText) findViewById(R.id.et_phone);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String leixing = et_leixing.getText().toString();
                String wenti = et_wenti.getText().toString();
                String phone = et_phone.getText().toString();
                if(!TextUtils.isEmpty(leixing)&&!TextUtils.isEmpty(wenti)&&!TextUtils.isEmpty(phone))
                {
                    submit(leixing,wenti,phone);
                }else {
                    toast("问题好像还没有完善呢");
                }
            }
        });
    }
    /*
     Toast 方法
     */
    public void toast(String text)
    {
        Toast.makeText(this,text,Toast.LENGTH_SHORT).show();
    }
    /*
        submit方法 提交到后端云
     */
    public void  submit(String Title,String Describe,String Phone)
    {
        DBproblem DB = new DBproblem();
        DB.setTitle(Title);
        DB.setDescribe(Describe);
        DB.setPhone(Phone);
        DB.setNumber(1);//用户发表的每一条数据的number都是一  便于查询所有数据
        //EdText输入的数据已获取 添加到数据库
        DB.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if(e==null){
                    toast("您的问题已成功发表");
                    Intent intent = new Intent(MainActivityProblem.this,MainActivity.class);
                    startActivity(intent);
                }else{
                    toast("问题发表失败");
                }
            }
        });
    }
}
