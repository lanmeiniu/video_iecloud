package com.iecloud.pojo;

import java.util.Date;

public class Video {
    private Integer id;

    private String title;

    private String detail;

    private String videoImageAddress;

    private String videoAddress;

    private Date createTime;

    private Date updateTime;

    public Video(Integer id, String title, String detail, String videoImageAddress, String videoAddress, Date createTime, Date updateTime) {
        this.id = id;
        this.title = title;
        this.detail = detail;
        this.videoImageAddress = videoImageAddress;
        this.videoAddress = videoAddress;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public Video() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail == null ? null : detail.trim();
    }

    public String getVideoImageAddress() {
        return videoImageAddress;
    }

    public void setVideoImageAddress(String videoImageAddress) {
        this.videoImageAddress = videoImageAddress == null ? null : videoImageAddress.trim();
    }

    public String getVideoAddress() {
        return videoAddress;
    }

    public void setVideoAddress(String videoAddress) {
        this.videoAddress = videoAddress == null ? null : videoAddress.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}