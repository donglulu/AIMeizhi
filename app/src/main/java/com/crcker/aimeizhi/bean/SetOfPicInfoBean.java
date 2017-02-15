package com.crcker.aimeizhi.bean;

/**
 * Created by Crcker on 15/02/2017.
 * 邮箱：635281462@qq.com
 * <p>
 * 套图
 */

public class SetOfPicInfoBean {

    private String title;

    private String pic_url;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "SetOfPicInfoBean{" +
                "title='" + title + '\'' +
                ", pic_url='" + pic_url + '\'' +
                '}';
    }

    public String getPic_url() {
        return pic_url;
    }

    public void setPic_url(String pic_url) {
        this.pic_url = pic_url;
    }
}
