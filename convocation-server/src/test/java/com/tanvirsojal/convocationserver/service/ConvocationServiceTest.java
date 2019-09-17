package com.tanvirsojal.convocationserver.service;

import com.tanvirsojal.convocationserver.exception.ResourceAlreadyExistsException;
import com.tanvirsojal.convocationserver.model.Convocation;
import com.tanvirsojal.convocationserver.model.Program;
import com.tanvirsojal.convocationserver.model.Student;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ConvocationServiceTest {
    @Autowired
    private ConvocationService convocationService;
    @Test
    public void insertTest() throws ResourceAlreadyExistsException {
        Student studnet = new Student("123", "Oliver Queen", 43, new Program(), LocalDate.of(2016, Month.APRIL, 15), 0, 0.0, new ArrayList<>(), new ArrayList<>(), "abc@email.com");
        Convocation convocation = new Convocation(1, studnet, "paid", "pending");
        convocationService.insert(convocation);
    }

    @Test
    public void findAll(){
        List<Convocation> all = convocationService.findAll();
        all.forEach(System.out::println);
    }
}
