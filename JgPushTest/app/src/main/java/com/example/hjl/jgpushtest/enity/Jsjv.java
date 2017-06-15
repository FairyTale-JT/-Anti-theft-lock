package com.example.hjl.jgpushtest.enity;

/**
 * Created by Administrator on 2017/6/13.
 */

public class Jsjv {
    private String cxh;//车号
    private String fz;//发站
    private String dz;//到站

    private String suo1;//锁1
    private String suo2;//锁2
    private String szt;//锁状态

    private String jlh;//记录号
    private String sbh;//设备号
    private String ccpc;//出厂批次
    private String cdts;//充电天数
    private FdSuo fdSuo1;//
    private FdSuo fdSuo2;//

    public FdSuo getFdSuo1() {
        return fdSuo1;
    }

    public void setFdSuo1(FdSuo fdSuo) {
        this.fdSuo1 = fdSuo;
    }
    public FdSuo getFdSuo2() {
        return fdSuo2;
    }

    public void setFdSuo2(FdSuo fdSuo) {
        this.fdSuo2 = fdSuo;
    }
    public String getJlh() {
        return jlh;
    }

    public void setJlh(String jlh) {
        this.jlh = jlh;
    }

    public String getSbh() {
        return sbh;
    }

    public void setSbh(String sbh) {
        this.sbh = sbh;
    }

    public String getCcpc() {
        return ccpc;
    }

    public void setCcpc(String ccpc) {
        this.ccpc = ccpc;
    }

    public String getCdts() {
        return cdts;
    }

    public void setCdts(String cdts) {
        this.cdts = cdts;
    }


    public String getFz() {
        return fz;
    }

    public void setFz(String fz) {
        this.fz = fz;
    }

    public String getDz() {
        return dz;
    }

    public void setDz(String dz) {
        this.dz = dz;
    }


    public String getCxh() {
        return cxh;
    }

    public void setCxh(String cxh) {
        this.cxh = cxh;
    }

    public String getSuo1() {
        return suo1;
    }

    public void setSuo1(String suo1) {
        this.suo1 = suo1;
    }

    public String getSuo2() {
        return suo2;
    }

    public void setSuo2(String suo2) {
        this.suo2 = suo2;
    }

    public String getSzt() {
        return szt;
    }

    public void setSzt(String szt) {
        this.szt = szt;
    }
}
