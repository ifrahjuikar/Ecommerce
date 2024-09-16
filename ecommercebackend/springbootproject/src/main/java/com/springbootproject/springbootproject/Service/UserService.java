package com.springbootproject.springbootproject.Service;

import java.util.List;

import com.springbootproject.springbootproject.Entitities.Product;
import com.springbootproject.springbootproject.Entitities.User;

public interface UserService {

    public User registerNewUser(User user);

    public void initRolesAndUser();

    public User createUser(User user);

}
