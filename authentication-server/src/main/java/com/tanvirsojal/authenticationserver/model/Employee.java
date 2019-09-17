package com.tanvirsojal.authenticationserver.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    private String id;
    private String name;
    @Email
    private String email;
    private Role role;
}
