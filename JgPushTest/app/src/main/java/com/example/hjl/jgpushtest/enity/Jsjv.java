package com.example.hjl.jgpushtest.enity;

import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;

/**
 * 锁与车/箱号 任务链 实体类
 * Created by Administrator on 2017/6/13.
 */

public class Jsjv extends DataSupport {
    @Column(nullable = true)
    private int id;

    @Column(nullable = false)
    private int isOk;//该任务链状态（-1=已经提交，1=暂没提交缓存在本地）

    @Column(nullable = false)
    private String cxh;//车号

    @Column(nullable = false)
    private String fz;//发站

    @Column(nullable = false)
    private String dz;//到站

    @Column(nullable = false)
    private String fdSuo1_id;//
    @Column(nullable = false)
    private String fdSuo1_sbbh;//
    @Column(nullable = false)
    private String fdSuo1_ztbj;//

    @Column(nullable = true)
    private String fdSuo2_id;//
    @Column(nullable = true)
    private String fdSuo2_sbbh;//
    @Column(nullable = false)
    private String fdSuo2_ztbj;//

    @Column(nullable = true)
    private String cxType;//车类型（整车或者车厢）

    public int getIsOk() {
        return isOk;
    }

    public void setIsOk(int isOk) {
        this.isOk = isOk;
    }

    public String getCxType() {
        return cxType;
    }

    public void setCxType(String cxType) {
        this.cxType = cxType;
    }

    public String getFdSuo1_id() {
        return fdSuo1_id;
    }

    public String getFdSuo1_sbbh() {
        return fdSuo1_sbbh;
    }

    public String getFdSuo2_id() {
        return fdSuo2_id;
    }

    public String getFdSuo2_sbbh() {
        return fdSuo2_sbbh;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFdSuo1_id(String fdSuo1_id) {
        this.fdSuo1_id = fdSuo1_id;
    }

    public void setFdSuo1_sbbh(String fdSuo1_sbbh) {
        this.fdSuo1_sbbh = fdSuo1_sbbh;
    }

    public void setFdSuo2_id(String fdSuo2_id) {
        this.fdSuo2_id = fdSuo2_id;
    }

    public void setFdSuo2_sbbh(String fdSuo2_sbbh) {
        this.fdSuo2_sbbh = fdSuo2_sbbh;
    }

    public String getFdSuo1_ztbj() {
        return fdSuo1_ztbj;
    }

    public String getFdSuo2_ztbj() {
        return fdSuo2_ztbj;
    }

    public void setFdSuo1_ztbj(String fdSuo1_ztbj) {
        this.fdSuo1_ztbj = fdSuo1_ztbj;
    }

    public void setFdSuo2_ztbj(String fdSuo2_ztbj) {
        this.fdSuo2_ztbj = fdSuo2_ztbj;
    }
}
