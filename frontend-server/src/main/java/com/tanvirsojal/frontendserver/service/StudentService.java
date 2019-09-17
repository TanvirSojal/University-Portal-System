package com.tanvirsojal.frontendserver.service;

import com.tanvirsojal.frontendserver.model.Student;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class StudentService {
    @Value("${student-url}/students")
    private String studentUrl;


    private RestTemplate restTemplate;

    public StudentService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Student getStudent(String id){
        System.out.println(studentUrl + "/" + id);
        Student student = restTemplate.getForObject(studentUrl + "/" + id, Student.class);
        return student;
    }

    public List<Student> getStudents(){
        ResponseEntity<List<Student>> response = restTemplate.exchange(
                studentUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Student>>(){});
        List<Student> studentList = response.getBody();
        return studentList;
    }

    public Student saveStudent(Student student){
        HttpEntity<Student> request = new HttpEntity<>(student);
        Student savedStudent = restTemplate.postForObject(studentUrl, request, Student.class);
        return student;
    }
}
