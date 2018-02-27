package com.example.xiti8.myapplication_listview;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xiti8 on 2018/2/15.
 */

public class Online extends Activity implements AdapterView.OnItemClickListener{
    private GridView gridView;
    private List<Map<String,Object>> datalist;
    //保存小图标
    private int[] iconArr = {R.mipmap.computers,R.mipmap.gongxue,
            R.mipmap.jingjiguanli,R.mipmap.lixue,
            R.mipmap.waiyu,R.mipmap.wenxuelishi,R.mipmap.xinlixue,
            R.mipmap.yishusheji,R.mipmap.zhexue};
    //各个的图标名称
    private String [] iconName = {"计算机","工学","经济管理","力学",
            "外语","文学历史","心理学","艺术设计","哲学"};
    //声明适配器
    private SimpleAdapter Adapter;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.online);//绑定布局

        gridView =(GridView) findViewById(R.id.GridView_online);
        //准备数据源、新建适配器、GridView加载适配器、配置监听器
        datalist = new ArrayList<Map<String, Object>>();//新建数据源
        Adapter = new SimpleAdapter(this,getData(),R.layout.online2,
                new String[] {"image","text"}, new int[] {R.id.image,R.id.text}
                );
        gridView.setAdapter(Adapter);
        gridView.setOnItemClickListener(this);
    }

    public List<Map<String,Object>> getData() {
        for (int i=0;i<iconName.length;i++)
        {
                 Map<String,Object>map = new HashMap<String,Object>();
                 map.put("image",iconArr[i]);
                 map.put("text",iconName[i]);
                 datalist.add(map);
        }
        return datalist;
    }
    //实现监听器
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        //这是事件的监听器
        Toast.makeText(this,"我是"+iconName[i],Toast.LENGTH_SHORT).show();
        //需要在加载监听器
    }
}
