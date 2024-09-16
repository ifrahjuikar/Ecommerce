package com.springbootproject.springbootproject.Repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.springbootproject.springbootproject.Entitities.Role;

@Repository
public interface RoleRepository extends CrudRepository<Role , String>{

    

    
}
