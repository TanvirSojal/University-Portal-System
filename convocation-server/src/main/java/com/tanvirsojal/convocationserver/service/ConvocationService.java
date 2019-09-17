package com.tanvirsojal.convocationserver.service;

import com.tanvirsojal.convocationserver.exception.ResourceAlreadyExistsException;
import com.tanvirsojal.convocationserver.exception.ResourceDoesNotExistException;
import com.tanvirsojal.convocationserver.model.Convocation;
import com.tanvirsojal.convocationserver.repository.ConvocationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ConvocationService {
    private ConvocationRepository convocationRepository;

    public ConvocationService(ConvocationRepository convocationRepository) {
        this.convocationRepository = convocationRepository;
    }

    // find by id
    public Convocation findById(int id) throws ResourceDoesNotExistException {
        Optional<Convocation> optionalConvocation = convocationRepository.findById(id);
        if (optionalConvocation.isPresent()){
            return optionalConvocation.get();
        } else throw new ResourceDoesNotExistException(id + "");
    }

    // find all
    public List<Convocation> findAll(){
        List<Convocation> convocationList = convocationRepository.findAll();
        return convocationList;
    }

    // insert
    public Convocation insert(Convocation convocation) throws ResourceAlreadyExistsException {
        Optional<Convocation> optionalConvocation = convocationRepository.findById(convocation.getId());
        if (optionalConvocation.isPresent()){
            throw new ResourceAlreadyExistsException(convocation.getId() + "");
        } return convocationRepository.save(convocation);
    }

    // delete by id
    public boolean deleteById(int id) throws ResourceDoesNotExistException {
        Optional<Convocation> optionalConvocation = convocationRepository.findById(id);
        if (optionalConvocation.isPresent()){
            convocationRepository.deleteById(id);
        } else throw new ResourceDoesNotExistException(id + "");
        return true;
    }

    // delete all
    public boolean deleteAll(){
        convocationRepository.deleteAll();
        return true;
    }

    // update
    public Convocation update(int id, Convocation convocation) throws ResourceDoesNotExistException {
        Optional<Convocation> optionalConvocation = convocationRepository.findById(id);
        if (optionalConvocation.isPresent()){
            convocation.setId(id);
            return convocationRepository.save(convocation);
        } else throw new ResourceDoesNotExistException(id + "");
    }
}
