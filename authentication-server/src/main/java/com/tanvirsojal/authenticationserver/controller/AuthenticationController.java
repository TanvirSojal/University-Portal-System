package com.tanvirsojal.authenticationserver.controller;

import com.tanvirsojal.authenticationserver.exception.ResourceAlreadyExistsException;
import com.tanvirsojal.authenticationserver.exception.ResourceDoesNotExistException;
import com.tanvirsojal.authenticationserver.model.Credential;
import com.tanvirsojal.authenticationserver.model.LoginToken;
import com.tanvirsojal.authenticationserver.service.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {
    private AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("")
    @ResponseBody
    public LoginToken authenticate(@RequestParam String username, @RequestParam String password){
        return authenticationService.authenticate(username, password);
    }

    @PostMapping("/register")
    public ResponseEntity<LoginToken> register(@RequestBody Credential credential) throws ResourceAlreadyExistsException {
        LoginToken loginToken = authenticationService.register(credential);
        return ResponseEntity.ok(loginToken);
    }

    @GetMapping("/{username}")
    public ResponseEntity<LoginToken> getById(@PathVariable String username){
        try{
            LoginToken loginToken = authenticationService.findById(username);
            return ResponseEntity.ok(loginToken);

        } catch (ResourceDoesNotExistException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("")
    public ResponseEntity<List<LoginToken>> getAllLoginTokens(){
        List<LoginToken> loginTokenList = authenticationService.findAll();
        return ResponseEntity.ok(loginTokenList);
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<String> deleteById(@PathVariable String username){
        try{
            boolean deleted = authenticationService.deleteById(username);
            return ResponseEntity.ok(username);
        } catch (ResourceDoesNotExistException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{username}")
    public ResponseEntity<LoginToken> update(@PathVariable String username, @RequestBody Credential credential){
        try{
            LoginToken updatedToken = authenticationService.update(username, credential);
            return ResponseEntity.ok(updatedToken);
        } catch (ResourceDoesNotExistException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
