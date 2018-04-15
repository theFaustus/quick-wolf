package com.quickwolf.web.service;

public interface BlowfishCryptoService {
  
  String encrypt(String data);
  
  String dencrypt(String encryptedData);
}
