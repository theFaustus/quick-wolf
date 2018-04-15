package com.quickwolf.web.service.impl;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import com.quickwolf.web.service.PdfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletContext;
import java.io.FileOutputStream;
import java.io.OutputStream;

@Service
public class PdfServiceImpl implements PdfService {

  @Autowired
  private ServletContext servletContext;

  @Override
  public void createPdf(String destinationPdf, String html) {
    try (OutputStream os = new FileOutputStream(destinationPdf)) {
      PdfRendererBuilder builder = new PdfRendererBuilder();
      builder.withHtmlContent(html, servletContext.getResource("/resources/images/")
          .toString());
      builder.toStream(os);
      builder.run();
      os.close();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
