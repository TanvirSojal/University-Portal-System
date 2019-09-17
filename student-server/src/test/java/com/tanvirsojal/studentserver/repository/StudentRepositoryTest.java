package com.tanvirsojal.studentserver.repository;

import com.tanvirsojal.studentserver.model.Program;
import com.tanvirsojal.studentserver.model.Student;
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
public class StudentRepositoryTest {
    @Autowired
    private StudentRepository studentRepository;

//    @Test
//    public void testSave(){
//        Student studnet = new Student("123", "Oliver Queen", 43, new Program(), LocalDate.of(2016, Month.APRIL, 15), 0, 0.0, new ArrayList<>(), "abc@email.com");
//        System.out.println(studentRepository.save(studnet));
//    }
}
