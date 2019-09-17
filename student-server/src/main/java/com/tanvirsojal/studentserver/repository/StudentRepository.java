package com.tanvirsojal.studentserver.repository;

import com.tanvirsojal.studentserver.model.Student;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends MongoRepository<Student, String> {
}
