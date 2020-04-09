package com.zx.expandedittext.entity;

public class BaseEntity {

    private int type = -1;

    public void setType(int type) {
        this.type = type;
    }

    public void setText(String text){}

    public String getText(){
        return "";
    }

    public int getType() {
        return type;
    }
}
