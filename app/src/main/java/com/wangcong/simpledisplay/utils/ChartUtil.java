package com.wangcong.simpledisplay.utils;

import android.graphics.Color;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.wangcong.simpledisplay.beans.DataPoint;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 功能：chart绘制工具
 * <p>
 * 作者：Wang Cong
 * <p>
 * 时间：2018.12.5
 */
public class ChartUtil {

    /**
     * @param chart 要绘制的chart
     * @param datas 数据
     * @param color 绘制chart颜色
     */
    public static void drawChart(LineChart chart, final List<DataPoint> datas, String color) {
        if (datas.size() > 0) {
            String y_label = datas.get(0).getName(); // Y轴标识

            // 添加数据
            List<Entry> entries = new ArrayList<>();
            int i = 0;
            for (DataPoint item : datas) {
                entries.add(new Entry(i, Float.valueOf(item.getValue())));
                ++i;
            }
            LineDataSet dataSet = new LineDataSet(entries, y_label);

            // 设置数据显示格式
            dataSet.setColor(Color.parseColor(color));
            dataSet.setValueTextColor(Color.parseColor(color));
            dataSet.setFillColor(Color.parseColor(color));
            dataSet.setCircleColor(Color.BLACK);
            dataSet.setCircleRadius(3f);
            dataSet.setDrawCircleHole(false);
            dataSet.setDrawFilled(true);
            dataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER); // 曲线显示
            dataSet.setCubicIntensity(0.5f); // 曲率

            LineData lineData = new LineData(dataSet);

            // X轴时间格式化
            final DateFormat sdf = new SimpleDateFormat("HH:mm:ss");
            IAxisValueFormatter formatter = new IAxisValueFormatter() {
                @Override
                public String getFormattedValue(float value, AxisBase axis) {
                    Date date = new Date(Long.valueOf(datas.get((int) value).getTimestamp()));
                    return sdf.format(date);
                }
            };

            // 应用时间格式化到X轴
            XAxis xAxis = chart.getXAxis();
            xAxis.setValueFormatter(formatter);
            xAxis.enableGridDashedLine(10f, 10f, 0f);
//        YAxis yAxis = tem_chart.getAxisLeft();
//        yAxis.setAxisMinimum(0);
//        yAxis.setAxisMaximum(30);

            // 设置不能拖拉和缩放
            chart.getAxisRight().setEnabled(false);
            chart.getDescription().setEnabled(false);
            chart.setDragEnabled(false);
            chart.setScaleEnabled(false);

            // 应用数据
            chart.setData(lineData);
            // refresh
            chart.invalidate();
        }
    }
}
