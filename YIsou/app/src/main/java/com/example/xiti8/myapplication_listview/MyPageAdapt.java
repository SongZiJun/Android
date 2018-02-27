package com.example.xiti8.myapplication_listview;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.List;

/**
 * Created by xiti8 on 2018/2/18.
 */

public class MyPageAdapt extends PagerAdapter{
    private List<View> viewList;
    private List<String> titleList;
    public MyPageAdapt(List<View> viewList,List<String> titleList)
    {
        this.viewList = viewList;
        this.titleList = titleList;
    }
    /*
        返回页卡的数量
     */
    @Override
    public int getCount() {
        return viewList.size();
    }
    /*
           View是否来自于对象
     */
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }
    /*
        实例化一个页卡
    */
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(viewList.get(position));
        return viewList.get(position);
    }
    /*
        销毁一个页卡
     */
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
      container.removeView(viewList.get(position));
    }

    /*
        设置导航标题
     */
    @Override
    public CharSequence getPageTitle(int position) {
        return titleList.get(position);
    }
}
