package com.tanvirsojal.frontendserver.service;

import com.tanvirsojal.frontendserver.model.Credential;
import com.tanvirsojal.frontendserver.model.LoginToken;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthenticationServiceTest {
    @Autowired
    private AuthenticationService authenticationService;

    @Test
    public void authenticate_john_123(){
        LoginToken test = authenticationService.authenticate("john", "123");
        assertEquals("john", test.getUsername());
        assertEquals("admission_officer", test.getRole());
    }

    @Test
    public void register(){
//        Credential credential = new Credential("io", "io", "oss");
//        LoginToken register = authenticationService.register(credential);
//        System.out.println(register);
    }
}
