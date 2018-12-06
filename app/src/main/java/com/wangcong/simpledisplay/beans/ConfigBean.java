package com.wangcong.simpledisplay.beans;

/**
 * 功能：配置项bean
 * <p>
 * 作者：Wang Cong
 * <p>
 * 时间：2018.12.6
 */
public class ConfigBean {
    /**
     * 设备ID
     */
    private String deviceId;

    /**
     * 服务ID
     */
    private String serviceId;

    /**
     * 每次展示数据点数
     */
    private String count;


    /**
     * 数据刷新展示时间间隔
     */
    private String interval;

    public ConfigBean(String deviceId, String serviceId, String count, String interval) {
        this.deviceId = deviceId;
        this.serviceId = serviceId;
        this.count = count;
        this.interval = interval;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getInterval() {
        return interval;
    }

    public void setInterval(String interval) {
        this.interval = interval;
    }

    @Override
    public String toString() {
        return "ConfigBean{" +
                "deviceId='" + deviceId + '\'' +
                ", serviceId='" + serviceId + '\'' +
                ", count='" + count + '\'' +
                ", interval='" + interval + '\'' +
                '}';
    }
}
