package com.tanvirsojal.humanresourcesserver.repository;

import com.tanvirsojal.humanresourcesserver.model.Employee;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends MongoRepository<Employee, String> {
}
