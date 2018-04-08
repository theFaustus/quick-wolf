package com.quickwolf.domain;

import java.util.Date;
import java.util.Properties;
import java.util.Random;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Created by Faust on 5/17/2017.
 */
public class MailSender {
    private String destination;
    private final String from = "help.quickwolf@gmail.com";
    private final String username = "help.quickwolf@gmail.com";
    private final String password = "sezwkqltyjqtpynb";
    private final String host = "smtp.gmail.com";
    private final String port = "587";
    private Session session;

    public MailSender(String destination) {
        this.destination = destination;
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);

        session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
    }

    public void sendBlockingWarning(String mail) {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(destination));
            message.setSubject("Quick Wolf - Account Update");
            message.setContent("<h3> Dear " + mail + ", your account was suspended due to too many reports on your account.</h3>", "text/html");
            Transport.send(message);

            System.out.println("Sent message successfully....");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendUnblockingWarning(String mail) {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(destination));
            message.setSubject("Quick Wolf - Account Update");
            message.setContent("<h3> Dear Mr./Mrs." + mail + ", your account was unlocked successfully.</h3>", "text/html");
            Transport.send(message);

            System.out.println("Sent message successfully....");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendInvoice(Passenger p, Trip t) {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(destination));
            message.setSubject("Quick Wolf - Invoice");
            message.setContent("<head>\n" +
                    "    <meta charset=\"utf-8\">\n" +
                    "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n" +
                    "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
                    "\n" +
                    "    <title>QuickWolf - Invoice</title>\n" +
                    "\n" +
                    "    <!-- Favicons-->\n" +
                    "    <link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.6/css/bootstrap.min.css\" integrity=\"sha384-rwoIResjU2yc3z8GV/NPeZWAv56rSmLldC3R/AZzGRnGxQQKnKkoFVhFQhNUwEyJ\" crossorigin=\"anonymous\">\n" +
                    "    <link rel=\"shortcut icon\" href=\"img/favicon.ico\" type=\"image/x-icon\">\n" +
                    "    <link rel=\"apple-touch-icon\" type=\"image/x-icon\" href=\"img/apple-touch-icon-57x57-precomposed.png\">\n" +
                    "    <link rel=\"apple-touch-icon\" type=\"image/x-icon\" sizes=\"72x72\" href=\"img/apple-touch-icon-72x72-precomposed.png\">\n" +
                    "    <link rel=\"apple-touch-icon\" type=\"image/x-icon\" sizes=\"114x114\" href=\"img/apple-touch-icon-114x114-precomposed.png\">\n" +
                    "    <link rel=\"apple-touch-icon\" type=\"image/x-icon\" sizes=\"144x144\" href=\"img/apple-touch-icon-144x144-precomposed.png\">\n" +
                    "\n" +
                    "    <!-- CSS -->\n" +
                    "    <link href=\"css/base.css\" rel=\"stylesheet\">\n" +
                    "\n" +
                    "    <!-- Google web fonts -->\n" +
                    "    <link href='http://fonts.googleapis.com/css?family=Montserrat:400,700' rel='stylesheet' type='text/css'>\n" +
                    "    <link href='http://fonts.googleapis.com/css?family=Gochi+Hand' rel='stylesheet' type='text/css'>\n" +
                    "    <link href='http://fonts.googleapis.com/css?family=Lato:300,400' rel='stylesheet' type='text/css'>\n" +
                    "\n" +
                    "    <script src=\"https://code.jquery.com/jquery-3.1.1.slim.min.js\" integrity=\"sha384-A7FZj7v+d/sdmMqp/nOQwliLvUsJfDHW+k9Omg/a/EheAdgtzNs3hpfag6Ed950n\" crossorigin=\"anonymous\"></script>\n" +
                    "    <script src=\"https://cdnjs.cloudflare.com/ajax/libs/tether/1.4.0/js/tether.min.js\" integrity=\"sha384-DztdAPBWPRXSA/3eYEEUWrWCy7G5KFbe8fFjk5JAIxUYHKkDx6Qin1DkWx51bBrb\" crossorigin=\"anonymous\"></script>\n" +
                    "    <script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.6/js/bootstrap.min.js\" integrity=\"sha384-vBWWzlZJ8ea9aCX4pEW3rVHjgjt7zpkNpZk+02D9phzyeVkE+jo0ieGizqPLForn\" crossorigin=\"anonymous\"></script>\n" +
                    "\n" +
                    "    <style>\n" +
                    "        .invoice-title h2,\n" +
                    "        .invoice-title h3 {\n" +
                    "            display: inline-block;\n" +
                    "        }\n" +
                    "        .table > tbody > tr > .no-line {\n" +
                    "            border-top: none;\n" +
                    "        }\n" +
                    "        .table > thead > tr > .no-line {\n" +
                    "            border-bottom: none;\n" +
                    "        }\n" +
                    "        .table > tbody > tr > .thick-line {\n" +
                    "            border-top: 2px solid;\n" +
                    "        }\n" +
                    "    </style>\n" +
                    "\n" +
                    "</head>\n" +
                    "\n" +
                    "<body style=\"padding: 50px 150px 50px 150px;\">\n" +
                    "\n" +
                    "\n" +
                    "    <table style=\"width:100%\">\n" +
                    "        <tr>\n" +
                    "\n" +
                    "            <td>\n" +
                    "                <h2>QuickWolf Invoice</h2>\n" +
                    "            </td>\n" +
                    "            <td>\n" +
                    "                <h3 align=\"right\">Order #" + new Random().nextInt(99999) + "</h3>\n" +
                    "            </td>\n" +
                    "\n" +
                    "        </tr>\n" +
                    "        <tr>\n" +
                    "            <td>\n" +
                    "                            <address>\n" +
                    "    \t\t\t\t<strong>Billed To:</strong><br>\n" +
                    "    \t\t\t\t\t" + p.getFirstName() + " " + p.getLastName() + "<br>\n" +
                    "    \t\t\t\t\t" + p.getEmail() + "<br>\n" +
                    "    \t\t\t\t\t" + p.getTelephoneNumber() + "\n" +
                    "    \t\t\t\t</address>\n" +
                    "            </td>\n" +
                    "            <td align=\"right\">\n" +
                    "                <address>\n" +
                    "    \t\t\t\t\t<strong>Payment Method:</strong><br>\n" +
                    "    \t\t\t\t\tCard ending " + p.getCreditCard().getCardNumber().substring(p.getCreditCard().getCardNumber().length() - 4, p.getCreditCard().getCardNumber().length()) + "<br>\n" +
                    "    \t\t\t\t\t" + p.getEmail() + "\n" +
                    "    \t\t\t\t</address>\n" +
                    "\n" +
                    "            </td>\n" +
                    "        </tr>\n" +
                    "        <tr>\n" +
                    "            <td>\n" +
                    "                <address>\n" +
                    "    \t\t\t\t\t<strong>Order Date:</strong><br>\n" +
                    "    \t\t\t\t\t" + new Date().toString() + "<br><br>\n" +
                    "    \t\t\t\t</address>\n" +
                    "            </td>\n" +
                    "            <td align=\"right\">\n" +
                    "                <address>\n" +
                    "    \t\t\t\t\t<strong>Trip details:</strong><br>\n" +
                    "    \t\t\t\t\tDepart : " + t.getTripTime() + ", " + t.getFromAddress() + "<br>\n" +
                    "    \t\t\t\t\tArrive : " + t.getArriveTime() + ", " + t.getDestinationAddress() + "<br><br>\n" +
                    "    \t\t\t\t</address>\n" +
                    "            </td>\n" +
                    "        </tr>\n" +
                    "    </table>\n" +
                    "    <h3 class=\"panel-title\"><strong>Order summary</strong></h3>\n" +
                    "    <table style=\"width: 100%;\" border=\"2\">\n" +
                    "        <thead>\n" +
                    "            <tr>\n" +
                    "                <td><strong>Trip</strong>\n" +
                    "                </td>\n" +
                    "                <td class=\"text-center\"><strong>Price</strong>\n" +
                    "                </td>\n" +
                    "                <td class=\"text-center\"><strong>Quantity</strong>\n" +
                    "                </td>\n" +
                    "                <td class=\"text-right\"><strong>Totals</strong>\n" +
                    "                </td>\n" +
                    "            </tr>\n" +
                    "        </thead>\n" +
                    "        <tbody>\n" +
                    "                                    <tr>\n" +
                    "                                        <td>" + t.getFromAddress() + " to " + t.getDestinationAddress() + "</td>\n" +
                    "                                        <td class=\"text-center\">MDL " + t.getPrice() + "</td>\n" +
                    "                                        <td class=\"text-center\">1</td>\n" +
                    "                                        <td class=\"text-right\">MDL " + t.getPrice() + "</td>\n" +
                    "                                    </tr>\n" +
                    "                                    <tr>\n" +
                    "                                        <td class=\"no-line\"></td>\n" +
                    "                                        <td class=\"no-line\"></td>\n" +
                    "                                        <td class=\"no-line text-center\"><strong>Total</strong>\n" +
                    "                                        </td>\n" +
                    "                                        <td class=\"no-line text-right\">MDL " + t.getPrice() + "</td>\n" +
                    "                                    </tr>\n" +
                    "        </tbody>\n" +
                    "    </table>\n" +
                    "</body>", "text/html");

            Transport.send(message);

            System.out.println("Sent message successfully....");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
