package com.tanvirsojal.frontendserver.service;

import com.tanvirsojal.frontendserver.model.Employee;
import com.tanvirsojal.frontendserver.model.Student;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {
    @Autowired
    private UserService userService;

    @Test
    public void getStudent(){
        String id = "163";
        Student student = userService.getStudent(id);
        assertEquals(id, student.getId());
    }

    @Test
    public void getEmployee(){
        String id = "john";
        Employee employee = userService.getEmployee(id);
        assertEquals(id, employee.getId());
    }
}
