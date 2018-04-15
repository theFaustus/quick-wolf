package com.quickwolf.domain;


import com.quickwolf.domain.Ticket;

import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "order_date")
    private Date orderDate = new Date();

    //TODO: private int seats;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Ticket ticket;

    public Long getId() {
        return id;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }


    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public String toString() {
        return String.format("Order{id=%d}", id);
    }

    public static OrderBuilder newBuilder() {
        return new OrderBuilder();
    }

    public static class OrderBuilder {
        private Order order = new Order();

        public OrderBuilder setId(Long id) {
            order.id = id;
            return this;
        }

        public OrderBuilder setTicket(Ticket ticket) {
            order.ticket = ticket;
            return this;
        }

        public Order build() {
            return order;
        }
    }
}
