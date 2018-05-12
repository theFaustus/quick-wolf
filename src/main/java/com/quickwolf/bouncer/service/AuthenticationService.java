package com.quickwolf.bouncer.service;

public interface AuthenticationService {

    boolean authenticate(String email, String password);
}
