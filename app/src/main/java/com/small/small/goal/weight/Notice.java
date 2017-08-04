package com.small.small.goal.weight;

/**
 * Created by JS on 2017-08-02.
 */
public class Notice {

    String noticeStr = "";
    int nameLength;

    public Notice(String noticeStr, int nameLength) {
        this.noticeStr = noticeStr;
        this.nameLength = nameLength;
    }

    public String getNoticeStr() {
        return noticeStr;
    }

    public void setNoticeStr(String noticeStr) {
        this.noticeStr = noticeStr;
    }

    public int getNameLength() {
        return nameLength;
    }

    public void setNameLength(int nameLength) {
        this.nameLength = nameLength;
    }
}