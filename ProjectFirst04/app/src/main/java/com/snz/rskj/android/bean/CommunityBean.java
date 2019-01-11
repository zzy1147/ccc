package com.snz.rskj.android.bean;

public class CommunityBean {
    private String name;
    private String des;
    private String status;

    public CommunityBean(String name, String des, String status) {
        this.name = name;
        this.des = des;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
