package com.example.xiti8.myapplication_listview;

import cn.bmob.v3.BmobObject;

/**
 * Created by xiti8 on 2018/2/25.
 */

public class DBproblem extends BmobObject {
    private String title;//问题所属科目类型
    private String describe;//问题描述
    private String phone;//联系方式

    public void setNumber(int number) {
        this.number = number;
    }

    private int number ;//控制查询所有数据

    public int getNumber() {
        return number;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTitle() {
        return title;
    }

    public String getDescribe() {
        return describe;
    }

    public String getPhone() {
        return phone;
    }
}
