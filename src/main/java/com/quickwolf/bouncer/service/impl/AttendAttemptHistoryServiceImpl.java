package com.quickwolf.bouncer.service.impl;

import com.quickwolf.bouncer.domain.TripAttendAttempt;
import com.quickwolf.bouncer.domain.TripAttendAttemptHistory;
import com.quickwolf.bouncer.repository.TripAttendAttemptRepository;
import com.quickwolf.bouncer.service.AttendAttemptHistoryService;
import com.quickwolf.domain.Order;
import com.quickwolf.web.repository.OrderRepository;
import com.quickwolf.web.service.BlowfishCryptoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class AttendAttemptHistoryServiceImpl implements AttendAttemptHistoryService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private TripAttendAttemptRepository tripAttendAttemptRepository;

    @Autowired
    private BlowfishCryptoService cryptoService;

    @Override
    public TripAttendAttemptHistory getAttendAttemptHistory(String encryptedOrderId) {
        String decryptedOrderId = cryptoService.dencrypt(encryptedOrderId);
        if (!decryptedOrderIdIsNumeric(decryptedOrderId))
            return new TripAttendAttemptHistory();
        long orderId = Long.valueOf(decryptedOrderId);
        Optional<Order> order = orderRepository.findById(orderId);
        if (!order.isPresent())
            return new TripAttendAttemptHistory();
        Long ticketId = order.get().getTicket().getId();
        List<TripAttendAttempt> attempts = tripAttendAttemptRepository.findAttendAttemptsForTicket(ticketId);
        return new TripAttendAttemptHistory(attempts);
    }

    private boolean decryptedOrderIdIsNumeric(String decryptedOrderId) {
        Pattern p = Pattern.compile("\\d+");
        return p.matcher(decryptedOrderId)
                .matches();
    }
}
