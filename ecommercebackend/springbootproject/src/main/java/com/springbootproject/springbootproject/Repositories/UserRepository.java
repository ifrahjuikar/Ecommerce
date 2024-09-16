package com.springbootproject.springbootproject.Repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.springbootproject.springbootproject.Entitities.User;

@Repository
public interface UserRepository extends CrudRepository<User , String> {

    
} 
