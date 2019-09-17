package com.tanvirsojal.frontendserver.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginToken {
    private String username;
    private String role;

    public LoginToken() {
        username = null;
        role = "none";
    }
}