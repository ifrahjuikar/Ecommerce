// package com.springbootproject.springbootproject.ServiceImplementation;

// import java.util.HashSet;
// import java.util.Set;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Lazy;
// import org.springframework.security.authentication.AuthenticationManager;
// import org.springframework.security.authentication.DisabledException;
// import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
// import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
// import org.springframework.security.core.authority.SimpleGrantedAuthority;
// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.security.core.userdetails.UserDetailsService;
// import org.springframework.security.core.userdetails.UsernameNotFoundException;
// import org.springframework.stereotype.Service;

// import com.springbootproject.springbootproject.Entitities.User;
// import com.springbootproject.springbootproject.Jwt.JwtHelper;
// import com.springbootproject.springbootproject.JwtUtils.JwtRequest;
// import com.springbootproject.springbootproject.JwtUtils.JwtResponse;
// import com.springbootproject.springbootproject.Repositories.UserRepository;

// @Service
// public class JwtService implements UserDetailsService {

//     @Autowired
//     private UserRepository userRepository;

//     @Autowired
//     private JwtHelper jwtHelper;

//     @Autowired
//     private AuthenticationManager authenticationManager;
//     public JwtService(){}

//     // public JwtService(AuthenticationManager authenticationManager){
//     //     this.authenticationManager=authenticationManager;

//     // }

//     public JwtResponse createJwtToken(JwtRequest jwtRequest) throws Exception {
//         String userName = jwtRequest.getUserName();
//         String userPassword = jwtRequest.getUserPassword();
//         // authenticate(userName, userPassword);
//         final UserDetails userDetails=loadUserByUsername(userName);
//         String newGeneratedToken=jwtHelper.doGenerateToken(userDetails);
//         User user=userRepository.findById(userName).get();
//         return new JwtResponse(user, newGeneratedToken);

//     }

//     @Override
//     public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
//         User user= userRepository.findById(username).get();
//         if(user!=null){
//             return new org.springframework.security.core.userdetails.User(
//                 user.getUserName(),
//                 user.getUserPassword(),
//                 getAuthorities(user)

//             );
//         }else{
//             throw new UsernameNotFoundException("username");
//         }
//     }

//     // private void authenticate(String userName, String userPassword) throws Exception{
//     //     try {
//     //         authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName, userPassword));
//     //     } catch (DisabledException e) {
//     //         throw new Exception("User is disabled");
//     //     }

//     // }

//     private Set getAuthorities(User user) {
//         Set authorities = new HashSet<>();
//         user.getRole().forEach(role -> {
//             authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRoleName()));
//         });
//         return authorities;
//     }

//     // @Bean
//     // public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
//     //     return configuration.getAuthenticationManager();
//     // }

// }


// // package com.springbootproject.springbootproject.ServiceImplementation;

// // import org.springframework.beans.factory.annotation.Autowired;
// // import org.springframework.security.authentication.AuthenticationManager;
// // import org.springframework.security.authentication.DisabledException;
// // import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
// // import org.springframework.security.core.userdetails.UserDetails;
// // import org.springframework.stereotype.Service;

// // import com.springbootproject.springbootproject.Entitities.User;
// // import com.springbootproject.springbootproject.JwtUtils.JwtRequest;
// // import com.springbootproject.springbootproject.JwtUtils.JwtResponse;
// // import com.springbootproject.springbootproject.Repositories.UserRepository;
// // import com.springbootproject.springbootproject.configuration.JwtHelper;

// // @Service
// // public class JwtService {

// //     @Autowired
// //     private UserRepository userRepository;

// //     @Autowired
// //     private JwtHelper jwtHelper;

// //     @Autowired
// //     private AuthenticationManager authenticationManager;

// //     @Autowired
// //     private CustomUserDetailsService customUserDetailsService;

// //     public JwtResponse createJwtToken(JwtRequest jwtRequest) throws Exception {
// //         String userName = jwtRequest.getUserName();
// //         String userPassword = jwtRequest.getUserPassword();
// //         authenticate(userName, userPassword);
// //         final UserDetails userDetails = customUserDetailsService.loadUserByUsername(userName);
// //         String newGeneratedToken = jwtHelper.doGenerateToken(userDetails);
// //         User user = userRepository.findById(userName).get();
// //         return new JwtResponse(user, newGeneratedToken);
// //     }

// //     private void authenticate(String userName, String userPassword) throws Exception {
// //         try {
// //             authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName, userPassword));
// //         } catch (DisabledException e) {
// //             throw new Exception("User is disabled");
// //         }
// //     }
// // }


package com.springbootproject.springbootproject.ServiceImplementation;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.springbootproject.springbootproject.Entitities.User;
import com.springbootproject.springbootproject.Jwt.JwtHelper;
import com.springbootproject.springbootproject.JwtUtils.JwtRequest;
import com.springbootproject.springbootproject.JwtUtils.JwtResponse;
import com.springbootproject.springbootproject.Repositories.UserRepository;

@Service
public class JwtService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    // @Autowired
    // private JwtHelper jwtHelper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findById(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new org.springframework.security.core.userdetails.User(
            user.getUserName(),
            user.getUserPassword(),
            getAuthorities(user)
        );
    }

    // public JwtResponse createJwtToken(JwtRequest jwtRequest) throws Exception {
    //     String userName = jwtRequest.getUserName();
    //     String userPassword = jwtRequest.getUserPassword();

    //     // Validate user credentials directly
    //     User user = userRepository.findById(userName)
    //         .orElseThrow(() -> new Exception("User not found"));

    //     if (!user.getUserPassword().equals(userPassword)) {
    //         throw new Exception("Invalid credentials");
    //     }

    //     // Generate JWT token
    //     final UserDetails userDetails = loadUserByUsername(userName);
    //     String newGeneratedToken = jwtHelper.doGenerateToken(userDetails);
    //     return new JwtResponse(user, newGeneratedToken);
    // }

    private Set<SimpleGrantedAuthority> getAuthorities(User user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        user.getRole().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRoleName()));
        });
        return authorities;
    }
}
