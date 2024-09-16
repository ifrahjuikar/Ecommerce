
    package com.springbootproject.springbootproject.ServiceImplementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springbootproject.springbootproject.Entitities.Role;
import com.springbootproject.springbootproject.Repositories.RoleRepository;
import com.springbootproject.springbootproject.Service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Role createNewRole(Role role){
        return roleRepository.save(role);

    }
    
}



    

