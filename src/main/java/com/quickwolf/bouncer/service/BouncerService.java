package com.quickwolf.bouncer.service;

import com.quickwolf.bouncer.domain.BouncerVerdict;
import com.quickwolf.bouncer.domain.TripAttendRequest;

public interface BouncerService {
    BouncerVerdict isAttenderAllowedIn(TripAttendRequest request);
}
