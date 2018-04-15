package com.quickwolf.web.service;

import com.quickwolf.domain.Order;
import com.quickwolf.domain.TicketType;

import java.io.File;

public interface TicketService {

  File createTicket(Order order, String ticketTemplatePath);

}
