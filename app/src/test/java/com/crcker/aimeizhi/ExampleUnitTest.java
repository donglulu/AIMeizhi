package com.crcker.aimeizhi;

import com.crcker.aimeizhi.model.GetDataFromHtml;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        GetDataFromHtml dataFromHtml = new GetDataFromHtml();
        dataFromHtml.getHomeData(0,true,"http://www.mmjpg.com/tag/xinggan");
    }
}