package com.tanvirsojal.authenticationserver.service;

import com.tanvirsojal.authenticationserver.exception.ResourceAlreadyExistsException;
import com.tanvirsojal.authenticationserver.exception.ResourceDoesNotExistException;
import com.tanvirsojal.authenticationserver.model.Credential;
import com.tanvirsojal.authenticationserver.model.Employee;
import com.tanvirsojal.authenticationserver.model.LoginToken;
import com.tanvirsojal.authenticationserver.model.Student;
import com.tanvirsojal.authenticationserver.repository.AuthenticationRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AuthenticationService {
    private AuthenticationRepository authenticationRepository;

    public AuthenticationService(AuthenticationRepository authenticationRepository) {
        this.authenticationRepository = authenticationRepository;
    }

    public LoginToken authenticate(String username, String password){
        Optional<Credential> credential = authenticationRepository.findById(username);
        if (credential.isPresent() && credential.get().getPassword().equals(password)){
            return new LoginToken(username, credential.get().getRole());
        }
        return new LoginToken();
    }

    public LoginToken register(Credential credential) throws ResourceAlreadyExistsException {
        Optional<Credential> userById = authenticationRepository.findById(credential.getUsername());
        if (userById.isPresent()){
            throw new ResourceAlreadyExistsException(credential.getUsername());
        }
        else{
            Credential savedCredential = authenticationRepository.save(credential);
            return new LoginToken(savedCredential.getUsername(), savedCredential.getRole());
        }
    }

    // find by id
    public LoginToken findById(String username) throws ResourceDoesNotExistException {
        Optional<Credential> optionalCredential = authenticationRepository.findById(username);
        if (optionalCredential.isPresent()){
            Credential credential = optionalCredential.get();
            return new LoginToken(credential.getUsername(), credential.getRole());
        } else throw new ResourceDoesNotExistException(username + "");
    }

    public List<LoginToken> findAll(){
        List<LoginToken> loginTokenList = new ArrayList<>();

        authenticationRepository.findAll()
                .forEach(credential -> loginTokenList.add(new LoginToken(credential.getUsername(), credential.getRole())));
        return loginTokenList;
    }

    public boolean deleteById(String username) throws ResourceDoesNotExistException {
        Optional<Credential> optionalCredential = authenticationRepository.findById(username);
        if (optionalCredential.isPresent()){
            authenticationRepository.deleteById(username);
        } else throw new ResourceDoesNotExistException(username + "");
        return true;
    }

    public LoginToken update(String username, Credential credential) throws ResourceDoesNotExistException {
        Optional<Credential> optionalCredential = authenticationRepository.findById(username);
        if (optionalCredential.isPresent()){
            credential.setUsername(username);
            Credential savedCredential = authenticationRepository.save(credential);
            return new LoginToken(savedCredential.getUsername(), savedCredential.getRole());
        } else throw new ResourceDoesNotExistException(username + "");
    }

}
