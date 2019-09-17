package com.tanvirsojal.authenticationserver.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
@AllArgsConstructor
public class LoginToken {
    @Id
    private String username;
    private String role;

    public LoginToken() {
        username = null;
        role = "none";
    }
}
