package com.zx.expandedittext.utils;

import com.zx.expandedittext.entity.BaseEntity;
import com.zx.expandedittext.entity.EditEntity;
import com.zx.expandedittext.entity.EntityType;
import com.zx.expandedittext.entity.ImageEntity;

public class EntityUtils {

    public static boolean isEditEntity(BaseEntity entity){
        if (entity.getType() == EntityType.TYPE_EDIT){
            return true;
        }else {
            return false;
        }
    }

    public static boolean isImageEntity(BaseEntity entity){
        if (entity.getType() == EntityType.TYPE_IMAGE){
            return true;
        }else {
            return false;
        }
    }

    public static EditEntity getEditEntity(BaseEntity entity) {
        if (isEditEntity(entity)){
            return (EditEntity) entity;
        }
        return null;
    }

    public static ImageEntity getImageEntity(BaseEntity entity){
        if (isImageEntity(entity)){
            return (ImageEntity) entity;
        }
        return null;
    }
}
