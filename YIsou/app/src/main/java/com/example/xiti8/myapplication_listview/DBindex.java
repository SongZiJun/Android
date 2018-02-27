package com.example.xiti8.myapplication_listview;

import cn.bmob.v3.BmobObject;

/**
 * Created by xiti8 on 2018/2/24.
 */
    //问题表单
public class DBindex extends BmobObject {

    private String title;//问题所属科目类型
    private String describe;//问题描述
    private String phone;//联系方式

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
