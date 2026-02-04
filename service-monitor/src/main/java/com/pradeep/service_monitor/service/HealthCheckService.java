package com.pradeep.service_monitor.service;

import com.pradeep.service_monitor.model.ServiceStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

@Service
public class HealthCheckService {

    public String checkStatus(String url) {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(3000); // 3 seconds timeout
            connection.connect();

            int code = connection.getResponseCode();
            return (code >= 200 && code < 400) ? "UP" : "DOWN";
        } catch (Exception e) {
            return "DOWN";
        }
    }
}
