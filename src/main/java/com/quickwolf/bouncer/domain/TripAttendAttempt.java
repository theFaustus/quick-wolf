package com.quickwolf.bouncer.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.quickwolf.domain.Ticket;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

@XmlRootElement
@Entity
public class TripAttendAttempt {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date attendDate = new Date();

    @OneToOne
    @JsonIgnore
    private Ticket ticket;

    @Enumerated(EnumType.STRING)
    private AttendStatus attemptStatus;

    public TripAttendAttempt() {
    }

    public TripAttendAttempt(Ticket ticket) {
        this.ticket = ticket;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getAttendDate() {
        return attendDate;
    }

    public void setAttendDate(Date attendDate) {
        this.attendDate = attendDate;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public AttendStatus getAttemptStatus() {
        return attemptStatus;
    }

    public void setAttemptStatus(AttendStatus attemptStatus) {
        this.attemptStatus = attemptStatus;
    }

    public static TripAttendAttemptBuilder newBuilder() {
        return new TripAttendAttemptBuilder();
    }

    public static final class TripAttendAttemptBuilder {
        private TripAttendAttempt tripAttendAttempt = new TripAttendAttempt();

        private TripAttendAttemptBuilder() {
        }

        public TripAttendAttemptBuilder setAttendDate(Date attendDate) {
            tripAttendAttempt.attendDate = attendDate;
            return this;
        }

        public TripAttendAttemptBuilder setTicket(Ticket ticket) {
            tripAttendAttempt.ticket = ticket;
            return this;
        }

        public TripAttendAttemptBuilder setAttemptStatus(AttendStatus attemptStatus) {
            tripAttendAttempt.attemptStatus = attemptStatus;
            return this;
        }

        public TripAttendAttempt build() {
            return tripAttendAttempt;
        }
    }
}
