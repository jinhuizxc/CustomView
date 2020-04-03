package com.example.expandedittext.entity;

import android.widget.TextView;

public class TextEntity extends BaseEntity {

    private TextView textView;

    public TextEntity(TextView textView) {
        this.textView = textView;
    }

    public TextView getTextView() {
        return textView;
    }

    public void setTextView(TextView textView) {
        this.textView = textView;
    }

    @Override
    public String getText() {
        return textView.getText().toString();
    }

    @Override
    public void setText(String text) {
        textView.setText(text);
    }

    @Override
    public int getType() {
        return EntityType.TYPE_TEXT;
    }
}
