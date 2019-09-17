package com.tanvirsojal.frontendserver.service;

import com.tanvirsojal.frontendserver.model.Program;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ProgramService {
    @Value("${academic-url}/programs")
    private String programUrl;
    @Value("@{academic-url}/courses")
    private String courseUrl;

    private RestTemplate restTemplate;

    public ProgramService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Program getProgram(int id){
        System.out.println(programUrl + "/" + id);
        Program program = restTemplate.getForObject(programUrl + "/" + id, Program.class);
        return program;
    }
}
