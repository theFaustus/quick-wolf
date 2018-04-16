package com.quickwolf.domain;


import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "orders")
public class Order extends AbstractEntity {

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "order_date")
    private Date orderDate = new Date();

    //TODO: private int seats;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Ticket ticket;

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
        return String.format("Order{id=%d}", getId());
    }

    public static OrderBuilder newBuilder() {
        return new OrderBuilder();
    }

    public static class OrderBuilder {
        private Order order = new Order();

        public OrderBuilder setId(Long id) {
            order.setId(id);
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
