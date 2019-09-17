package com.tanvirsojal.academicserver.service;

import com.tanvirsojal.academicserver.exception.ResourceAlreadyExistsException;
import com.tanvirsojal.academicserver.exception.ResourceDoesNotExistException;
import com.tanvirsojal.academicserver.model.Course;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CourseServiceTest {
    @Autowired
    private CourseService courseService;

    @Test
    public void insertTest() throws ResourceAlreadyExistsException {
        Course course = new Course("CSE4047", "Advanced Java", 3.0, 1, "BSc in CSE");
        Course insert = courseService.insert(course);
        System.out.println(insert);
    }

    @Test
    public void findAll(){
        List<Course> all = courseService.findAll();
        all.forEach(System.out::println);
    }

    @Test
    public void update() throws ResourceDoesNotExistException {
        Course course = new Course("CSE1011", "Advanced C", 3.0, 1, "BSc in CSE");
        Course cse1011 = courseService.update("CSE1011", course);
        System.out.println(cse1011);
    }
}
