package com.quickwolf.web.service.impl;

import com.quickwolf.web.service.BlowfishCryptoService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

@Service
public class BlowfishCryptoServiceImpl implements BlowfishCryptoService {

  @Value("${blowfish.secret.key}")
  private String encriptionKey;

  @Override
  public String encrypt(String dataToEncrypt) {
    try {
      SecretKeySpec secretkey = new SecretKeySpec(encriptionKey.getBytes(), "Blowfish");
      Cipher cipher = Cipher.getInstance("Blowfish");
      cipher.init(Cipher.ENCRYPT_MODE, secretkey);
      byte[] data = cipher.doFinal(dataToEncrypt.getBytes());
      String encrypted = javax.xml.bind.DatatypeConverter.printHexBinary(data);
      return encrypted;
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public String dencrypt(String encryptedData) {
    try {
      SecretKeySpec secretkey = new SecretKeySpec(encriptionKey.getBytes(), "Blowfish");
      Cipher cipher = Cipher.getInstance("Blowfish");
      cipher.init(Cipher.DECRYPT_MODE, secretkey);
      byte[] data = javax.xml.bind.DatatypeConverter.parseHexBinary(encryptedData);
      return new String(cipher.doFinal(data));
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
