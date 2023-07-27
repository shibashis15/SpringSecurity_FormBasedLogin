package com.SpringSecurity.com.Security;

import com.SpringSecurity.com.Entity.Attempts;
import com.SpringSecurity.com.Entity.User;
import com.SpringSecurity.com.Repository.AttemptsRepository;
import com.SpringSecurity.com.Repository.UserRepository;
import com.SpringSecurity.com.Service.SecurityUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

public class AuthProvider implements AuthenticationProvider {
    private static final int ATTEMPT_LIMITS = 3;
    @Autowired
    private Attempts attempts;
    @Autowired
    private SecurityUserService securityUserService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AttemptsRepository attemptsRepository;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String userName = authentication.getName();
        Optional<Attempts>userAttempts = attemptsRepository.findAttemptsByUsername(userName);
        if(userAttempts.isPresent()) {
            Attempts attempts = userAttempts.get();
            attempts.setAttempts(0);
            attemptsRepository.save(attempts);
            authentication.setAuthenticated(true);
        }
        return authentication;
    }

    private void processFailedAttempts(String username , User user) {
        Optional<Attempts>userAttempts = attemptsRepository.findAttemptsByUsername(username);
        if(userAttempts.isEmpty()) {
            Attempts attempts = new Attempts();
            attempts.setUsername(username);
            attempts.setAttempts(1);
            attemptsRepository.save(attempts);
        }
        else {
            Attempts attempts = userAttempts.get();
            attempts.setAttempts(attempts.getAttempts()+1);
            attemptsRepository.save(attempts);
            if(attempts.getAttempts()+1>ATTEMPT_LIMITS) {
                user.setAccountNonLocked(false);
                userRepository.save(user);
                throw new LockedException("Too Many Attempts");
            }
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
