package com.springbootproject.springbootproject.Entitities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Role {

    @Id
    private String roleName;
    private String roleDescription;
    
}
