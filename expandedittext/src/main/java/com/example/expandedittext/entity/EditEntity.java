package com.example.expandedittext.entity;

import android.widget.EditText;

public class EditEntity extends BaseEntity {

    EditText editText;

    public EditEntity(EditText editText) {
        this.editText = editText;
    }

    public EditText getEditText() {
        return editText;
    }

    public void setEditText(EditText editText) {
        this.editText = editText;
    }

    @Override
    public void setText(String text) {
        getEditText().setText(text);
    }

    @Override
    public String getText() {
        return editText == null ? "" : editText.getText().toString();
    }

    @Override
    public int getType() {
        return EntityType.TYPE_EDIT;
    }
}
