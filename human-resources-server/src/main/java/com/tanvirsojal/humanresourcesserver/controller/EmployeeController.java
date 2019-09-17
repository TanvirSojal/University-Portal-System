package com.tanvirsojal.humanresourcesserver.controller;

import com.tanvirsojal.humanresourcesserver.exception.ResourceAlreadyExistsException;
import com.tanvirsojal.humanresourcesserver.exception.ResourceDoesNotExistException;
import com.tanvirsojal.humanresourcesserver.model.Employee;
import com.tanvirsojal.humanresourcesserver.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeController {
    private EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("")
    public ResponseEntity<List<Employee>> getAll(){
        List<Employee> employeeList = employeeService.findAll();
        return ResponseEntity.ok(employeeList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getById(@PathVariable String id){
        try{
            Employee employee = employeeService.findById(id);
            return ResponseEntity.ok(employee);

        } catch (ResourceDoesNotExistException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping
    public ResponseEntity<Employee> insert(@RequestBody Employee employee){
        try{
            Employee insertedEmployee = employeeService.insert(employee);
            return ResponseEntity.status(HttpStatus.CREATED).body(insertedEmployee);
        } catch (ResourceAlreadyExistsException e){
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable String id){
        try{
            boolean deleted = employeeService.deleteById(id);
            return ResponseEntity.ok(id);
        } catch (ResourceDoesNotExistException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> update(@PathVariable String id, @RequestBody Employee employee){
        try{
            Employee updatedEmployee = employeeService.update(id, employee);
            return ResponseEntity.ok(updatedEmployee);
        } catch (ResourceDoesNotExistException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
