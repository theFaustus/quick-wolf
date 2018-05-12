package com.quickwolf.web.controller;

import org.apache.log4j.Logger;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Faust on 5/15/2017.
 */
@Controller
public class LoginController {
    private static final Logger LOGGER = Logger.getLogger(LoginController.class);

    @RequestMapping(value = "/login")
    public String login() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        LOGGER.info("Authenticating user.");
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            return "redirect:/";
        }
        return "login";
    }
}
