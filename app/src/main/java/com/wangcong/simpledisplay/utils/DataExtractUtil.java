package com.wangcong.simpledisplay.utils;

import com.wangcong.simpledisplay.beans.ConfigBean;
import com.wangcong.simpledisplay.beans.DataPoint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 功能：数据提取工具
 * <p>
 * 作者：Wang Cong
 * <p>
 * 时间：2018.12.5
 */
public class DataExtractUtil {

    /**
     * @param data 服务器返回的有效数据
     * @return 数据集合的集合
     */
    public static List<List<DataPoint>> dataExtractor(String data) throws ParseException {
        List<List<DataPoint>> result = new ArrayList<>();
        if (data == null || data.length() == 0 || data == "\n")
            return result;
        String datas[] = data.split("\n"); // 按行分割
        int datasetCnt = (datas[0].split(" ").length - 2) / 2; // 计算数据集的个数
        for (int i = 0; i < datasetCnt; i++) { // 添加空的数据集
            result.add(new ArrayList<DataPoint>());
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd hhmmss "); // 解析格式："20181205 125531 "
        for (String item : datas) {
            String temp[] = item.split(" ");
            char time[] = temp[1].toCharArray(); // 格式：20181205T125531Z
            // 替换T和Z为空格
            time[8] = ' ';
            time[15] = ' ';
            String timestamp = String.valueOf(simpleDateFormat.parse(new String(time)).getTime()); // 转成时间戳
            for (int i = 2; i < 2 + 2 * datasetCnt; i += 2) {
                result.get(i / 2 - 1).add(new DataPoint(temp[i], temp[i + 1], timestamp));
            }
        }
        return result;
    }

    public static List<ConfigBean> configExtractor(String config) {
        List<ConfigBean> configBeanList = new ArrayList<>();
        if (config == null || config.length() == 0 || config == "\n")
            return configBeanList;

        String configs[] = config.split("\n");
        for (String aconfig : configs) {
            if (aconfig.length() == 0)
                continue;
            String temp[] = aconfig.split(" ");
            if (temp.length < 3)
                continue;
            configBeanList.add(new ConfigBean(temp[0], temp[1], temp[2]));
        }
        return configBeanList;
    }

    /**
     * 测试
     *
     * @param args
     * @throws ParseException
     */
    public static void main(String args[]) throws ParseException {
//        String data = "MONIITORVALE 20181205T125531Z TEMP 27 humi 18 \n" +
//                "MONIITORVALE 20181205T125537Z TEMP 27 humi 10 \n" +
//                "MONIITORVALE 20181205T125543Z TEMP 27 humi 9 \n" +
//                "MONIITORVALE 20181205T125548Z TEMP 27 humi 9 \n" +
//                "MONIITORVALE 20181205T125554Z TEMP 27 humi 9 \n" +
//                "MONIITORVALE 20181205T125601Z TEMP 27 humi 8 \n" +
//                "MONIITORVALE 20181205T125606Z TEMP 27 humi 8 \n" +
//                "MONIITORVALE 20181205T125612Z TEMP 27 humi 8 \n" +
//                "MONIITORVALE 20181205T125619Z TEMP 27 humi 8 \n";
//        List<List<DataPoint>> result = dataExtractor(data);
//        System.out.println(result.size());
//        for (DataPoint dataPoint : result.get(0)) {
//            System.out.println(dataPoint);
//        }
//        for (DataPoint dataPoint : result.get(1)) {
//            System.out.println(dataPoint);
//        }
        List<ConfigBean> result = configExtractor(Const.CONFIGS_4);
        for (ConfigBean configBean : result) {
            System.out.println(configBean);
        }
    }
}
