package com.quickwolf.web.service.impl;



import com.quickwolf.domain.Order;
import com.quickwolf.domain.TicketType;
import com.quickwolf.web.service.BlowfishCryptoService;
import com.quickwolf.web.service.PdfService;
import com.quickwolf.web.service.QrService;
import com.quickwolf.web.service.TicketService;
import com.quickwolf.web.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.File;

@Service
@Transactional
public class TicketServiceImpl implements TicketService {

    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private PdfService pdfService;

    @Autowired
    private QrService qrService;

    @Autowired
    private FileUtil fileUtil;

    @Autowired
    private BlowfishCryptoService cryptoService;


    @Override
    public File createTicket(Order order, String ticketTemplate) {
        String encryptedOrderId = cryptoService.encrypt(order.getId().toString());
        File qrImage = qrService.generateQrCodeImage(encryptedOrderId);
        Context context = new Context();
        context.setVariable("order", order);
        context.setVariable("qrImagePath", qrImage.getName());
        context.setVariable("orderId", encryptedOrderId);
        String html = templateEngine.process(ticketTemplate, context);
        File pdfFile = fileUtil.createTempFile(".pdf");
        pdfService.createPdf(pdfFile.getAbsolutePath(), html);
        qrImage.delete();
        return pdfFile;
    }

}
