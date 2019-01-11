package com.snz.rskj.android.bean;

import java.io.Serializable;

public class   UserMsg implements Serializable{

    private String name;
    private String psw;
    private String toKen;

    public String getPsw() {
        return psw;
    }

    public void setPsw(String psw) {
        this.psw = psw;
    }

    public String getToKen() {
        return toKen;
    }

    public void setToKen(String toKen) {
        this.toKen = toKen;
    }

    @Override
    public String toString() {
        return "UserMsg{" +
                "name='" + name + '\'' +
                ", psw='" + psw + '\'' +
                ", toKen='" + toKen + '\'' +
                '}';
    }


    public UserMsg(String name, String psw, String toKen) {
        this.name = name;
        this.psw = psw;
        this.toKen = toKen;
    }

    public UserMsg(String name) {
        this.name = name;
    }


    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
