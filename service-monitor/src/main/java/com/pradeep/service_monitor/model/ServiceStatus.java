package com.pradeep.service_monitor.model;

public class ServiceStatus {
    private String name;
    private String url;
    private String status;
    private String message;

    public ServiceStatus(String name, String url, String status, String message) {
        this.name = name;
        this.url = url;
        this.status = status;
        this.message = message;
    }

    public String getName() { return name; }
    public String getUrl() { return url; }
    public String getStatus() { return status; }
    public String getMessage() { return message; }
}
