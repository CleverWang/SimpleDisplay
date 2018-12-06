package com.wangcong.simpledisplay.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.LinearLayout;

import com.wangcong.simpledisplay.R;
import com.wangcong.simpledisplay.utils.Const;
import com.wangcong.simpledisplay.utils.HttpUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ShowChartsActivity extends AppCompatActivity {
    private LinearLayout linear_chart_container;

    private Map<String, String> postData;
    private int interval;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_charts);
        // 绑定控件
        bindView();

        // 初始化数据
        initData();

        // 事件监听
        initEvent();
    }

    private void bindView() {
        linear_chart_container = findViewById(R.id.linear_chart_container);
    }

    private void initData() {
        Intent intent = getIntent();
        postData = new HashMap<>();
        postData.put("deviceId", intent.getStringExtra("deviceId"));
        postData.put("serviceId", intent.getStringExtra("serviceId"));
        postData.put("count", intent.getStringExtra("count"));
        interval = Integer.parseInt(intent.getStringExtra("interval"));
        Const.DO_LOOP = true;
    }

    private void initEvent() {
        sendRequest();
    }

    private void sendRequest() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (Const.DO_LOOP) {
                    String response = "";
                    try {
                        response = HttpUtil.getData(postData);
                    } catch (IOException e) {
//                    e.printStackTrace();
                        Log.d(Const.TAG, "getData: " + e.getMessage());
                    }

                    Log.d(Const.TAG, "sendRequest: " + response);

                    try {
                        Thread.sleep(interval * 1000);
                    } catch (InterruptedException e) {
//                        e.printStackTrace();
                        Log.d(Const.TAG, "sendRequest: " + e.getMessage());
                    }
                }
            }
        }).start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Const.DO_LOOP = false;
    }
}
