package com.zx.customview.ad;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class AdsBean {

    private int times;
    private List<AdData> imgs;
    private String msg;

    @Data
    private static class AdData implements Serializable {

        private String imgurl;    // 欢迎页url
        private String imglink;


    }
}