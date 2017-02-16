package com.crcker.aimeizhi.model;

import android.util.Log;

import com.crcker.aimeizhi.bean.LableInfoBean;
import com.crcker.aimeizhi.bean.PicInfoBean;
import com.crcker.aimeizhi.bean.SetOfPicInfoBean;
import com.crcker.aimeizhi.constant.Constant;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Crcker on 15/02/2017.
 * 邮箱：635281462@qq.com
 */

public class GetDataFromHtml {

    ArrayList<PicInfoBean> picInfoBeens = null;

    ArrayList<LableInfoBean> lableInfoBeens = null;

    ArrayList<SetOfPicInfoBean> setOfPicInfoBeens = null;


    //获取首页数据
    public ArrayList<PicInfoBean> getHomeData(int pages, boolean b, String url) {

        String Totalpages = "1";
        Document content = null;
        try {
            if (b == true) {
                content = Jsoup.connect(url).timeout(5000).post();
            } else {
                content = Jsoup.connect(url + "/" + pages).timeout(5000).post();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Elements element = content.select("div.pic");

        Document pageContent = Jsoup.parse(element.toString());

        if (url != Constant.HOT_ADDRESS) {

            //获取页码
            Elements page = pageContent.select("em.info");
            String pageEle = page.text();
            Totalpages = pageEle.substring(1, pageEle.length() - 1);

        }
        Document picContent = Jsoup.parse(element.toString());

        Elements Li = picContent.getElementsByTag("li");

        PicInfoBean picInfoBean;

        if (picInfoBeens == null) {

            picInfoBeens = new ArrayList<>();
        }

        picInfoBeens.clear();


        for (Element li : Li) {
            picInfoBean = new PicInfoBean();
            //地址
            picInfoBean.setUrl(li.select("a").attr("href"));
            //标题
            picInfoBean.setPicTitle(li.select("img").attr("alt"));
            //缩略图
            picInfoBean.setPicUrl(li.select("img").attr("src"));
            picInfoBean.setPages(Totalpages);
            System.out.print(picInfoBean.toString());
            picInfoBeens.add(picInfoBean);
        }


        return picInfoBeens;
    }


    //获取标签数据
    public ArrayList<LableInfoBean> getLableData() {
        lableInfoBeens = new ArrayList<>();
        Document content = null;
        try {
            content = Jsoup.connect(Constant.LABLE_ADDRESS).timeout(5000).post();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Elements element = content.select("div.tag");

        Document picContent = Jsoup.parse(element.toString());
        Elements Li = picContent.getElementsByTag("li");

        LableInfoBean lableInfoBean;
        lableInfoBeens = new ArrayList<>();
        for (Element li : Li) {
            lableInfoBean = new LableInfoBean();
            lableInfoBean.setUrl(li.select("a").attr("href"));
            lableInfoBean.setLable_pic_url(li.select("img").attr("src"));
            lableInfoBean.setTitle(li.select("a").tagName("i").text());
            String count = li.tagName("i").text();
            int index = count.indexOf("共");


            lableInfoBean.setPicCount(count.substring(index, count.length()));
            System.out.print(lableInfoBean.toString());
            lableInfoBeens.add(lableInfoBean);
        }

        return lableInfoBeens;
    }


    //获取套图数据
    public ArrayList<SetOfPicInfoBean> getSetPic(String url) {
        lableInfoBeens = new ArrayList<>();
        Document content = null;
        try {
            content = Jsoup.connect(url).timeout(5000).post();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //获取内容
        Elements element = content.select("div.content");
        //获取页码
        Elements page = content.select("div.page");


        int endIndex = page.text().indexOf("全");
        int startIndex = page.text().indexOf("6");


        //分割页码 取到最后一页

        int pages = Integer.parseInt(page.text().substring(startIndex + 1, endIndex));

        //图片内容
        Document picContent = Jsoup.parse(element.toString());
        //解析图片
        Elements Img = picContent.getElementsByTag("img");

        SetOfPicInfoBean setOfPicInfoBean;
        setOfPicInfoBeens = new ArrayList<>();
        for (Element li : Img) {


            //第一张图片地址
            String picUrl = li.attr("src");
            //分割，获取服务端
            String picServer = picUrl.toString().substring(0, picUrl.length() - 5);

            //合并图片数据
            for (int i = 1; i <= pages; i++) {
                setOfPicInfoBean = new SetOfPicInfoBean();

                setOfPicInfoBean.setPic_url(picServer + i + ".jpg");
                System.out.print(setOfPicInfoBean.toString());
                setOfPicInfoBeens.add(setOfPicInfoBean);
                setOfPicInfoBean = null;
            }


        }

        return setOfPicInfoBeens;
    }


    //随机数据
    public ArrayList<PicInfoBean> getRandomData() {


        Document content = null;

        try {

            content = Jsoup.connect("http://www.mmjpg.com/mm/871").timeout(5000).post();


        } catch (IOException e) {
            e.printStackTrace();
        }

        Elements element = content.select("div.hot");

        Document picContent = Jsoup.parse(element.toString());

        Elements Li = picContent.getElementsByTag("dd");

        PicInfoBean picInfoBean;

        if (picInfoBeens == null) {

            picInfoBeens = new ArrayList<>();
        }


        for (Element li : Li) {
            picInfoBean = new PicInfoBean();
            //地址
            picInfoBean.setUrl(li.select("a").attr("href"));
            //标题
            picInfoBean.setPicTitle(li.select("img").attr("alt"));
            //缩略图
            picInfoBean.setPicUrl(li.select("img").attr("src"));
            picInfoBeens.add(picInfoBean);
        }


        return picInfoBeens;
    }

}

