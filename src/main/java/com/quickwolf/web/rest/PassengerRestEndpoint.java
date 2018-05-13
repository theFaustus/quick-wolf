package com.quickwolf.web.rest;

import com.quickwolf.domain.Passenger;
import com.quickwolf.domain.Trip;
import com.quickwolf.web.form.beans.RegisterPassengerFormBean;
import com.quickwolf.web.service.PassengerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class PassengerRestEndpoint {

    @Autowired
    private PassengerService passengerService;

    @GetMapping(value = "/rest/passengers/{passengerEmail}/bookedTrips", produces = { "application/json" })
    public List<Trip> getBookedTrips(@PathVariable String passengerEmail) {
        return passengerService.findBookedTrips(passengerEmail);
    }

    @GetMapping(value = "/rest/passengers/{passengerEmail}/", produces = { "application/json" })
    public Passenger getPassenger(String passengerEmail) {
        return passengerService.findPassengerBy(passengerEmail);
    }

    @PostMapping("/rest/passengers/")
    public ResponseEntity registerPassenger(@Valid @RequestBody RegisterPassengerFormBean registerPassengerFormBean,
                                            BindingResult bindingResult) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        if(bindingResult.hasErrors())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(bindingResult.getAllErrors());
        else {
           try {
               Passenger passenger = passengerService.savePassenger(registerPassengerFormBean);
               return ResponseEntity.status(HttpStatus.CREATED).body(passenger);
           } catch (Exception e) {
               return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
           }
        }
    }
}
