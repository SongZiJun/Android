package com.example.xiti8.myapplication_listview;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.wangjie.shadowviewhelper.ShadowProperty;
import com.wangjie.shadowviewhelper.ShadowViewHelper;

import java.sql.SQLDataException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

import static android.widget.AbsListView.OnScrollListener.SCROLL_STATE_IDLE;
import static android.widget.AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL;
import static android.widget.NumberPicker.OnScrollListener.SCROLL_STATE_FLING;

public class MainActivity extends AppCompatActivity{
    //声明ListView
    private ListView listView;
    private SimpleAdapter sim_adapter;
    //声明SimpleAdapter的数据源类型
    private List<Map<String,Object>> dataList;
    private MyMenu myMenu;
    //退出时的时间
    private long mExitTime;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);//绑定布局
        show("小易正在努力加载数据中");
        //数据与视图的绑定
        listView = (ListView) findViewById(R.id.listView_problem);
        dataList = new ArrayList<Map<String,Object>>();//存放数据
        //初始化BmobSDK
        Bmob.initialize(this,"a28b21e364d57673faa8b23622ce7182");
        //查询DBproblem中的所有数据
        BmobQuery<DBproblem> query = new BmobQuery<DBproblem>();
        query.order("-createdAt");
        query.addWhereGreaterThan("number",0);//查询number大于0的所有数据
        query.setLimit(500);//返回50条数据，如果不加上这条语句，默认返回10条数据
        query.findObjects(new FindListener<DBproblem>() {
            @Override
            public void done(List<DBproblem> object, BmobException e) {
                if(e==null)
                {
                    for (DBproblem DB : object)
                    {
                        String s = DB.getTitle();//获得问题类型信息
                        String s2 = DB.getDescribe();//获得服务端问题详细描述信息
                        String s3 = DB.getPhone();//获得用户联系信息
                        Map<String,Object>map = new HashMap<String, Object>();
                        map.put("title",s);
                        map.put("describe",s2);
                        map.put("phone",s3);
                        dataList.add(map);
                        //视图加载适配器
                        listView.setAdapter(sim_adapter);
                    }
                }else {
                    show("抱歉，获取数据失败");
                }
            }
        });
        sim_adapter = new SimpleAdapter(this,dataList,R.layout.main2,new String[]{"title","describe","phone"},new int[]{R.id.main2_title,R.id.main2_describe,R.id.main2_phone});
        listView.setDivider(null);//去除listView的分割线
        //创建卫星样式菜单
        myMenu =(MyMenu) findViewById(R.id.mymenu);
        myMenu.setMyOnMenuItemClickListener(new MyMenu.OnMenuItemClickListener() {
            @Override
            public void onClick(View view, int pos) {
                //Toast.makeText(MainActivity.this, pos+""+view.getTag(),Toast.LENGTH_SHORT).show();
                if(pos == 1)
                {   //搜索

                }if(pos == 2)
                {       //课程
                    Intent intent = new Intent(MainActivity.this,Yisou_kecheng.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }if(pos == 3)
                {       //分享
                    Intent intent = new Intent(MainActivity.this,Yisou_fenxiang.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);;
                    startActivity(intent);
                }if(pos == 4)
                {       //我的
                    Intent intent = new Intent(MainActivity.this,Yisou_wode.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);;
                    startActivity(intent);
                }
            }
        });
    }
    /*
       双击退出
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            exit();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void exit() {

        if ((System.currentTimeMillis() - mExitTime) > 2000) {
            Toast.makeText(MainActivity.this, "再按一次退出小易", Toast.LENGTH_SHORT).show();
            mExitTime = System.currentTimeMillis();
        }else
        {
            finish();
            System.exit(0);
        }
    }
    //自定义Toast
    public void show(String text)
    {
        Toast.makeText(this,text,Toast.LENGTH_SHORT).show();
    }


}



