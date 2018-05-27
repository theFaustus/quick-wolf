package com.quickwolf.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.quickwolf.web.form.beans.DriverReviewFormBean;
import com.quickwolf.web.service.DriverService;

@RestController
public class DriverReviewController {

    @Autowired
    private DriverService driverService;

    @PostMapping(value = "/rest/drivers/{driverId}/reviews", consumes = { "application/json" })
    public ResponseEntity reviewDriver(@RequestBody DriverReviewFormBean formBean, @PathVariable("driverId") long driverId) {
        driverService.addDriverReview(driverId, formBean);
        return ResponseEntity.ok().build();
    }
}
