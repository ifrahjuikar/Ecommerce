package com.springbootproject.springbootproject.Repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.springbootproject.springbootproject.Entitities.Cart;
import com.springbootproject.springbootproject.Entitities.User;

import java.util.List;

@Repository
public interface CartRepository extends CrudRepository<Cart, Integer> {

    public List<Cart> findByUser(User user);

    
} 
