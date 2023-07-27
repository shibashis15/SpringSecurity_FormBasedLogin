package com.SpringSecurity.com.Service;

import com.SpringSecurity.com.Entity.User;
import com.SpringSecurity.com.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class SecurityUserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user =userRepository.findAllUserByUsername(username).orElseThrow(() -> new UsernameNotFoundException("UserNotFound"));
        return user;
    }
    public void createUser(UserDetails user) {
        userRepository.save((User)user);
    }
}
