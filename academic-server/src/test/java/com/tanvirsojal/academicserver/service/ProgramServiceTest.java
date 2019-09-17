package com.tanvirsojal.academicserver.service;

import com.tanvirsojal.academicserver.exception.ResourceAlreadyExistsException;
import com.tanvirsojal.academicserver.exception.ResourceDoesNotExistException;
import com.tanvirsojal.academicserver.model.Course;
import com.tanvirsojal.academicserver.model.Program;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProgramServiceTest {
    @Autowired
    private ProgramService programService;

    @Test
    public void insertTest() throws ResourceAlreadyExistsException {

    }

    @Test
    public void findAll(){

    }

    @Test
    public void update() throws ResourceDoesNotExistException {
        Program program = new Program(1, "BSc in CSE", "kmh", 2.6, 144, new ArrayList<>());
        Course course = new Course("CSE1011", "Advanced C", 3.0, 1, "BSc in CSE");
        program.getCourseList().add(course);
        Program update = programService.update(1, program);
        System.out.println(update);
    }
}
