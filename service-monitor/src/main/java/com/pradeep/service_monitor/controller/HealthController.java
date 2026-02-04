package com.pradeep.service_monitor.controller;

import com.pradeep.service_monitor.config.MonitoringConfig;
import com.pradeep.service_monitor.service.HealthCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class HealthController {

    @Autowired
    private MonitoringConfig config;

    @Autowired
    private HealthCheckService healthService;

    @GetMapping("/")
    public String getDashboard(Model model) {
        // Iterate over the config list, check status, and map to a list of Maps
        List<Map<String, String>> serviceStatuses = config.getServices().stream().map(service -> {
            Map<String, String> map = new HashMap<>();
            map.put("name", service.getName());
            map.put("url", service.getUrl());

            // Perform the check
            String status = healthService.checkStatus(service.getUrl());
            map.put("status", status);

            return map;
        }).collect(Collectors.toList());

        // Pass the list to the Thymeleaf view
        model.addAttribute("services", serviceStatuses);

        return "dashboard"; // This looks for dashboard.html in templates folder
    }
}
