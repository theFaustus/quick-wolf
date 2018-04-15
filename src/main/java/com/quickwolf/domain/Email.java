package com.quickwolf.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Email {
  private String host;
  private String port;
  private String fromEmail;
  private String password;
  private String toEmail;
  private String subject;
  private String message;
  private List<String> attachedFiles = new ArrayList<>();

  private Email() {
  }

  public String getHost() {
    return host;
  }

  public void setHost(String host) {
    this.host = host;
  }

  public String getPort() {
    return port;
  }

  public void setPort(String port) {
    this.port = port;
  }

  public String getFromEmail() {
    return fromEmail;
  }

  public void setFromEmail(String fromEmail) {
    this.fromEmail = fromEmail;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getToEmail() {
    return toEmail;
  }

  public void setToEmail(String toEmail) {
    this.toEmail = toEmail;
  }

  public String getSubject() {
    return subject;
  }

  public void setSubject(String subject) {
    this.subject = subject;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public List<String> getAttachedFiles() {
    return attachedFiles;
  }

  public void setAttachedFiles(List<String> attachedFiles) {
    this.attachedFiles = attachedFiles;
  }

  @Override
  public String toString() {
    return String.format(
        "Email{host=%s, port=%s, fromEmail=%s, password=%s, toEmail=%s, subject=%s, message=%s, attachedFiles=%s}",
        host, port, fromEmail, password, toEmail, subject, message, attachedFiles);
  }

  public static EmailBuilder newBuilder() {
    return new EmailBuilder();
  }

  public static class EmailBuilder {
    private Email email = new Email();

    public EmailBuilder setHost(String host) {
      email.host = host;
      return this;
    }

    public EmailBuilder setPort(String port) {
      email.port = port;
      return this;
    }

    public EmailBuilder setFromEmail(String fromEmail) {
      email.fromEmail = fromEmail;
      return this;
    }

    public EmailBuilder setPassword(String password) {
      email.password = password;
      return this;
    }

    public EmailBuilder setToEmail(String toEmail) {
      email.toEmail = toEmail;
      return this;
    }

    public EmailBuilder setSubject(String subject) {
      email.subject = subject;
      return this;
    }

    public EmailBuilder setMessage(String message) {
      email.message = message;
      return this;
    }

    public EmailBuilder setAttachedFiles(List<String> attachedFiles) {
      email.attachedFiles = attachedFiles;
      return this;
    }

    public EmailBuilder loadProperties(Properties props) {
      email.fromEmail = props.getProperty("email");
      email.password = props.getProperty("password");
      email.host = props.getProperty("host");
      email.port = props.getProperty("port");
      return this;
    }

    public Email build() {
      return email;
    }

  }
}
