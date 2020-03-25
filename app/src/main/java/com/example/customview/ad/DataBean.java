package com.example.customview.ad;

public class DataBean {

    public int animType;
    public String animName;

    public int getAnimType() {
        return animType;
    }

    public DataBean(int animType, String animName) {
        this.animType = animType;
        this.animName = animName;
    }

    public void setAnimType(int animType) {
        this.animType = animType;
    }

    public String getAnimName() {
        return animName;
    }

    public void setAnimName(String animName) {
        this.animName = animName;
    }
}
