package com.example.xiti8.myapplication_listview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.LayoutParams;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.wangjie.shadowviewhelper.ShadowProperty;
import com.wangjie.shadowviewhelper.ShadowViewHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xiti8 on 2018/2/11.
 */

public class Yisou_kecheng extends Activity implements AdapterView.OnItemClickListener,AbsListView.OnScrollListener{
    //声明Button
    private Button imageButton_Online;
    private Button imageButton2_Offline;
    //声明ListView
    private ListView listView;
    private ArrayAdapter<String > arr_adapter;
    private SimpleAdapter sim_adapter;
    //卫星式导航
    private MyMenu myMenu;
    //声明SimpleAdapter的数据源
    private List<Map<String,Object>> dataList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.yisou_kecheng);//绑定布局
        //初始化ImageButton
        imageButton_Online = (Button)findViewById(R.id.imageButton_Online);
        imageButton2_Offline = (Button)findViewById(R.id.imageButton2_Offline);
        Online();//调用线上按钮点击事件
        Offline();//调用线下按钮点击事件
            //SimpleAdapter()
        //数据与视图的绑定
        listView = (ListView) findViewById(R.id.listView);
        dataList = new ArrayList<Map<String,Object>>();
        /*
         SimpleAdapter(context,data,resource,from,to)
         context:上下文
         data: 数据源（List<? extends Map<String,?>> data） 一个Map所组成的List集合
        每一个Map都会取对应ListView列表中的一行
        每一个Map<key,value>中的key必须包含所有在from中所指定的key
          resource： 列表项的布局文件ID
            from：Map中的key值（键名）
              to:绑定数据视图中的ID，与from成对应关系
         */
        sim_adapter = new SimpleAdapter(this,getData(),R.layout.item,new String[]{"img","text","text2","text3"},new int[]{R.id.img,R.id.text,R.id.text2,R.id.text3});
        //视图加载适配器
        listView.setAdapter(sim_adapter);
        //加载事件监听器
        listView.setOnItemClickListener(this);
        listView.setOnScrollListener(this);//监视滚动变化



        /*
         卫星式导航
         */
        myMenu =(MyMenu) findViewById(R.id.mymenu);
        myMenu.setMyOnMenuItemClickListener(new MyMenu.OnMenuItemClickListener() {
            @Override
            public void onClick(View view, int pos) {
                //Toast.makeText(MainActivity.this, pos+""+view.getTag(),Toast.LENGTH_SHORT).show();
                if(pos == 1)
                {   //搜索
                    Intent intent = new Intent(Yisou_kecheng.this,MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);

                }if(pos == 2)
                {       //课程

                }if(pos == 3)
                {       //分享
                    Intent intent = new Intent(Yisou_kecheng.this,Yisou_fenxiang.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);


                }if(pos == 4)
                {       //我的
                    Intent intent = new Intent(Yisou_kecheng.this,Yisou_wode.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);

                }

            }
        });
    }

    //Online事件
    private void Online(){
        imageButton_Online.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //线上约课

                Intent intent = new Intent(Yisou_kecheng.this,Online.class);
                startActivity(intent);
            }
        });
    }
    //Offline事件
    private void Offline(){
        imageButton2_Offline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //线下约课
                Intent intent1 = new Intent(Yisou_kecheng.this,Yisou_kecheng_Offline.class);
                startActivity(intent1);
            }
        });

    }

    private List<Map<String,Object>> getData(){

        for(int i=0;i<2;i++)
        {
            Map<String,Object>map = new HashMap<String, Object>();
            map.put("img",R.mipmap.xmt);
            map.put("text","新媒体运营宝典");
            map.put("text2","1502人学过");
            map.put("text3","¥19.90");
            //把map添加到datalist内
            dataList.add(map);
        }
        return dataList;
    }


    //以下三个为ListView点击事件监听器
    @Override
    public void onScrollStateChanged(AbsListView absListView, int i) {
        //滚动状态监听器
        switch (i)
        {
            case SCROLL_STATE_FLING:
                Log.i("main","用户手指离开屏幕前，由于用力滑了一下试图仍然依靠惯性滑动");
                break;
            case SCROLL_STATE_IDLE:
                Log.i("Main","试图已经停止滑动");
                break;
            case SCROLL_STATE_TOUCH_SCROLL:
                Log.i("Main","手指没有离开屏幕，试图正在滑动");
                break;
        }
    }

    @Override
    public void onScroll(AbsListView absListView, int i, int i1, int i2) {

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
       //列表项目触发监听器
        //String text = listView.getItemAtPosition(i)+"";
        Toast.makeText(this,"正在完善中....",Toast.LENGTH_LONG).show();
    }
}



