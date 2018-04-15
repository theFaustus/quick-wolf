package com.quickwolf.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Ticket {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Long id;

  @ManyToOne
  private Trip trip;

    @Enumerated(EnumType.STRING)
  private TicketType ticketType;

  @ManyToOne
  private User user;

  private Ticket(Builder builder) {
    this.id = builder.id;
    this.trip = builder.trip;
    this.ticketType = builder.ticketType;
    this.user = builder.user;
  }

  public static Builder newTicket() {
    return new Builder();
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
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

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }


  public static final class Builder {
    private Long id;
    private Trip trip;
    private TicketType ticketType;
    private User user;

    private Builder() {
    }

    public Ticket build() {
      return new Ticket(this);
    }

    public Builder id(Long id) {
      this.id = id;
      return this;
    }

    public Builder trip(Trip trip) {
      this.trip = trip;
      return this;
    }

    public Builder ticketType(TicketType ticketType) {
      this.ticketType = ticketType;
      return this;
    }

    public Builder user(User user) {
      this.user = user;
      return this;
    }
  }

  @Override
  public String toString() {
    return "Ticket{" +
            "id=" + id +
            ", trip=" + trip +
            ", ticketType=" + ticketType +
            ", user=" + user +
            '}';
  }
}
