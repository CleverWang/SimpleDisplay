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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 功能：展示表格界面
 * <p>
 * 作者：Wang Cong
 * <p>
 * 时间：2018.12.6
 */
public class ShowChartsActivity extends AppCompatActivity {
    private LinearLayout linear_chart_container; // 线性布局，用于放置chart

    private Map<String, String> postData; // 传递给服务器的参数
    private int interval; // 刷新间隔

    private List<LineChart> charts; // chart列表
    private List<String> colors; // chart绘制颜色列表，与每一个chart对应

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
        // 获取从上个界面传来的数据
        postData.put("deviceId", intent.getStringExtra("deviceId"));
        postData.put("serviceId", intent.getStringExtra("serviceId"));
        postData.put("count", intent.getStringExtra("count"));
        interval = Integer.parseInt(intent.getStringExtra("interval"));

        // 设置动态刷新
        Const.DO_LOOP = true;
    }

    private void initEvent() {
        sendRequest();
    }

    /**
     * 按interval时间发送请求获取数据并展示
     */
    private void sendRequest() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean isFirstRequest = true; // 是否是第一次请求
                while (Const.DO_LOOP) {
                    String response = "";
                    try {
                        response = HttpUtil.getData(postData); // 从服务器获取数据
                    } catch (Exception e) {
//                    e.printStackTrace();
                        Log.d(Const.TAG, "getData: " + e.getMessage());
                    }
//                    Log.d(Const.TAG, "sendRequest: " + response);

                    int code_end_idx = response.indexOf('\n');
                    if (code_end_idx != -1) {
                        String code = response.substring(0, code_end_idx); // 截取第一行唯一的那个服务器响应code
                        if (code.equals("0")) { // code为0表示数据有效
                            if (isFirstRequest) {
                                if (doDrawChartsFirstTime(response.substring(code_end_idx + 1))) // 第一次绘制成功才取消isFirstRequest
                                    isFirstRequest = false;
                            } else { // 接下来只需要更新绘制好的charts就行
                                doDrawCharts(response.substring(code_end_idx + 1));
                            }
                        } else { // code为-1表示输入的配置参数有问题，请检查deviceId和serviceId；code为0表示未找到数据，请确保设备已经上传数据到IoT平台
                            Log.d(Const.TAG, "run: " + response);
                        }
                    } else { // 服务器响应出错
                        Log.d(Const.TAG, "run: " + response);
                    }

                    try {
                        // 线程睡眠interval秒
                        Thread.sleep(interval * 1000);
                    } catch (InterruptedException e) {
//                        e.printStackTrace();
                        Log.d(Const.TAG, "sendRequest: " + e.getMessage());
                    }
                }
            }
        }).start();
    }

    /**
     * 第一次绘制charts
     *
     * @param data 从服务器获取的数据
     * @return 绘制成功与否
     */
    private boolean doDrawChartsFirstTime(final String data) {
        List<List<DataPoint>> datas_t;
        try {
            datas_t = DataExtractUtil.dataExtractor(data); // 解析数据得到数据集
        } catch (Exception e) {
            Log.d(Const.TAG, "doDrawChartsFirstTime: " + e.getMessage());
            return false;
        }
        final List<List<DataPoint>> datas = datas_t;
        if (datas == null)
            return false;

        // 在UI主线程中绘制
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // 初始化
                charts = new ArrayList<>();
                colors = new ArrayList<>();
                int datasetCnt = datas.size();
                for (int i = 0; i < datasetCnt; i++) {
                    charts.add(new LineChart(ShowChartsActivity.this));
                    colors.add(Const.COLORS[(int) (Math.random() * Const.COLORS.length)]); // 从颜色池随机挑选一个颜色
                }

                int height = Const.CHART_HEIGHT; // 每个chart的高度
                // 动态添加chart
                for (LineChart lineChart : charts) {
                    lineChart.setPadding(0, 5, 0, 0);
                    linear_chart_container.addView(lineChart, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, height));
                }

                // 第一次绘制
                for (int i = 0; i < datasetCnt; i++) {
                    ChartUtil.drawChart(charts.get(i), datas.get(i), colors.get(i));
                }
            }
        });
        return true;
    }

    /**
     * 第一次绘制后，接下来只更新就行
     *
     * @param data 从服务器获取的数据
     */
    private void doDrawCharts(final String data) {
        List<List<DataPoint>> datas_t = null;
        try {
            datas_t = DataExtractUtil.dataExtractor(data);
        } catch (Exception e) {
            Log.d(Const.TAG, "doDrawCharts: " + e.getMessage());
        }
        final List<List<DataPoint>> datas = datas_t;
        if (datas == null)
            return;

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
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
        Const.DO_LOOP = false; // 退出当前界面后，终止访问服务器线程
    }
}
