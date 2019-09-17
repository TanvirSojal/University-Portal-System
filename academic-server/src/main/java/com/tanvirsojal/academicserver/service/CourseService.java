package com.tanvirsojal.academicserver.service;

import com.tanvirsojal.academicserver.exception.ResourceAlreadyExistsException;
import com.tanvirsojal.academicserver.exception.ResourceDoesNotExistException;
import com.tanvirsojal.academicserver.model.Course;
import com.tanvirsojal.academicserver.repository.CourseRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {
    private CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    // find by id
    public Course findById(String code) throws ResourceDoesNotExistException {
        Optional<Course> optionalCourse = courseRepository.findById(code);
        if (optionalCourse.isPresent()){
            return optionalCourse.get();
        } else throw new ResourceDoesNotExistException(code + "");
    }

    // find all
    public List<Course> findAll(){
        List<Course> courseList = courseRepository.findAll();
        return courseList;
    }

    // insert
    public Course insert(Course course) throws ResourceAlreadyExistsException {
        Optional<Course> optionalCourse = courseRepository.findById(course.getCode());
        if (optionalCourse.isPresent()){
            throw new ResourceAlreadyExistsException(course.getCode() + "");
        } return courseRepository.save(course);
    }

    // delete by id
    public boolean deleteById(String code) throws ResourceDoesNotExistException {
        Optional<Course> optionalCourse = courseRepository.findById(code);
        if (optionalCourse.isPresent()){
            courseRepository.deleteById(code);
        } else throw new ResourceDoesNotExistException(code + "");
        return true;
    }

    // delete all
    public boolean deleteAll(){
        courseRepository.deleteAll();
        return true;
    }

    // update
    public Course update(String code, Course course) throws ResourceDoesNotExistException {
        Optional<Course> optionalCourse = courseRepository.findById(code);
        if (optionalCourse.isPresent()){
            course.setCode(code);
            return courseRepository.save(course);
        } else throw new ResourceDoesNotExistException(code + "");
    }
}
