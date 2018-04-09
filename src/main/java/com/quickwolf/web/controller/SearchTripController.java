package com.quickwolf.web.controller;

import com.quickwolf.web.form.beans.TripFormBean;
import com.quickwolf.web.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collection;

@Controller
@RequestMapping(value = "/")
public class SearchTripController {

    @Autowired
    private TripService service;

    @GetMapping
    public String search(Model model) {
        Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        if (hasRole(authorities, "ROLE_ADMIN"))
            return "redirect:/adminProfile";
        if (hasRole(authorities, "ROLE_USER"))
            return "searchTrip";
        if (hasRole(authorities, "ROLE_DRIVER"))
            return "redirect:/driverProfile";
        return "redirect:/";
    }

    private boolean hasRole(Collection<? extends GrantedAuthority> authorities, String role) {
        return authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(r -> r.equals(role));
    }

    @GetMapping("/search")
    public String searchTrip(@ModelAttribute("tripFormBean") TripFormBean trip, Model model) {
        model.addAttribute("trips", service.findTripsBy(trip));
        return "listTrips";
    }

    @ModelAttribute
    public void populateModel(Model model) {
        model.addAttribute("tripFormBean", new TripFormBean());
    }
}
