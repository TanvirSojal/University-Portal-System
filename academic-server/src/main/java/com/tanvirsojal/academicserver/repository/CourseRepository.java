package com.tanvirsojal.academicserver.repository;

import com.tanvirsojal.academicserver.model.Course;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends MongoRepository<Course, String> {
}
