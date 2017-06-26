package com.example.hjl.jgpushtest.enity;

import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;

/**
 * 防盗锁属性实体类
 * Created by hjl on 2017/6/14.
 */

public class FdSuo extends DataSupport {

    @Column(nullable = true)
    private int id;
    @Column(nullable = false)
    private String user;
    /**
     * 状态标记
     * 0：入库
     * 1：出库
     * 2：预加锁
     * 3：销号
     * 4：报废
     * 5: 加锁
     */
    @Column(nullable = false)
    private String suo_ztBJ;//状态标记

    @Column(nullable = false)
    private String suo_haoma;//锁号

    @Column(nullable = false)
    private String suo_sbBH;//设备编号

    /**
     * 1==出库
     * 2==已经编辑
     * -9==已使用
     */
    @Column(nullable = true)
    private int suo_isuse;//锁的使用状态

    @Column(nullable = true)
    private String suo_rkryMz;//入库人员用户名

    @Column(nullable = true)
    private String suo_rkczID;//入库车站ID

    @Column(nullable = true)
    private String suo_rkSJ;//入库时间

    @Column(nullable = true)
    private String suo_ckryMZ;//出库人员用户名

    @Column(nullable = true)
    private String suo_ckSJ;//出库时间

    @Column(nullable = true)
    private String suo_lqrMZ;//领取人用户名

    @Column(nullable = true)
    private String suo_bfryMZ;//报废人员用户名

    @Column(nullable = true)
    private String suo_bfSJ;//报废时间

    @Column(nullable = true)
    private String suo_sgID;//申购ID

    @Column(nullable = true)
    private String suo_LX;//锁类型0

    @Column(nullable = true)
    private String suo_cdTS;//充电天数


    public FdSuo() {
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "FdSuo{" +
                "id=" + id +
                ", user='" + user + '\'' +
                ", suo_ztBJ='" + suo_ztBJ + '\'' +
                ", suo_haoma='" + suo_haoma + '\'' +
                ", suo_sbBH='" + suo_sbBH + '\'' +
                ", suo_isuse=" + suo_isuse +
                ", suo_rkryMz='" + suo_rkryMz + '\'' +
                ", suo_rkczID='" + suo_rkczID + '\'' +
                ", suo_rkSJ='" + suo_rkSJ + '\'' +
                ", suo_ckryMZ='" + suo_ckryMZ + '\'' +
                ", suo_ckSJ='" + suo_ckSJ + '\'' +
                ", suo_lqrMZ='" + suo_lqrMZ + '\'' +
                ", suo_bfryMZ='" + suo_bfryMZ + '\'' +
                ", suo_bfSJ='" + suo_bfSJ + '\'' +
                ", suo_sgID='" + suo_sgID + '\'' +
                ", suo_LX='" + suo_LX + '\'' +
                ", suo_cdTS='" + suo_cdTS + '\'' +
                '}';
    }

    public FdSuo(String suo_haoma,
                 String suo_rkryMz,
                 String suo_rkczID,
                 String suo_rkSJ,
                 String suo_ztBJ,
                 String suo_ckryMZ,
                 String suo_ckSJ,
                 String suo_lqrMZ,
                 String suo_sbBH,
                 String suo_bfryMZ,
                 String suo_bfSJ,
                 String suo_sgID,
                 String suo_LX
    ) {
        this.suo_haoma = suo_haoma;
        this.suo_rkryMz = suo_rkryMz;
        this.suo_rkczID = suo_rkczID;
        this.suo_rkSJ = suo_rkSJ;
        this.suo_ztBJ = suo_ztBJ;
        this.suo_ckryMZ = suo_ckryMZ;
        this.suo_ckSJ = suo_ckSJ;
        this.suo_lqrMZ = suo_lqrMZ;
        this.suo_sbBH = suo_sbBH;
        this.suo_bfryMZ = suo_bfryMZ;
        this.suo_bfSJ = suo_bfSJ;
        this.suo_sgID = suo_sgID;
        this.suo_LX = suo_LX;
        this.suo_cdTS = suo_cdTS;
    }

    public int getSuo_isuse() {
        return suo_isuse;
    }

    public void setSuo_isuse(int suo_isuse) {
        this.suo_isuse = suo_isuse;
    }

    public void setSuo_haoma(String suo_haoma) {
        this.suo_haoma = suo_haoma;
    }

    public void setSuo_rkryMz(String suo_rkryMz) {
        this.suo_rkryMz = suo_rkryMz;
    }

    public void setSuo_rkczID(String suo_rkczID) {
        this.suo_rkczID = suo_rkczID;
    }

    public void setSuo_rkSJ(String suo_rkSJ) {
        this.suo_rkSJ = suo_rkSJ;
    }

    public void setSuo_ztBJ(String suo_ztBJ) {
        this.suo_ztBJ = suo_ztBJ;
    }

    public void setSuo_ckryMZ(String suo_ckryMZ) {
        this.suo_ckryMZ = suo_ckryMZ;
    }

    public void setSuo_ckSJ(String suo_ckSJ) {
        this.suo_ckSJ = suo_ckSJ;
    }

    public void setSuo_lqrMZ(String suo_lqrMZ) {
        this.suo_lqrMZ = suo_lqrMZ;
    }

    public void setSuo_sbBH(String suo_sbBH) {
        this.suo_sbBH = suo_sbBH;
    }

    public void setSuo_bfryMZ(String suo_bfryMZ) {
        this.suo_bfryMZ = suo_bfryMZ;
    }

    public void setSuo_bfSJ(String suo_bfSJ) {
        this.suo_bfSJ = suo_bfSJ;
    }

    public void setSuo_sgID(String suo_sgID) {
        this.suo_sgID = suo_sgID;
    }

    public void setSuo_LX(String suo_LX) {
        this.suo_LX = suo_LX;
    }

    public void setSuo_cdTS(String suo_cdTS) {
        this.suo_cdTS = suo_cdTS;
    }


    public String getSuo_haoma() {

        return suo_haoma;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSuo_rkryMz() {
        return suo_rkryMz;
    }

    public String getSuo_rkczID() {
        return suo_rkczID;
    }

    public String getSuo_rkSJ() {
        return suo_rkSJ;
    }

    public String getSuo_ztBJ() {
        return suo_ztBJ;
    }

    public String getSuo_ckryMZ() {
        return suo_ckryMZ;
    }

    public String getSuo_ckSJ() {
        return suo_ckSJ;
    }

    public String getSuo_lqrMZ() {
        return suo_lqrMZ;
    }

    public String getSuo_sbBH() {
        return suo_sbBH;
    }

    public String getSuo_bfryMZ() {
        return suo_bfryMZ;
    }

    public String getSuo_bfSJ() {
        return suo_bfSJ;
    }

    public String getSuo_sgID() {
        return suo_sgID;
    }

    public String getSuo_LX() {
        return suo_LX;
    }

    public String getSuo_cdTS() {
        return suo_cdTS;
    }


}
