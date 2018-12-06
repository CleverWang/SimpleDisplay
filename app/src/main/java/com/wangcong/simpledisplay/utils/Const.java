package com.wangcong.simpledisplay.utils;

/**
 * 功能：全局变量
 * <p>
 * 作者：Wang Cong
 * <p>
 * 时间：2018.12.5
 */
public class Const {

    // Log的TAG
    public static final String TAG = "SimpleDisplay";

    // IP和端口
    public static final String SERVER_URL = "http://106.39.43.30:8066/api/get_data";

    // 实验四的测试配置
    public static final String CONFIGS_4_TEST = "778761a0-468b-467e-93b8-d5ad071a3a1d MONIITORVALE 10 5\n778761a0-468b-467e-93b8-d5ad071a3a1d BatteryMon 10 5\n";

    // 实验四的配置
    public static String CONFIGS_4 = "";

    // 颜色池
    public static final String[] COLORS = {"#f17c67", "#db9019", "#5ed5d1", "#1a2d27", "#ff6e97", "#F1aaa6", "#376956", "#9966cc"};

    // 每个chart的高度
    public static final int CHART_HEIGHT = 700;

    // 动态图表是否动态显示
    public static boolean DO_LOOP = false;
}