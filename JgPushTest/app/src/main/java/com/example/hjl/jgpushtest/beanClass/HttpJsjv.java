package com.example.hjl.jgpushtest.beanClass;

import java.util.List;

/**
 * Created by hjl on 2017/6/27.
 */

public class HttpJsjv {
    private String coachNo;//车号
    private String sealOrgCode;//
    private String startStationId;//发站DBM
    private String startStationName;//发站名
    private String destStationId;//到站DBM
    private  String destStationName;//到站名
    private List<HttpSuoSx>  details;//锁属性

    @Override
    public String toString() {
        return "HttpJsjv{" +
                "coachNo='" + coachNo + '\'' +
                ", sealOrgCode='" + sealOrgCode + '\'' +
                ", startStationId='" + startStationId + '\'' +
                ", startStationName='" + startStationName + '\'' +
                ", destStationId='" + destStationId + '\'' +
                ", destStationName='" + destStationName + '\'' +
                ", details=" + details +
                '}';
    }

    public void setCoachNo(String coachNo) {
        this.coachNo = coachNo;
    }

    public void setDetails(List<HttpSuoSx> details) {
        this.details = details;
    }

    public String getCoachNo() {
        return coachNo;
    }

    public List<HttpSuoSx> getDetails() {
        return details;
    }

    public void setSealOrgCode(String sealOrgCode) {
        this.sealOrgCode = sealOrgCode;
    }

    public void setStartStationId(String startStationId) {
        this.startStationId = startStationId;
    }

    public void setStartStationName(String startStationName) {
        this.startStationName = startStationName;
    }

    public void setDestStationId(String destStationId) {
        this.destStationId = destStationId;
    }

    public void setDestStationName(String destStationName) {
        this.destStationName = destStationName;
    }


    public String getSealOrgCode() {
        return sealOrgCode;
    }

    public String getStartStationId() {
        return startStationId;
    }

    public String getStartStationName() {
        return startStationName;
    }

    public String getDestStationId() {
        return destStationId;
    }

    public String getDestStationName() {
        return destStationName;
    }
}
