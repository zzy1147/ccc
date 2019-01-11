package com.snz.rskj.android.bean.andiencebean;

import com.netease.nimlib.sdk.avchat.constant.AVChatType;
import com.snz.rskj.android.activitylive.MicStateEnum;

public class Audience {
    private String andienceName;
    private String andienceImageUrl;
    private Boolean andienceAttention;
    private Boolean andienceConnectM;
    private String andienceGoldNum;

/*    private String account;
    private String name;
    private String avatar;
    private AVChatType avChatType;
    private boolean isSelected;*/
    private MicStateEnum micStateEnum;

    public MicStateEnum getMicStateEnum() {
        return micStateEnum;
    }

    public void setMicStateEnum(MicStateEnum micStateEnum) {
        this.micStateEnum = micStateEnum;
    }

    public Audience(String andienceName, String andienceImageUrl, Boolean andienceAttention, Boolean andienceConnectM, String andienceGoldNum) {
        this.andienceName = andienceName;
        this.andienceImageUrl = andienceImageUrl;
        this.andienceAttention = andienceAttention;
        this.andienceConnectM = andienceConnectM;
        this.andienceGoldNum = andienceGoldNum;
    }

    public Audience() {
    }

    public String getAndienceName() {
        return andienceName;
    }

    public void setAndienceName(String andienceName) {
        this.andienceName = andienceName;
    }

    public String getAndienceImageUrl() {
        return andienceImageUrl;
    }

    public void setAndienceImageUrl(String andienceImageUrl) {
        this.andienceImageUrl = andienceImageUrl;
    }

    public Boolean getAndienceAttention() {
        return andienceAttention;
    }

    public void setAndienceAttention(Boolean andienceAttention) {
        this.andienceAttention = andienceAttention;
    }

    public Boolean getAndienceConnectM() {
        return andienceConnectM;
    }

    public void setAndienceConnectM(Boolean andienceConnectM) {
        this.andienceConnectM = andienceConnectM;
    }

    public String getAndienceGoldNum() {
        return andienceGoldNum;
    }

    public void setAndienceGoldNum(String andienceGoldNum) {
        this.andienceGoldNum = andienceGoldNum;
    }


}
