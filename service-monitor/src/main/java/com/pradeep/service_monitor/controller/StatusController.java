package com.pradeep.service_monitor.controller;

import com.pradeep.service_monitor.config.MonitoringConfig;
import com.pradeep.service_monitor.model.ServiceStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;
import java.net.URL;
import java.net.Socket;
import java.net.InetSocketAddress;

@Controller
public class StatusController {

    @Autowired
    private MonitoringConfig config;

    @GetMapping("/status")
    public String getStatus(@RequestParam(required = false) String region,
                            @RequestParam(required = false) String country,
                            Model model) {

        // 1. Always provide the full list of regions for the first dropdown
        model.addAttribute("allRegions", config.getRegions());
        model.addAttribute("selectedRegion", region);
        model.addAttribute("selectedCountry", country);

        // 2. Filter available countries based on the selected region
        if (region != null && !region.isEmpty()) {
            config.getRegions().stream()
                    .filter(r -> r.getName().equals(region))
                    .findFirst()
                    .ifPresent(r -> model.addAttribute("availableCountries", r.getCountries()));
        }

        // 3. If both are selected, perform health checks on the URLs
        if (region != null && country != null && !country.isEmpty()) {
            List<ServiceStatus> results = config.getRegions().stream()
                    .filter(r -> r.getName().equals(region))
                    .flatMap(r -> r.getCountries().stream())
                    .filter(c -> c.getName().equals(country))
                    .flatMap(c -> c.getUrls().stream())
                    .map(service -> new ServiceStatus(
                            service.getName(),
                            service.getUrl(),
                            performSocketCheck(service.getUrl()) // Using the Socket fix
                    ))
                    .collect(Collectors.toList());

            model.addAttribute("services", results);
        }

        return "dashboard";
    }

    /**
     * Bypasses HTTP protocol issues and DNS proxy blocks by checking the raw port.
     */
    private String performSocketCheck(String urlString) {
        try {
            URL url = new URL(urlString);
            String host = url.getHost();
            int port = url.getPort() != -1 ? url.getPort() :
                    (url.getProtocol().equalsIgnoreCase("https") ? 443 : 80);

            try (Socket socket = new Socket()) {
                // Connect to the port directly
                socket.connect(new InetSocketAddress(host, port), 2000);
                return "UP";
            }
        } catch (Exception e) {
            return "DOWN";
        }
    }
}