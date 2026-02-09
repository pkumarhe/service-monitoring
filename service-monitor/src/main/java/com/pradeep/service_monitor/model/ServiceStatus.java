package com.pradeep.service_monitor.model;

public class ServiceStatus {
    private String name;
    private String url;
    private String status;

    public ServiceStatus(String name, String url, String status) {
        this.name = name;
        this.url = url;
        this.status = status;
    }
    // Getters
    public String getName() { return name; }
    public String getUrl() { return url; }
    public String getStatus() { return status; }
}