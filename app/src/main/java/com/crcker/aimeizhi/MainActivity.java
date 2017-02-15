package com.crcker.aimeizhi;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.crcker.aimeizhi.adapter.MyPagerAdapter;
import com.crcker.aimeizhi.fragment.HotFragment;
import com.crcker.aimeizhi.fragment.LableFragment;
import com.crcker.aimeizhi.fragment.MainFragment;
import com.crcker.aimeizhi.fragment.RecommedFragment;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import cn.hugeterry.coordinatortablayout.CoordinatorTabLayout;
import cn.hugeterry.coordinatortablayout.listener.LoadHeaderImagesListener;

public class MainActivity extends AppCompatActivity {
    private CoordinatorTabLayout mCoordinatorTabLayout;
    private int[] mImageArray, mColorArray;
    private ArrayList<Fragment> mFragments;
    private final String[] mTitles = {"首页", "分类", "热门", "随机"};
    private ViewPager mViewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        initFragments();
        initViewPager();

        mColorArray = new int[]{
                android.R.color.holo_blue_dark,
                android.R.color.holo_red_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_orange_dark};

        mCoordinatorTabLayout = (CoordinatorTabLayout) findViewById(R.id.coordinatortablayout);
        mCoordinatorTabLayout.setTitle("爱妹纸")
                .setBackEnable(false)
                .setContentScrimColorArray(mColorArray)
                .setLoadHeaderImagesListener(new LoadHeaderImagesListener() {
                    @Override
                    public void loadHeaderImages(ImageView imageView, TabLayout.Tab tab) {
                        switch (tab.getPosition()) {
                            case 0:
                                loadImages(imageView, "http://img.mmjpg.com/2016/557/1.jpg");
                                break;
                            case 1:
                                loadImages(imageView, "http://img.mmjpg.com/2016/557/18.jpg");
                                break;
                            case 2:
                                loadImages(imageView, "http://img.mmjpg.com/2016/557/21.jpg");
                                break;
                            case 3:
                                loadImages(imageView, "http://img.mmjpg.com/2016/557/29.jpg");
                                break;
                            default:
                                break;
                        }
                    }
                })
                .setupWithViewPager(mViewPager);
    }

    private void loadImages(ImageView imageView, String url) {
        Picasso.with(MainActivity.this)
                .load(url).into(imageView);
    }

    private void initFragments() {
        mFragments = new ArrayList<>();
        mFragments.add(MainFragment.getInstance());
        mFragments.add(LableFragment.getInstance());
        mFragments.add(HotFragment.getInstance());
        mFragments.add(RecommedFragment.getInstance());
    }


    private void initViewPager() {
        mViewPager = (ViewPager) findViewById(R.id.vp);
        mViewPager.setOffscreenPageLimit(4);
        mViewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager(), mFragments, mTitles));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        switch (item.getItemId()) {
            case R.id.action_about:
                break;
            case R.id.action_about_me:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
