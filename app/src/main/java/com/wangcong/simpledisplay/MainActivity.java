package com.wangcong.simpledisplay;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private TextView tem_tv; // 温度显示
    private TextView hum_tv; // 湿度显示

    private LineChart tem_chart; // 温度图表
    private LineChart hum_chart; // 湿度图表

    private List<DataBean> datas;

    // 消息处理
    private final int UPDATE_MSG = 1;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATE_MSG:
                    generateFakeData();
                    drawCharts();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 绑定控件
        bindView();

        // 初始化数据
        initData();

        // 事件监听
        initEvent();
    }

    private void bindView() {
        tem_tv = findViewById(R.id.tem_tv);
        hum_tv = findViewById(R.id.hum_tv);

        tem_chart = findViewById(R.id.tem_chart);
        hum_chart = findViewById(R.id.hum_chart);
    }

    private void initData() {
        Const.DO_LOOP = true; // 设置动态展示

        // 初始化零数据用于填充图表
        datas = new ArrayList<>(Const.DATA_COUNT);
        int data_count = Const.DATA_COUNT;
        for (int i = 0; i < data_count; i++) {
            DataBean data = new DataBean();
            data.setTem((float) 0.0);
            data.setHum((float) 0.0);
            Date date = new Date();
            data.setTem_timestamp(String.valueOf(date.getTime()));
            data.setHum_timestamp(String.valueOf(date.getTime()));
            datas.add(data);
        }

        setTimer(); // 启动定时器
        drawCharts(); // 绘制初始图表
    }

    private void initEvent() {
    }

    private void setTimer() {
        // 启动定时器，每隔一定时间通知图表进行更新
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (Const.DO_LOOP) {
                    try {
                        Thread.sleep(Const.SLEEP_TIME);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Message message = new Message();
                    message.what = UPDATE_MSG;
                    handler.sendMessage(message);
                }
            }
        }).start();
    }

    private void getData() {
        // TODO: 实际获取数据
    }

    private void generateFakeData() { // 生成随机数据用于测试
        Random rand = new Random();
        datas.remove(0);
        DataBean data = new DataBean();
        data.setTem(rand.nextFloat() * 100);
        data.setHum(rand.nextFloat() * 100);
        Date date = new Date();
        data.setTem_timestamp(String.valueOf(date.getTime()));
        data.setHum_timestamp(String.valueOf(date.getTime()));
        datas.add(data);
        tem_tv.setText(String.format(Locale.CHINA, "%.1f", data.getTem()));
        hum_tv.setText(String.format(Locale.CHINA, "%.1f", data.getHum()));
    }

    private void drawCharts() { // 绘制图表
        if (datas.size() > 0) {

            List<Entry> tem_entries = new ArrayList<>();
            List<Entry> hum_entries = new ArrayList<>();

            int i = 0;
            for (DataBean item : datas) {
                tem_entries.add(new Entry(i, item.getTem()));
                hum_entries.add(new Entry(i, item.getHum()));
                ++i;
            }

            LineDataSet tem_dataSet = new LineDataSet(tem_entries, "温度");
            LineDataSet hum_dataSet = new LineDataSet(hum_entries, "湿度");

            tem_dataSet.setColor(Color.parseColor("#f17c67"));
            tem_dataSet.setValueTextColor(Color.parseColor("#f17c67"));
            tem_dataSet.setCircleColor(Color.BLACK);
            tem_dataSet.setCircleRadius(3f);
            tem_dataSet.setDrawCircleHole(false);
            tem_dataSet.setDrawFilled(true);
            tem_dataSet.setFillColor(Color.parseColor("#f17c67"));
            tem_dataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
            tem_dataSet.setCubicIntensity(0.2f);

            hum_dataSet.setColor(Color.parseColor("#DB9019"));
            hum_dataSet.setValueTextColor(Color.parseColor("#DB9019"));
            hum_dataSet.setCircleColor(Color.BLACK);
            hum_dataSet.setCircleRadius(3f);
            hum_dataSet.setDrawCircleHole(false);
            hum_dataSet.setDrawFilled(true);
            hum_dataSet.setFillColor(Color.parseColor("#DB9019"));
            hum_dataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
            hum_dataSet.setCubicIntensity(0.2f);

            LineData tem_lineData = new LineData(tem_dataSet);
            LineData hum_lineData = new LineData(hum_dataSet);

            final DateFormat sdf = new SimpleDateFormat("HH:mm:ss");
            IAxisValueFormatter formatter = new IAxisValueFormatter() {
                @Override
                public String getFormattedValue(float value, AxisBase axis) {
                    Date date = new Date(Long.valueOf(datas.get((int) value).getTem_timestamp()));
                    return sdf.format(date).toString();
                }
            };

            XAxis xAxis = tem_chart.getXAxis();
            xAxis.setValueFormatter(formatter);
            xAxis.enableGridDashedLine(10f, 10f, 0f);
//        YAxis yAxis = tem_chart.getAxisLeft();
//        yAxis.setAxisMinimum(0);
//        yAxis.setAxisMaximum(30);
            tem_chart.getAxisRight().setEnabled(false);
            tem_chart.getDescription().setEnabled(false);
            tem_chart.setDragEnabled(false);
            tem_chart.setScaleEnabled(false);
            tem_chart.setData(tem_lineData);
            tem_chart.invalidate(); // refresh

            formatter = new IAxisValueFormatter() {
                @Override
                public String getFormattedValue(float value, AxisBase axis) {
                    Date date = new Date(Long.valueOf(datas.get((int) value).getHum_timestamp()));
                    return sdf.format(date).toString();
                }
            };

            xAxis = hum_chart.getXAxis();
            xAxis.setValueFormatter(formatter);
            xAxis.enableGridDashedLine(10f, 10f, 0f);
//        yAxis = hum_chart.getAxisLeft();
//        yAxis.setAxisMinimum(0);
//        yAxis.setAxisMaximum(20);
            hum_chart.getAxisRight().setEnabled(false);
            hum_chart.getDescription().setEnabled(false);
            hum_chart.setDragEnabled(false);
            hum_chart.setScaleEnabled(false);
            hum_chart.setData(hum_lineData);
            hum_chart.invalidate(); // refresh
        }
    }
}






