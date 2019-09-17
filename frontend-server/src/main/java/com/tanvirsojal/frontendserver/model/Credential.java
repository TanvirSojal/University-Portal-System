package com.tanvirsojal.frontendserver.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Credential {
    private String username;
    private String password;
    private String role;

    public Credential(String username, String password) {
        this.username = username;
        this.password = password;
        this.role = "none";
    }

    public Credential() {
        username = "";
        password = "";
        role = "none";
    }
}
