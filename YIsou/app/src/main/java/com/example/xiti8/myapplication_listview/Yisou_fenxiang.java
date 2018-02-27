package com.example.xiti8.myapplication_listview;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Created by xiti8 on 2018/2/21.
 */

public class Yisou_fenxiang extends Activity implements ViewPager.OnPageChangeListener{

    private List<View> viewList;
    private ViewPager pager;
    //底部导航
    private PagerTabStrip tab;
    private List<String> titleList;
    //卫星式导航
    private MyMenu myMenu;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.yisou_fenxiang);

        viewList = new ArrayList<View>();
        View view1 = View.inflate(this,R.layout.yisou_fenxiang_ziyuan,null);
        View view2 = View.inflate(this,R.layout.yisou_fenxiang_qiuzhu,null);

        //将各个页面添加到list集合中
        viewList.add(view1);
        viewList.add(view2);
        //为页卡设置标题
        titleList = new ArrayList<String>();
        titleList.add("资源");
        titleList.add("求助");

        //设置导航属性
        tab =(PagerTabStrip) findViewById(R.id.tab);
        tab.setBackgroundColor(Color.WHITE);
        tab.setTextColor(Color.parseColor("#57C98C"));
        tab.setDrawFullUnderline(false);
        tab.setTabIndicatorColor(Color.parseColor("#57C98C"));

        //初始化ViewPager
        pager =findViewById(R.id.pager);
        //建立适配器PageAdapt
        MyPageAdapt adapter = new MyPageAdapt(viewList,titleList);
        //ViewPage加载适配器
        pager.setAdapter(adapter);

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
                    Intent intent = new Intent(Yisou_fenxiang.this,MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);

                }if(pos == 2)
                {       //课程
                    Intent intent = new Intent(Yisou_fenxiang.this,Yisou_kecheng.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }if(pos == 3)
                {       //分享
                }if(pos == 4)
                {       //我的
                    Intent intent = new Intent(Yisou_fenxiang.this,Yisou_wode.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            }
        });
    }

    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }


    public void onPageSelected(int position) {

    }


    public void onPageScrollStateChanged(int state) {

    }




}
