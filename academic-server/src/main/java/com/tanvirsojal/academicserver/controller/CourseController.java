package com.tanvirsojal.academicserver.controller;

import com.tanvirsojal.academicserver.exception.ResourceAlreadyExistsException;
import com.tanvirsojal.academicserver.exception.ResourceDoesNotExistException;
import com.tanvirsojal.academicserver.model.Course;
import com.tanvirsojal.academicserver.service.CourseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/courses")
public class CourseController {
    private CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping("")
    public ResponseEntity<List<Course>> getAll(){
        List<Course> courseList = courseService.findAll();
        return ResponseEntity.ok(courseList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Course> getById(@PathVariable String id){
        try{
            Course course = courseService.findById(id);
            return ResponseEntity.ok(course);

        } catch (ResourceDoesNotExistException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping
    public ResponseEntity<Course> insert(@RequestBody Course course){
        try{
            Course insertedCourse = courseService.insert(course);
            return ResponseEntity.status(HttpStatus.CREATED).body(insertedCourse);
        } catch (ResourceAlreadyExistsException e){
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable String id){
        try{
            boolean deleted = courseService.deleteById(id);
            return ResponseEntity.ok(id);
        } catch (ResourceDoesNotExistException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Course> update(@PathVariable String id, @RequestBody Course course){
        try{
            Course updatedCourse = courseService.update(id, course);
            return ResponseEntity.ok(updatedCourse);
        } catch (ResourceDoesNotExistException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
