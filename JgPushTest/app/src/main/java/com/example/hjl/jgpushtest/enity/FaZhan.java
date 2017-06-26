package com.example.hjl.jgpushtest.enity;

import org.litepal.annotation.Column;

/**
 * Created by Administrator on 2017/6/19.
 */

public class FaZhan {
    @Column(nullable = true)
    private int  id;

    @Column(nullable = false)
    private String fz;

    @Column(nullable = false)
    private String fzDBM;

    @Column(nullable = false)
    private String user;

    public String getFzDBM() {
        return fzDBM;
    }

    public void setFzDBM(String fzDBM) {
        this.fzDBM = fzDBM;
    }

    public FaZhan(String fz) {
        this.fz = fz;
    }

    public String getFz() {
        return fz;
    }

    public void setFz(String fz) {
        this.fz = fz;
    }

    public FaZhan(String fz, String fzDBM) {
        this.fz = fz;
        this.fzDBM = fzDBM;
    }

    public int getId() {
        return id;
    }

    public String getUser() {
        return user;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUser(String user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "FaZhan{" +
                "id=" + id +
                ", fz='" + fz + '\'' +
                ", fzDBM='" + fzDBM + '\'' +
                ", user='" + user + '\'' +
                '}';
    }
}
