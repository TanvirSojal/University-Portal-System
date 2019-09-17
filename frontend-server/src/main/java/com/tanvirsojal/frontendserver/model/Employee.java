package com.tanvirsojal.frontendserver.model;

import com.vaadin.flow.component.polymertemplate.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;

@Data
@AllArgsConstructor
public class Employee {
    @Id
    private String id;
    private String name;
    @Email
    private String email;
    private Role role;

    public Employee() {
        role = new Role();
    }

    public String getDesignation(){
        return role.getDesignation();
    }

    public String getDepartment(){
        return role.getDepartment();
    }

    public void setDepartment(String department){
        role.setDepartment(department);
    }

    public void setDesignation(String designation){
        role.setDesignation(designation);
    }
}
