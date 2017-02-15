package com.crcker.aimeizhi.bean;

/**
 * Created by Crcker on 15/02/2017.
 * 邮箱：635281462@qq.com
 */

public class LableInfoBean {
    private String lable_pic_url;
    private String url;

    private String picCount;

    public String getPicCount() {
        return picCount;
    }

    public void setPicCount(String picCount) {
        this.picCount = picCount;
    }

    public String getLable_pic_url() {
        return lable_pic_url;
    }

    public void setLable_pic_url(String lable_pic_url) {
        this.lable_pic_url = lable_pic_url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    private String title;

    @Override
    public String toString() {
        return "LableInfoBean{" +
                "lable_pic_url='" + lable_pic_url + '\'' +
                ", url='" + url + '\'' +
                ", picCount='" + picCount + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
