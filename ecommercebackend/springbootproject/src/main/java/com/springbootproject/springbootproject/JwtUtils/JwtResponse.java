package com.springbootproject.springbootproject.JwtUtils;

import com.springbootproject.springbootproject.Entitities.User;

public class JwtResponse {

    private String user;

    private String jwtToken;

    public JwtResponse(String user, String jwtToken) {
        this.user = user;
        this.jwtToken = jwtToken;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    


    
}
