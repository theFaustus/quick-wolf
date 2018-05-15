package com.quickwolf.bouncer.service;

import java.util.Optional;

import com.quickwolf.domain.User;

public interface AuthenticationService {

    Optional<User> authenticate(String email, String password);
}
