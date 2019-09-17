package com.tanvirsojal.convocationserver.controller;

import com.tanvirsojal.convocationserver.exception.ResourceAlreadyExistsException;
import com.tanvirsojal.convocationserver.exception.ResourceDoesNotExistException;
import com.tanvirsojal.convocationserver.model.Convocation;
import com.tanvirsojal.convocationserver.service.ConvocationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/convocations")
public class ConvocationController {
    private ConvocationService convocationService;

    public ConvocationController(ConvocationService convocationService) {
        this.convocationService = convocationService;
    }

    @GetMapping("")
    public ResponseEntity<List<Convocation>> getAll(){
        List<Convocation> convocationList = convocationService.findAll();
        return ResponseEntity.ok(convocationList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Convocation> getById(@PathVariable int id){
        try{
            Convocation convocation = convocationService.findById(id);
            return ResponseEntity.ok(convocation);

        } catch (ResourceDoesNotExistException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping
    public ResponseEntity<Convocation> insert(@RequestBody Convocation convocation){
        try{
            Convocation insertedConvocation = convocationService.insert(convocation);
            return ResponseEntity.status(HttpStatus.CREATED).body(insertedConvocation);
        } catch (ResourceAlreadyExistsException e){
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Integer> deleteById(@PathVariable int id){
        try{
            boolean deleted = convocationService.deleteById(id);
            return ResponseEntity.ok(id);
        } catch (ResourceDoesNotExistException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Convocation> update(@PathVariable int id, @RequestBody Convocation convocation){
        try{
            Convocation updatedConvocation = convocationService.update(id, convocation);
            return ResponseEntity.ok(updatedConvocation);
        } catch (ResourceDoesNotExistException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
