package com.example.sliderview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;

public class MainActivity extends AppCompatActivity {
    ViewPager viewPager;
    CircleIndicator circleIndicator;
    ImgAdapter imgAdapter;
    private List<Image> mListImage;
    private Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = findViewById(R.id.viewPager);
        circleIndicator = findViewById(R.id.circleIndicator);
        mListImage=getList();
        imgAdapter = new ImgAdapter(this, mListImage);
        viewPager.setAdapter(imgAdapter);
        circleIndicator.setViewPager(viewPager);
        imgAdapter.registerDataSetObserver(circleIndicator.getDataSetObserver());
        AutoSlideImage();
    }

    private List<Image> getList() {
        List<Image> list = new ArrayList<>();
        list.add(new Image(R.drawable.aa));
        list.add(new Image(R.drawable.aser));
        list.add(new Image(R.drawable.asus));
        return list;
    }

    private void AutoSlideImage() {
        if (mListImage == null || mListImage.isEmpty() || viewPager == null) {
            return;
        }
        // Init timer
        if (timer == null) {
            timer = new Timer();
        }
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        int currentItem = viewPager.getCurrentItem();
                        int totalItem = mListImage.size() - 1;
                        if (currentItem < totalItem) {
                            currentItem++;
                            viewPager.setCurrentItem(currentItem);
                        }else {
                            viewPager.setCurrentItem(0);
                        }
                    }
                });
            }
        }, 500, 3000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(timer!=null){
            timer.cancel();
            timer=null;
        }
    }
}