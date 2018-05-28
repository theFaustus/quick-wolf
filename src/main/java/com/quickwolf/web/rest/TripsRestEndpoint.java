package com.quickwolf.web.rest;

import com.quickwolf.domain.Trip;
import com.quickwolf.web.form.beans.BookTripFormBean;
import com.quickwolf.web.form.beans.TripFormBean;
import com.quickwolf.web.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
public class TripsRestEndpoint {

    @Autowired
    private TripService tripService;

    @GetMapping(value = "/rest/trips", produces = { "application/json" })
    public List<Trip> searchTrip(@Valid TripFormBean trip) {
        return tripService.findValidTripsBy(trip);
    }

    @PostMapping(value = "/rest/bookTrip", consumes = { "application/json" })
    public ResponseEntity bookTrip(@RequestBody BookTripFormBean bookTripFormBean) {
        boolean tripBooked = tripService.bookTrip(bookTripFormBean.getEmail(), bookTripFormBean.getTripId());
        return tripBooked ? ResponseEntity.ok().build() : ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).build();
    }
}
