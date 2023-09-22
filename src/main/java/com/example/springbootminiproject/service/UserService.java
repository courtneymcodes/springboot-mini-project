package com.example.springbootminiproject.service;

import com.example.springbootminiproject.exception.InformationExistsException;
import com.example.springbootminiproject.model.User;
import com.example.springbootminiproject.model.request.LoginRequest;
import com.example.springbootminiproject.repository.UserRepository;
import com.example.springbootminiproject.security.JWTUtils;
import com.example.springbootminiproject.security.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final JWTUtils jwtUtils;

    private final AuthenticationManager authenticationManager;
    @Autowired
    public UserService(UserRepository userRepository, @Lazy PasswordEncoder passwordEncoder, JWTUtils jwtUtils, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
        this.authenticationManager = authenticationManager;
    }

    /**
     * Registers a new user, adding them to the database. Password is hashed before it is stored.
     * @param userObject
     * @return a User object
     */
    public User createUser(User userObject){
        if(!userRepository.existsByEmailAddress(userObject.getEmailAddress())){
            userObject.setPassword(passwordEncoder.encode(userObject.getPassword()));
            return userRepository.save(userObject);
        }else {
            throw new InformationExistsException("User with email address " + userObject.getEmailAddress() + "already exists");
        }
    }

    public User loadUserByEmailAddress(String emailAddress){
        return userRepository.findUserByEmailAddress(emailAddress);
    }

    /**
     * Authenticates a users login credentials and returns a jwt token if authentication is successful
     * @param loginRequest request object from request body containing the username (email address) and password of user attempting to login
     * @return an optional that may or may not contain  a string representing the token
     * @throws Exception if authentication fails
     */
    public Optional<String> loginUser(LoginRequest loginRequest) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginRequest.getEmailAddress(), loginRequest.getPassword());
        try{
            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            MyUserDetails myUserDetails = (MyUserDetails) authentication.getPrincipal();
            return Optional.of(jwtUtils.generateJwtToken(myUserDetails));
        }catch (Exception e){
            return Optional.empty();
        }
    }

}
