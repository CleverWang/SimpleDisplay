package com.wangcong.simpledisplay.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.LinearLayout;

import com.github.mikephil.charting.charts.LineChart;
import com.wangcong.simpledisplay.R;
import com.wangcong.simpledisplay.beans.DataPoint;
import com.wangcong.simpledisplay.utils.ChartUtil;
import com.wangcong.simpledisplay.utils.Const;
import com.wangcong.simpledisplay.utils.DataExtractUtil;
import com.wangcong.simpledisplay.utils.HttpUtil;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShowChartsActivity extends AppCompatActivity {
    private LinearLayout linear_chart_container;

    private Map<String, String> postData;
    private int interval;

    private List<LineChart> charts;
    private List<String> colors;

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
                boolean isFirstRequest = true;
                while (Const.DO_LOOP) {
                    String response = "";
                    try {
                        response = HttpUtil.getData(postData);
                    } catch (IOException e) {
//                    e.printStackTrace();
                        Log.d(Const.TAG, "getData: " + e.getMessage());
                    }

//                    Log.d(Const.TAG, "sendRequest: " + response);
                    int code_end_idx = response.indexOf('\n');
                    if (code_end_idx != -1) {
                        String code = response.substring(0, code_end_idx);
                        if (code.equals("0")) {
                            if (isFirstRequest) {
                                if (doDrawChartsFirstTime(response.substring(code_end_idx + 1)))
                                    isFirstRequest = false;
                            } else {
                                doDrawCharts(response.substring(code_end_idx + 1));
                            }

                        } else {
                            Log.d(Const.TAG, "run: " + response);
                        }
                    } else {
                        Log.d(Const.TAG, "run: " + response);
                    }

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

    private boolean doDrawChartsFirstTime(final String data) {
        List<List<DataPoint>> datas_t;
        try {
            datas_t = DataExtractUtil.dataExtractor(data);
        } catch (ParseException e) {
            Log.d(Const.TAG, "doDrawChartsFirstTime: " + e.getMessage());
            return false;
        }
        final List<List<DataPoint>> datas = datas_t;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                charts = new ArrayList<>();
                colors = new ArrayList<>();
                int datasetCnt = datas.size();
                for (int i = 0; i < datasetCnt; i++) {
                    charts.add(new LineChart(ShowChartsActivity.this));
                    colors.add(Const.COLORS[(int) (Math.random() * Const.COLORS.length)]);
                }
                int height = 700;
                for (LineChart lineChart : charts) {
                    lineChart.setPadding(0, 5, 0, 0);
                    linear_chart_container.addView(lineChart, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, height));
                }
                for (int i = 0; i < datasetCnt; i++) {
                    ChartUtil.drawChart(charts.get(i), datas.get(i), colors.get(i));
                }
            }
        });
        return true;
    }

    private void doDrawCharts(final String data) {
        List<List<DataPoint>> datas_t = null;
        try {
            datas_t = DataExtractUtil.dataExtractor(data);
        } catch (ParseException e) {
            Log.d(Const.TAG, "doDrawCharts: " + e.getMessage());
        }
        final List<List<DataPoint>> datas = datas_t;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (datas == null)
                    return;
                int datasetCnt = datas.size();
                for (int i = 0; i < datasetCnt; i++) {
                    ChartUtil.drawChart(charts.get(i), datas.get(i), colors.get(i));
                }
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Const.DO_LOOP = false;
    }
}
