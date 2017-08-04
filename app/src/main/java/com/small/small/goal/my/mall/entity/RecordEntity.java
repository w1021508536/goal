package com.small.small.goal.my.mall.entity;


import com.small.small.goal.my.mall.adapter.RecordAdapter;

/**
 * 公司：小目标
 * 创建者： 王金壮
 * 时间： 2017/7/14 10:38
 * 修改：
 * 描述：
 **/
public class RecordEntity {
    public int type = RecordAdapter.ITEM_TYPE_CONTENT;
    public String title = "";

//    public RecordEntity(int type) {
//        this.type = type;
//    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
