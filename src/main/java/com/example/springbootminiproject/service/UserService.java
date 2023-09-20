package com.example.springbootminiproject.service;

import com.example.springbootminiproject.exception.InformationExistsException;
import com.example.springbootminiproject.model.User;
import com.example.springbootminiproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    public UserService(UserRepository userRepository, @Lazy PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
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
}
