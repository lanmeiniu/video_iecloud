package com.iecloud.pojo;

public class HistoricalRecord {
    private Integer id;

    private String phoneNumber;

    private String title;

    private String videoAddress;

    private String detail;

    private Long currentVideoTime;

    public HistoricalRecord(Integer id, String phoneNumber, String title, String videoAddress, String detail, Long currentVideoTime) {
        this.id = id;
        this.phoneNumber = phoneNumber;
        this.title = title;
        this.videoAddress = videoAddress;
        this.detail = detail;
        this.currentVideoTime = currentVideoTime;
    }

    public HistoricalRecord() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber == null ? null : phoneNumber.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getVideoAddress() {
        return videoAddress;
    }

    public void setVideoAddress(String videoAddress) {
        this.videoAddress = videoAddress == null ? null : videoAddress.trim();
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail == null ? null : detail.trim();
    }

    public Long getCurrentVideoTime() {
        return currentVideoTime;
    }

    public void setCurrentVideoTime(Long currentVideoTime) {
        this.currentVideoTime = currentVideoTime;
    }
}