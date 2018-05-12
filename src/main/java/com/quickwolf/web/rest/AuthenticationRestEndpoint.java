package com.quickwolf.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.quickwolf.bouncer.service.AuthenticationService;

@RestController
public class AuthenticationRestEndpoint {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/rest/login")
    public ResponseEntity authenticate(@RequestParam("email") String user, @RequestParam("password") String password) {
        if (authenticationService.authenticate(user, password))
            return ResponseEntity.ok().build();
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
}
