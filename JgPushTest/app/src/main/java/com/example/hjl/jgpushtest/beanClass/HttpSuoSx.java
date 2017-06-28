package com.example.hjl.jgpushtest.beanClass;

/**
 * Created by hjl on 2017/6/27.
 */

public class HttpSuoSx {
    private String lockDeviceNo;//设备号
    private String lockNo;//锁号
    private String state;//锁状态
   private String stateName;//中文锁状态
    private  int order;//

    @Override
    public String toString() {
        return "HttpSuoSx{" +
                "lockDeviceNo='" + lockDeviceNo + '\'' +
                ", lockNo='" + lockNo + '\'' +
                ", state='" + state + '\'' +
                ", stateName='" + stateName + '\'' +
                ", order=" + order +
                '}';
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getStateName() {
        return stateName;
    }

    public void setLockDeviceNo(String lockDeviceNo) {
        this.lockDeviceNo = lockDeviceNo;
    }

    public void setLockNo(String lockNo) {
        this.lockNo = lockNo;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getLockDeviceNo() {
        return lockDeviceNo;
    }

    public String getLockNo() {
        return lockNo;
    }

    public String getState() {
        return state;
    }

    public int getOrder() {
        return order;
    }
}
