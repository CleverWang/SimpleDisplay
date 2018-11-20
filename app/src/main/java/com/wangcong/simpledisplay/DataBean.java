package com.wangcong.simpledisplay;

public class DataBean {

    private Float tem; // 温度
    private Float hum; // 湿度
    private String tem_timestamp; // 温度时间戳
    private String hum_timestamp; // 湿度时间戳


    public DataBean() {
    }

    public DataBean(Float tem, String tem_timestamp, Float hum, String hum_timestamp) {
        this.tem = tem;
        this.hum = hum;
        this.tem_timestamp = tem_timestamp;
        this.hum_timestamp = hum_timestamp;
    }

    public DataBean(Float tem, Float hum) {
        this.tem = tem;
        this.hum = hum;
    }

    public Float getTem() {
        return tem;
    }

    public void setTem(Float tem) {
        this.tem = tem;
    }

    public Float getHum() {
        return hum;
    }

    public void setHum(Float hum) {
        this.hum = hum;
    }

    public String getTem_timestamp() {
        return tem_timestamp;
    }

    public void setTem_timestamp(String tem_timestamp) {
        this.tem_timestamp = tem_timestamp;
    }

    public String getHum_timestamp() {
        return hum_timestamp;
    }

    public void setHum_timestamp(String hum_timestamp) {
        this.hum_timestamp = hum_timestamp;
    }
}