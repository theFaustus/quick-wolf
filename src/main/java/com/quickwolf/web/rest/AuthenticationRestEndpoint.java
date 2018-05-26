package com.quickwolf.web.rest;

import java.util.Optional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.quickwolf.bouncer.service.AuthenticationService;
import com.quickwolf.domain.User;
import com.quickwolf.web.form.beans.LoginUserFormBean;

@RestController
public class AuthenticationRestEndpoint {
    private static final Logger LOGGER = Logger.getLogger(AuthenticationRestEndpoint.class);

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/rest/login")
    public ResponseEntity authenticate(@RequestBody LoginUserFormBean formBean) {
        Optional<User> user = authenticationService.authenticate(formBean.getEmail(), formBean.getPassword());
        if (user.isPresent()) {
            LOGGER.info("Successfully logged user: " + formBean.getEmail());
            return ResponseEntity.ok().body(user);
        }
        LOGGER.error("Failure to authenticate user: " + formBean.getEmail());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
}
