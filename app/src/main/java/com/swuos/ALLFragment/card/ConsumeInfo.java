package com.swuos.ALLFragment.card;

import java.io.Serializable;

/**
 * Created by codekk on 2016/5/3.
 * Email:  645326280@qq.com
 */
public class ConsumeInfo implements Serializable{
    private String time;
    private String kind;
    private String times;
    private String  before;
    private String  delta;
    private String  after;
    private String  address;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getTimes() {
        return times;
    }

    public void setTimes(String times) {
        this.times = times;
    }

    public String getBefore() {
        return before;
    }

    public void setBefore(String before) {
        this.before = before;
    }

    public String getDelta() {
        return delta;
    }

    public void setDelta(String delta) {
        this.delta = delta;
    }

    public String getAfter() {
        return after;
    }

    public void setAfter(String after) {
        this.after = after;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
