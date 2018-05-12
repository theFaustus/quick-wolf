package com.quickwolf.web.service.impl;

import com.quickwolf.domain.Order;
import com.quickwolf.web.service.BlowfishCryptoService;
import com.quickwolf.web.service.PdfService;
import com.quickwolf.web.service.QrService;
import com.quickwolf.web.service.TicketService;
import com.quickwolf.web.util.FileUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Service
public class TicketServiceImpl implements TicketService {
    private static final Logger LOGGER = Logger.getLogger(TicketServiceImpl.class);

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
        Context context = makeContext(order, encryptedOrderId, qrImage);
        File pdfFile = fileUtil.createTempFile(".pdf");
        String html = templateEngine.process(ticketTemplate, context);
        pdfService.createPdf(pdfFile.getAbsolutePath(), html);
        deleteQrCodeImage(qrImage);
        return pdfFile;
    }

    private Context makeContext(Order order, String encryptedOrderId, File qrImage) {
        Context context = new Context();
        context.setVariable("order", order);
        context.setVariable("qrImagePath", qrImage.getName());
        context.setVariable("orderId", encryptedOrderId);
        return context;
    }

    private void deleteQrCodeImage(File qrImage) {
        try {
            Files.delete(qrImage.toPath());
        } catch (IOException e) {
            LOGGER.error("Error while trying to delete the qr-code image.", e);
        }
    }
}
