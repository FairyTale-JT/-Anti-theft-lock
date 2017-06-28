package com.example.hjl.jgpushtest.beanClass;

/**
 * 防盗锁网络属性
 * Created by hjl on 2017/6/26.
 */

public class HttpFdsuo {
    private String deviceNo;//设备编号
    private String lockNo;//锁号
    private String stateEnum;//
    private int state;//状态标记编号
    private String stateName;//中文锁状态

    public void setDeviceNo(String deviceNo) {
        this.deviceNo = deviceNo;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public void setLockNo(String lockNo) {
        this.lockNo = lockNo;
    }

    public void setStateEnum(String stateEnum) {
        this.stateEnum = stateEnum;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getDeviceNo() {
        return deviceNo;
    }

    public String getLockNo() {
        return lockNo;
    }

    public String getStateEnum() {
        return stateEnum;
    }

    public int getState() {
        return state;
    }

    @Override
    public String toString() {
        return "HttpFdsuo{" +
                "deviceNo='" + deviceNo + '\'' +
                ", lockNo='" + lockNo + '\'' +
                ", stateEnum='" + stateEnum + '\'' +
                ", state=" + state +
                ", stateName='" + stateName + '\'' +
                '}';
    }
}
