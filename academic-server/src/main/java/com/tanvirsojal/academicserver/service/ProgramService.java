package com.tanvirsojal.academicserver.service;

import com.tanvirsojal.academicserver.exception.ResourceAlreadyExistsException;
import com.tanvirsojal.academicserver.exception.ResourceDoesNotExistException;
import com.tanvirsojal.academicserver.model.Program;
import com.tanvirsojal.academicserver.repository.ProgramRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProgramService {
    private ProgramRepository programRepository;

    public ProgramService(ProgramRepository programRepository) {
        this.programRepository = programRepository;
    }

    // find by id
    public Program findById(int id) throws ResourceDoesNotExistException {
        Optional<Program> optionalProgram = programRepository.findById(id);
        if (optionalProgram.isPresent()){
            return optionalProgram.get();
        } else throw new ResourceDoesNotExistException(id + "");
    }

    // find all
    public List<Program> findAll(){
        List<Program> programList = programRepository.findAll();
        return programList;
    }

    // insert
    public Program insert(Program program) throws ResourceAlreadyExistsException {
        Optional<Program> optionalProgram = programRepository.findById(program.getId());
        if (optionalProgram.isPresent()){
            throw new ResourceAlreadyExistsException(program.getId() + "");
        } return programRepository.save(program);
    }

    // delete by id
    public boolean deleteById(int id) throws ResourceDoesNotExistException {
        Optional<Program> optionalProgram = programRepository.findById(id);
        if (optionalProgram.isPresent()){
            programRepository.deleteById(id);
        } else throw new ResourceDoesNotExistException(id + "");
        return true;
    }

    // delete all
    public boolean deleteAll(){
        programRepository.deleteAll();
        return true;
    }

    // update
    public Program update(int id, Program program) throws ResourceDoesNotExistException {
        Optional<Program> optionalProgram = programRepository.findById(id);
        if (optionalProgram.isPresent()){
            program.setId(id);
            return programRepository.save(program);
        } else throw new ResourceDoesNotExistException(id + "");
    }
}
