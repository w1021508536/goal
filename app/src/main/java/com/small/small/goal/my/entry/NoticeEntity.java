package com.small.small.goal.my.entry;

/**
 * 公司：小目标
 * 创建者： 王金壮
 * 时间： 2017/6/16 18:58
 * 修改：
 * 描述： 公告的实体
 **/
public class NoticeEntity {
    /**
     * id : 1
     * title : 明天要下雨了
     * content : 明天要下雨了明天要下雨了明天要下雨了明天要下雨了明天要下雨了明天要下雨了明天要下雨了明天要下雨了
     * createTime : 1497495217000
     * updateTime : 1497495217000
     * status : 1
     */

    private int id;
    private String title;
    private String content;
    private long createTime;
    private long updateTime;
    private int status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
