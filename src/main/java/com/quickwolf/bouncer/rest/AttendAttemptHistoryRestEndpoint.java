package com.quickwolf.bouncer.rest;

import com.quickwolf.bouncer.domain.TripAttendAttemptHistory;
import com.quickwolf.bouncer.service.AttendAttemptHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AttendAttemptHistoryRestEndpoint {

    @Autowired
    private AttendAttemptHistoryService historyService;

    @GetMapping(value = "/attendAttemptHistory/{encryptedOrderId}", produces = "application/json")
    public TripAttendAttemptHistory getAttendAttemptHistory(@PathVariable("encryptedOrderId") String orderId) {
        return historyService.getAttendAttemptHistory(orderId);
    }
}
