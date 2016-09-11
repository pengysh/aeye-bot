package com.a.eye.bot.interfaces.share.entity;

public class MeetingRoom implements java.io.Serializable{
    private Integer id;

    private String name;

    private String capacity;

    private String hasprojector;

    private String region;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity == null ? null : capacity.trim();
    }

    public String getHasprojector() {
        return hasprojector;
    }

    public void setHasprojector(String hasprojector) {
        this.hasprojector = hasprojector == null ? null : hasprojector.trim();
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region == null ? null : region.trim();
    }
}