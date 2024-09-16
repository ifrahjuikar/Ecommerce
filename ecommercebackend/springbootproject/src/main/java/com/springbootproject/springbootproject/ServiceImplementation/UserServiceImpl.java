package com.springbootproject.springbootproject.ServiceImplementation;


import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.springbootproject.springbootproject.Repositories.RoleRepository;
import com.springbootproject.springbootproject.Repositories.UserRepository;
import com.springbootproject.springbootproject.Service.UserService;
import com.springbootproject.springbootproject.Entitities.Role;
import com.springbootproject.springbootproject.Entitities.User;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public User registerNewUser(User user){
        user.setUserFirstName(user.getUserFirstName());
        user.setUserLastName(user.getUserLastName());

        user.setUserPassword(getEncodedPassword(user.getUserPassword()));

        Role userRole = roleRepository.findById("User").get();
       
        Set<Role> userRoles=new HashSet<>();
        userRoles.add(userRole);
        user.setRole(userRoles);

        return userRepository.save(user);
    };


    @Override
    public void initRolesAndUser(){
        Role adminRole = new Role();
        adminRole.setRoleName("Admin");
        adminRole.setRoleDescription("Admin Role");
        roleRepository.save(adminRole);

        Role userRole = new Role();
        userRole.setRoleName("User");
        userRole.setRoleDescription("Default role for newly created record");
        roleRepository.save(userRole);


        User adminUser=new User();
        adminUser.setUserFirstName("admin");
        adminUser.setUserLastName("admin");
        adminUser.setUserName("admin123");
        adminUser.setUserPassword(getEncodedPassword("admin@pass"));

        Set<Role> adminRoles=new HashSet<>();
        adminRoles.add(adminRole);
        adminUser.setRole(adminRoles);
        userRepository.save(adminUser);

        User user=new User();
        user.setUserFirstName("ifrah");
        user.setUserLastName("juikar");
        user.setUserName("ifrah123");
        user.setUserPassword(getEncodedPassword("ifrah@pass"));

        Set<Role> userRoles=new HashSet<>();
        userRoles.add(userRole);
        user.setRole(userRoles);
        userRepository.save(user);


    }

    @Override
    public User createUser(User user){
        User users=new User();
        users.setUserPassword(getEncodedPassword(users.getUserPassword()));
        return userRepository.save(users);
    }

    public String getEncodedPassword(String password){
        return passwordEncoder.encode(password);
    }


    
}
