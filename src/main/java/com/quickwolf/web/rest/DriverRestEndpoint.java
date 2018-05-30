package com.quickwolf.web.rest;

import com.quickwolf.domain.Driver;
import com.quickwolf.domain.Trip;
import com.quickwolf.web.form.beans.AddTripFormBean;
import com.quickwolf.web.form.beans.RegisterDriverFormBean;
import com.quickwolf.web.service.DriverService;
import com.quickwolf.web.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
public class DriverRestEndpoint {

    @Autowired
    private DriverService driverService;

    @Autowired
    private TripService tripService;

    @PostMapping("/rest/drivers")
    public ResponseEntity registerDriver(@RequestBody @Valid RegisterDriverFormBean registerDriverFormBean,
                                 BindingResult bindingResult) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        if(bindingResult.hasErrors())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(bindingResult.getAllErrors());
        else {
            try {
                Driver driver = driverService.saveDriver(registerDriverFormBean);
                return ResponseEntity.status(HttpStatus.CREATED).body(driver);
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
        }
    }

    @PostMapping(value = "/rest/drivers/{driverEmail}/trips")
    public ResponseEntity createNewTrip(@Valid @RequestBody AddTripFormBean trip, @PathVariable String driverEmail) {
        tripService.createTrip(trip, driverEmail);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping(value = "/rest/drivers/{driverEmail}/addedTrips", produces = { "application/json" })
    public List<Trip> getBookedTrips(@PathVariable String driverEmail) {
        return driverService.findAddedTrips(driverEmail);
    }

    @GetMapping("/rest/drivers/{driverEmail}")
    public Driver getDriver(@PathVariable("driverEmail") String driverEmail) {
        return driverService.findDriverBy(driverEmail);
    }
}
