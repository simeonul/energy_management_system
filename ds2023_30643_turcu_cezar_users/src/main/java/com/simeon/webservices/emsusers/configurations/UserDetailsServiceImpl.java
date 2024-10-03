package com.simeon.webservices.emsusers.configurations;

import com.simeon.webservices.emsusers.entities.User;
import com.simeon.webservices.emsusers.exceptions.models.ResourceNotFoundException;
import com.simeon.webservices.emsusers.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

//@Component
public class UserDetailsServiceImpl implements UserDetailsService {


    private final UserRepository userRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws ResourceNotFoundException {
        User queriedUser =  userRepository.findByEmail(username).
                orElseThrow(() -> new ResourceNotFoundException(User.class.getSimpleName() + " with email: " + username));
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(queriedUser.getRole().name()));
        return new org.springframework.security.core.userdetails.User(queriedUser.getEmail(), queriedUser.getPassword(), authorities);
    }
}
