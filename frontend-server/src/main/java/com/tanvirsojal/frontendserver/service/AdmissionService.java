package com.tanvirsojal.frontendserver.service;

import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

public class AdmissionService {
    private RestTemplate restTemplate;

    public AdmissionService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
}
