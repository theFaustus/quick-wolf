package com.quickwolf.bouncer.domain;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TripAttendAttemptHistory {

    private List<TripAttendAttempt> attempts = new ArrayList<>();

    public TripAttendAttemptHistory() {
    }

    public TripAttendAttemptHistory(List<TripAttendAttempt> attempts) {
        this.attempts = attempts;
    }

    public List<TripAttendAttempt> getAttempts() {
        return attempts;
    }

    public void setAttempts(List<TripAttendAttempt> attempts) {
        this.attempts = attempts;
    }
}
