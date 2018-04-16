package com.quickwolf.domain;

import javax.persistence.*;

@Entity
public class Ticket extends AbstractEntity {

    @ManyToOne
    private Trip trip;

    @Enumerated(EnumType.STRING)
    @Column(name = "ticket_type")
    private TicketType ticketType;

    @Enumerated(EnumType.STRING)
    @Column(name = "ticket_status")
    private TicketStatus ticketStatus = TicketStatus.NOT_USED;

    @ManyToOne
    private User user;

    public Ticket() {
    }

    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }

    public TicketType getTicketType() {
        return ticketType;
    }

    public void setTicketType(TicketType ticketType) {
        this.ticketType = ticketType;
    }

    public TicketStatus getTicketStatus() {
        return ticketStatus;
    }

    public void setTicketStatus(TicketStatus ticketStatus) {
        this.ticketStatus = ticketStatus;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + getId() +
                ", trip=" + trip +
                ", ticketType=" + ticketType +
                ", user=" + user +
                '}';
    }

    public static TicketBuilder newBuilder() {
        return new TicketBuilder();
    }

    public static final class TicketBuilder {
        private Ticket ticket = new Ticket();

        private TicketBuilder() {
        }

        public TicketBuilder setTrip(Trip trip) {
            ticket.trip = trip;
            return this;
        }

        public TicketBuilder setTicketType(TicketType ticketType) {
            ticket.ticketType = ticketType;
            return this;
        }

        public TicketBuilder setTicketStatus(TicketStatus ticketStatus) {
            ticket.ticketStatus = ticketStatus;
            return this;
        }

        public TicketBuilder setUser(User user) {
            ticket.user = user;
            return this;
        }

        public Ticket build() {
            return ticket;
        }
    }
}
