package com.tanvirsojal.humanresourcesserver.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.Email;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    @Id
    private String id;
    private String name;
    @Email
    private String email;
    private Role role;
}
