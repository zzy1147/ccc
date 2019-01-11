package com.snz.rskj.android.bean.giftbean;


import com.netease.nim.chatroom.demo.entertainment.constant.GiftType;

/**
 * 礼物
 *
 */
public class Gift {
    private GiftType giftType;
    private String title;
    private int count;
    private int imageId;
    private String giftPrice;

    public Gift(GiftType giftType, String title, int count, int imageId) {
        this.giftType = giftType;
        this.title = title;
        this.count = count;
        this.imageId = imageId;
    }

    public GiftType getGiftType() {
        return giftType;
    }

    public void setGiftType(GiftType giftType) {
        this.giftType = giftType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getGiftPrice() {
        return giftPrice;
    }

    public void setGiftPrice(String giftPrice) {
        this.giftPrice = giftPrice;
    }

    public Gift() {

    }

    public Gift(GiftType giftType, String title, int count, int imageId, String giftPrice) {

        this.giftType = giftType;
        this.title = title;
        this.count = count;
        this.imageId = imageId;
        this.giftPrice = giftPrice;
    }
}
