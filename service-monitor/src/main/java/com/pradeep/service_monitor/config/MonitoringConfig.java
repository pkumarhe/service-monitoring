package com.pradeep.service_monitor.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@Component
@ConfigurationProperties(prefix = "app.monitoring")
public class MonitoringConfig {
    private List<Region> regions;

    public static class Region {
        private String name;
        private List<Country> countries;
        // Getters and Setters
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public List<Country> getCountries() { return countries; }
        public void setCountries(List<Country> countries) { this.countries = countries; }

    }

    public static class Country {
        private String name;
        private List<ServiceInfo> urls;
        // Getters and Setters
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public List<ServiceInfo> getUrls() { return urls; }
        public void setUrls(List<ServiceInfo> urls) { this.urls = urls; }
    }

    public static class ServiceInfo {
        private String name;
        private String url;
        // Getters and Setters
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getUrl() { return url; }
        public void setUrl(String url) { this.url = url; }
    }

    public List<Region> getRegions() { return regions; }
    public void setRegions(List<Region> regions) { this.regions = regions; }
}