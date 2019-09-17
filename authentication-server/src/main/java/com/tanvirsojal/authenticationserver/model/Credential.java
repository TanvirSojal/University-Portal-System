package com.tanvirsojal.authenticationserver.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@AllArgsConstructor
public class Credential {
    @Id
    private String username;
    private String password;
    private String role;

    public Credential() {
        username = "";
        password = "";
        role = "none";
    }
}
