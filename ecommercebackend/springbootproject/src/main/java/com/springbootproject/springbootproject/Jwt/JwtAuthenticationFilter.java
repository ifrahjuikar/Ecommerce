package com.springbootproject.springbootproject.Jwt;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.springbootproject.springbootproject.ServiceImplementation.JwtService;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    // Logger for logging messages
    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    public static String CURRENT_USER="";

    // Autowired components for JWT handling and user details service
    @Autowired
    private JwtHelper jwtHelper;

    @Autowired
    private UserDetailsService userDetailsService;

    // Method to filter each incoming request
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String username = null;
        // Extract token from request header
        String token = request.getHeader("Authorization");
        String requestURI = request.getRequestURI();

        // Skip JWT processing for publicly accessible endpoints
        if (requestURI.startsWith("/authenticate") || requestURI.startsWith("/registerNewUser")) {
            filterChain.doFilter(request, response);
            return; // Skip further processing for these endpoints
        }

        // String requestHeader = request.getHeader("Authorization");
        // if (requestHeader != null && requestHeader.startsWith("Bearer")) {
        // Extract token from Authorization header
        // token = requestHeader.substring(7);

        // Check if token is present in the request header
        if (token != null) {
            try {
                // Extract username from token
                username = this.jwtHelper.getUsernameFromToken(token);
                CURRENT_USER=username;

            } catch (IllegalArgumentException e) {
                // Log error if an illegal argument is encountered while fetching the username
                logger.error("Illegal Argument while fetching the username !!");
            } catch (ExpiredJwtException e) {
                // Log error if the token is expired
                logger.error("Given jwt token is expired !!");
            } catch (MalformedJwtException e) {
                // Log error if the token is malformed
                logger.error("Some changes have been made in the token !! Invalid Token");
            } catch (Exception e) {
                // Log error for any other exceptions
                logger.error("Internal Server Problem");
            }
        } else {
            // Log info if no token is found in the request header
            logger.error("Invalid Header Value !! ");
        }

        // Check if username is not null and no authentication has been set
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // Fetch user details from username
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            // Validate token
            Boolean validateToken = this.jwtHelper.validateToken(token, userDetails);
            if (validateToken) {
                // Set the authentication if the token is valid
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                // Log info if token validation fails
                logger.error("Validation fails !!");
            }
        }
        // Continue the filter chain
        filterChain.doFilter(request, response);
    }
}
