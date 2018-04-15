package com.quickwolf.web.service;

import java.io.File;

public interface QrService {
  
  File generateQrCodeImage(String content);
}
