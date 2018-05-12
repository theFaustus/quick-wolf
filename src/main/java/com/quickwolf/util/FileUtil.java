package com.quickwolf.util;

import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.Date;

@Component
public class FileUtil {

  public File createTempFile() {
    return createTempFile(".tmp");
  }
  
  public File createTempFile(String suffix) {    
    try {
      String salt = String.valueOf(Math.random())
          .replace(".", "_");
      return File.createTempFile("qr_" + new Date().getTime() + "_" + salt, suffix);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
