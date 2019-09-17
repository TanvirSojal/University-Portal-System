package com.tanvirsojal.studentserver.service;

import com.tanvirsojal.studentserver.exception.ResourceAlreadyExistsException;
import com.tanvirsojal.studentserver.exception.ResourceDoesNotExistException;
import com.tanvirsojal.studentserver.model.Student;
import com.tanvirsojal.studentserver.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    private StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    // find by id
    public Student findById(String id) throws ResourceDoesNotExistException {
        Optional<Student> optionalStudent = studentRepository.findById(id);
        if (optionalStudent.isPresent()){
            return optionalStudent.get();
        } else throw new ResourceDoesNotExistException(id + "");
    }

    // find all
    public List<Student> findAll(){
        List<Student> studentList = studentRepository.findAll();
        return studentList;
    }

    // insert
    public Student insert(Student student) throws ResourceAlreadyExistsException {
        Optional<Student> optionalStudent = studentRepository.findById(student.getId());
        if (optionalStudent.isPresent()){
            throw new ResourceAlreadyExistsException(student.getId() + "");
        } return studentRepository.save(student);
    }

    // delete by id
    public boolean deleteById(String id) throws ResourceDoesNotExistException {
        Optional<Student> optionalStudent = studentRepository.findById(id);
        if (optionalStudent.isPresent()){
            studentRepository.deleteById(id);
        } else throw new ResourceDoesNotExistException(id + "");
        return true;
    }

    // delete all
    public boolean deleteAll(){
        studentRepository.deleteAll();
        return true;
    }

    // update
    public Student update(String id, Student student) throws ResourceDoesNotExistException {
        Optional<Student> optionalStudent = studentRepository.findById(id);
        if (optionalStudent.isPresent()){
            student.setId(id);
            return studentRepository.save(student);
        } else throw new ResourceDoesNotExistException(id + "");
    }
}
