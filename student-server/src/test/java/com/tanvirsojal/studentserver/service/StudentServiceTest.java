package com.tanvirsojal.studentserver.repository;

import com.tanvirsojal.studentserver.exception.ResourceAlreadyExistsException;
import com.tanvirsojal.studentserver.model.Program;
import com.tanvirsojal.studentserver.model.Student;
import com.tanvirsojal.studentserver.service.StudentService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StudentServiceTest {
    @Autowired
    private StudentService studentService;

//    @Test
//    public void testSave() throws ResourceAlreadyExistsException {
//        Student studnet = new Student("123", "Oliver Queen", 43, new Program(), LocalDate.of(2016, Month.APRIL, 15), 0, 0.0, new ArrayList<>(), "abc@email.com");
//        System.out.println(studentService.insert(studnet));
//    }
}
