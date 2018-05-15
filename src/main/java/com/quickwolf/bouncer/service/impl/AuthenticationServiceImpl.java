package com.quickwolf.bouncer.service.impl;

import java.util.Optional;

import javax.swing.text.html.Option;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.quickwolf.bouncer.service.AuthenticationService;
import com.quickwolf.domain.User;
import com.quickwolf.web.repository.UserRepository;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Optional<User> authenticate(final String email, final String password) {
        try {
            UsernamePasswordAuthenticationToken authReq
                    = new UsernamePasswordAuthenticationToken(email, password);
            Authentication auth = authenticationManager.authenticate(authReq);
            SecurityContext context = SecurityContextHolder.getContext();
            context.setAuthentication(auth);
            return Optional.of(userRepository.findUserByEmail(email));
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
