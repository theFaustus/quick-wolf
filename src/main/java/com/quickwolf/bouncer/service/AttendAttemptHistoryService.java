package com.quickwolf.bouncer.service;

import com.quickwolf.bouncer.domain.TripAttendAttemptHistory;

public interface AttendAttemptHistoryService {

    TripAttendAttemptHistory getAttendAttemptHistory(String encryptedOrderId);
}
