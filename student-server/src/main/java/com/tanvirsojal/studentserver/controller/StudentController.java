package com.tanvirsojal.studentserver.controller;

import com.tanvirsojal.studentserver.exception.ResourceAlreadyExistsException;
import com.tanvirsojal.studentserver.exception.ResourceDoesNotExistException;
import com.tanvirsojal.studentserver.model.Student;
import com.tanvirsojal.studentserver.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/students")
public class StudentController {
    private StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("")
    public ResponseEntity<List<Student>> getAll(){
        List<Student> studentList = studentService.findAll();
        return ResponseEntity.ok(studentList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getById(@PathVariable String id){
        try{
            Student student = studentService.findById(id);
            return ResponseEntity.ok(student);

        } catch (ResourceDoesNotExistException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping
    public ResponseEntity<Student> insert(@RequestBody Student student){
        try{
            Student insertedStudent = studentService.insert(student);
            return ResponseEntity.status(HttpStatus.CREATED).body(insertedStudent);
        } catch (ResourceAlreadyExistsException e){
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable String id){
        try{
            boolean deleted = studentService.deleteById(id);
            return ResponseEntity.ok(id);
        } catch (ResourceDoesNotExistException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Student> update(@PathVariable String id, @RequestBody Student student){
        try{
            Student updatedStudent = studentService.update(id, student);
            return ResponseEntity.ok(updatedStudent);
        } catch (ResourceDoesNotExistException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
