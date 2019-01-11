package com.snz.rskj.android.bean.beautybean;

public class Beauty {
    private String beautyName;
    private int beautyImage;

    public Beauty() {
    }

    public String getBeautyName() {

        return beautyName;
    }

    public void setBeautyName(String beautyName) {
        this.beautyName = beautyName;
    }

    public int getBeautyImage() {
        return beautyImage;
    }

    public void setBeautyImage(int beautyImage) {
        this.beautyImage = beautyImage;
    }

    public Beauty(String beautyName, int beautyImage) {
        this.beautyName = beautyName;
        this.beautyImage = beautyImage;
    }
}
