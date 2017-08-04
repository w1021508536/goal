package com.small.small.goal.my.guess.twoColorBall;


public class OvalEntity {

    public int flag = 0;  //0 红色 1蓝色
    public String content = "";
    public boolean selected = false;

    public OvalEntity(int flag, String content, boolean selected) {
        this.flag = flag;
        this.content = content;
        this.selected = selected;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
