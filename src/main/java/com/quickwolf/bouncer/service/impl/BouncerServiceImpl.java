package com.quickwolf.bouncer.service.impl;

import com.quickwolf.bouncer.domain.AttendStatus;
import com.quickwolf.bouncer.domain.BouncerVerdict;
import com.quickwolf.bouncer.domain.TripAttendAttempt;
import com.quickwolf.bouncer.domain.TripAttendRequest;
import com.quickwolf.bouncer.repository.TripAttendAttemptRepository;
import com.quickwolf.bouncer.service.BouncerService;
import com.quickwolf.domain.Order;
import com.quickwolf.domain.Ticket;
import com.quickwolf.domain.TicketStatus;
import com.quickwolf.web.repository.OrderRepository;
import com.quickwolf.web.repository.TicketRepository;
import com.quickwolf.web.service.BlowfishCryptoService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class BouncerServiceImpl implements BouncerService {
    private static final Logger LOGGER = Logger.getLogger(BouncerServiceImpl.class);

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private TripAttendAttemptRepository tripAttendAttemptRepository;

    @Autowired
    private BlowfishCryptoService cryptoService;

    //TODO: think about transaction-isolation level of this method
    @Transactional
    @Override
    public BouncerVerdict isAttenderAllowedIn(TripAttendRequest request) {
        String decryptedOrderId = cryptoService.dencrypt(request.getEncryptedOrderId());
        LOGGER.info("Attending trip with order id: " + decryptedOrderId);
        if (!decryptedOrderIdIsNumeric(decryptedOrderId))
            return new BouncerVerdict(false, request.getEncryptedOrderId());
        return getBouncerVerdict(Long.valueOf(decryptedOrderId), request.getEncryptedOrderId());
    }

    private BouncerVerdict getBouncerVerdict(long orderId, String encryptedOrderId) {
        Optional<Order> order = orderRepository.findById(orderId);
        if (order.isPresent()) {
            Ticket ticket = order.get().getTicket();
            boolean isAllowedIn = ticket.getTicketStatus() == TicketStatus.NOT_USED;
            if (isAllowedIn)
                markTicketAsUsed(ticket);
            saveTripAttendAttempt(ticket, isAllowedIn);
            return new BouncerVerdict(isAllowedIn, encryptedOrderId);
        }
        return new BouncerVerdict(false, encryptedOrderId);
    }

    private void markTicketAsUsed(Ticket ticket) {
        ticket.setTicketStatus(TicketStatus.USED);
        ticketRepository.save(ticket);
    }

    private void saveTripAttendAttempt(Ticket ticket, boolean isAllowedIn) {
        tripAttendAttemptRepository.save(TripAttendAttempt.newBuilder()
                .setTicket(ticket)
                .setAttemptStatus(isAllowedIn ? AttendStatus.SUCCESSFULL : AttendStatus.NOT_SUCCESSFULL)
                .build());
    }

    private boolean decryptedOrderIdIsNumeric(String decryptedOrderId) {
        Pattern p = Pattern.compile("\\d+");
        return p.matcher(decryptedOrderId)
                .matches();
    }
}
