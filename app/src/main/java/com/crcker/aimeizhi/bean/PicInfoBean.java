package com.crcker.aimeizhi.bean;

/**
 * Created by Crcker on 15/02/2017.
 * 邮箱：635281462@qq.com
 */

public class PicInfoBean {
    //图片地址
    private String picUrl;
    //图片标题
    private String picTitle;
    //图片观看次数
    private String seeCount;
    //套图地址
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "PicInfoBean{" +
                "picUrl='" + picUrl + '\'' +
                ", picTitle='" + picTitle + '\'' +
                ", seeCount='" + seeCount + '\'' +
                '}';
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getPicTitle() {
        return picTitle;
    }

    public void setPicTitle(String picTitle) {
        this.picTitle = picTitle;
    }

    public String getSeeCount() {
        return seeCount;
    }

    public void setSeeCount(String seeCount) {
        this.seeCount = seeCount;
    }
}
