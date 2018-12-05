package com.wangcong.simpledisplay.beans;

/**
 * 功能：数据点bean
 * <p>
 * 作者：Wang Cong
 * <p>
 * 时间：2018.12.5
 */
public class DataPoint {
    /**
     * 数据点名称
     */
    private String name;

    /**
     * 数据点值
     */
    private String value;

    /**
     * 产生数据点的时间戳
     */
    private String timestamp;

    public DataPoint(String name, String value, String timestamp) {
        this.name = name;
        this.value = value;
        this.timestamp = timestamp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "DataPoint{" +
                "name='" + name + '\'' +
                ", value='" + value + '\'' +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }
}
