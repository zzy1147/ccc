package com.snz.rskj.android.view.home.bean;

public class LiveBean {
    public String url;
    public String img;
    public String zan;
    public boolean attention;
    public String msg;
    public String share;
    public int lives;
    public int livesImg;

    public LiveBean(int lives, int livesImg) {
        this.lives = lives;
        this.livesImg = livesImg;
    }

    public LiveBean(int video) {

    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public void setZan(String zan) {
        this.zan = zan;
    }

    public void setAttention(boolean attention) {
        this.attention = attention;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setShare(String share) {
        this.share = share;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public void setLivesImg(int livesImg) {
        this.livesImg = livesImg;
    }
}
