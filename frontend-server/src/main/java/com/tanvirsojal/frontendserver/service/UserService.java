package com.tanvirsojal.frontendserver.service;

import com.tanvirsojal.frontendserver.model.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class UserService {
    @Value("${student-url}/students")
    private String studentUrl;
    @Value("${hr-url}/employees")
    private String hrUrl;
    @Value("${academic-url}/programs")
    private String programUrl;
    @Value("${academic-url}/courses")
    private String courseUrl;
    @Value(("${convocation-url}/convocations"))
    private String convocationUrl;

    private RestTemplate restTemplate;

    public UserService(RestTemplate restTemplate) {
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
        return savedStudent;
    }

    public Student updateStudent(Student student){
        HttpEntity<Student> requestUpdate = new HttpEntity<>(student);
        restTemplate.exchange(studentUrl + "/" + student.getId(), HttpMethod.PUT, requestUpdate, Void.class);
        return getStudent(student.getId());
    }


    public Employee getEmployee(String id){
        Employee employee = restTemplate.getForObject(hrUrl + "/" + id, Employee.class);
        return employee;
    }

    public Employee saveEmployee(Employee employee){
        HttpEntity<Employee> request = new HttpEntity<>(employee);
        Employee savedEmployee = restTemplate.postForObject(hrUrl, request, Employee.class);
        return savedEmployee;
    }

    public List<Employee> getEmployees(){
        ResponseEntity<List<Employee>> response = restTemplate.exchange(
                hrUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Employee>>(){});
        List<Employee> employeeList = response.getBody();
        return employeeList;
    }

    public Employee updateEmployee(Employee employee){
        HttpEntity<Employee> requestUpdate = new HttpEntity<>(employee);
        restTemplate.exchange(hrUrl + "/" + employee.getId(), HttpMethod.PUT, requestUpdate, Void.class);
        return getEmployee(employee.getId());
    }

    public Program getProgram(int id){
        System.out.println(programUrl + "/" + id);
        Program program = restTemplate.getForObject(programUrl + "/" + id, Program.class);
        return program;
    }

    public List<Program> getPrograms(){
        ResponseEntity<List<Program>> response = restTemplate.exchange(
                programUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Program>>(){});
        List<Program> programList = response.getBody();
        return programList;
    }

    public Program saveProgram(Program program){
        HttpEntity<Program> request = new HttpEntity<>(program);
        Program savedProgram = restTemplate.postForObject(programUrl, request, Program.class);
        return savedProgram;
    }

    public Program updateProgram(Program program){
        HttpEntity<Program> requestUpdate = new HttpEntity<>(program);
        restTemplate.exchange(programUrl + "/" + program.getId(), HttpMethod.PUT, requestUpdate, Void.class);
//        Program updatedProgram = restTemplate.patchForObject(programUrl + "/" + program.getId(), requestUpdate, Program.class);
        return getProgram(program.getId());
    }

    public List<Course> getCourses(){
        ResponseEntity<List<Course>> response = restTemplate.exchange(
                courseUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Course>>(){});
        List<Course> courseList = response.getBody();
        return courseList;
    }

    public Course saveCourse(Course course){

        Program program = getProgram(course.getProgramId());
        program.getCourseList().add(course);
        updateProgram(program);

        HttpEntity<Course> request = new HttpEntity<>(course);
        Course savedCourse = restTemplate.postForObject(courseUrl, request, Course.class);
        return savedCourse;
    }

    public List<Convocation> getConvocations(){
        ResponseEntity<List<Convocation>> response = restTemplate.exchange(
                convocationUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Convocation>>(){});
        List<Convocation> convocationList = response.getBody();
        return convocationList;
    }

    public Convocation getConvocation(int id){
        Convocation convocation = restTemplate.getForObject(convocationUrl + "/" + id, Convocation.class);
        return convocation;
    }

    public Convocation saveConvocation(Convocation convocation){
        HttpEntity<Convocation> request = new HttpEntity<>(convocation);
        Convocation savedConvocation = restTemplate.postForObject(convocationUrl, request, Convocation.class);
        return savedConvocation;
    }

    public Convocation updateConvocation(Convocation convocation){
        HttpEntity<Convocation> requestUpdate = new HttpEntity<>(convocation);
        restTemplate.exchange(convocationUrl + "/" + convocation.getId(), HttpMethod.PUT, requestUpdate, Void.class);
//        Program updatedProgram = restTemplate.patchForObject(programUrl + "/" + program.getId(), requestUpdate, Program.class);
        return getConvocation(convocation.getId());

    }
}
