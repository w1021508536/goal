package com.small.small.goal.my.entry;

/**
 * 公司：小目标
 * 创建者： 王金壮
 * 时间： 2017/6/21 14:56
 * 修改：
 * 描述： 最后一个代理商的信息
 **/
public class LastAgentEntity {


    /**
     * msg : success
     * code : 0
     * result : {"id":6,"userId":66,"pid":0,"level":3,"subCompanyId":0,"volume":0,"nick":"15628960522","avatar":"http://img.smallaim.cn/1497839039781.jpeg"}
     * pageNum : 0
     * pageSize : 0
     * pageTotal : 0
     * total : 0
     */

    private String msg;
    private int code;
    private ResultBean result;
    private int pageNum;
    private int pageSize;
    private int pageTotal;
    private int total;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageTotal() {
        return pageTotal;
    }

    public void setPageTotal(int pageTotal) {
        this.pageTotal = pageTotal;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public static class ResultBean {
        /**
         * id : 6
         * userId : 66
         * pid : 0
         * level : 3
         * subCompanyId : 0
         * volume : 0
         * nick : 15628960522
         * avatar : http://img.smallaim.cn/1497839039781.jpeg
         */

        private int id;
        private int userId;
        private int pid;
        private int level;
        private int subCompanyId;
        private int volume;
        private String nick;
        private String avatar;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public int getPid() {
            return pid;
        }

        public void setPid(int pid) {
            this.pid = pid;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public int getSubCompanyId() {
            return subCompanyId;
        }

        public void setSubCompanyId(int subCompanyId) {
            this.subCompanyId = subCompanyId;
        }

        public int getVolume() {
            return volume;
        }

        public void setVolume(int volume) {
            this.volume = volume;
        }

        public String getNick() {
            return nick;
        }

        public void setNick(String nick) {
            this.nick = nick;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }
    }
}
