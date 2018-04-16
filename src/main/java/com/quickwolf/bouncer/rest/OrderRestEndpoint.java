package com.quickwolf.bouncer.rest;

import com.quickwolf.bouncer.domain.BouncerVerdict;
import com.quickwolf.bouncer.domain.TripAttendRequest;
import com.quickwolf.bouncer.service.BouncerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderRestEndpoint {

    @Autowired
    private BouncerService bouncerService;

    @PostMapping(value = "/orders", produces = "application/json")
    public BouncerVerdict allowAttenderIn(@RequestBody TripAttendRequest request) {
        return bouncerService.isAttenderAllowedIn(request);
    }
}
