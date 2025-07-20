package com.example.service;

import com.example.model.BookingNotification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import java.io.ByteArrayOutputStream;
import java.util.Base64;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.OutputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

@Component
public class BookingNotificationListener {
    private static final Logger logger = LoggerFactory.getLogger(BookingNotificationListener.class);

    @Autowired
    private JavaMailSender mailSender;

    @Value("${twilio.account.sid}")
    private String twilioAccountSid;

    @Value("${twilio.auth.token}")
    private String twilioAuthToken;

    @Value("${twilio.whatsapp.number}")
    private String twilioWhatsappNumber;

    @Value("${twilio.sms.number}")
    private String twilioSmsNumber;

    @Value("${twilio.whatsapp.template.sid}")
    private String twilioWhatsappTemplateSid;

    @JmsListener(destination = "bookingNotificationQueue")
    public void onBookingNotification(BookingNotification notification) {
        logger.info("[JMS Listener] Received booking notification: {} | Email: {} | WhatsApp/SMS: {} | Movie: {} | Date: {} | Time: {} | Seat: {} | Price: {}",
            notification.getBookingUserName(),
            notification.getBookingUserEmail(),
            notification.getBookingUserMobile(),
            notification.getMovieName(),
            notification.getMovieDate(),
            notification.getMovieTime(),
            notification.getSeatNumber(),
            notification.getPrice()
        );
        try {
            // Compose detailed booking message
            String bookingDetails = String.format(
                "🎬 Booking Confirmed!\n\n" +
                "👤 Name: %s\n" +
                "📧 Email: %s\n" +
                "📱 Phone: %s\n" +
                "🎭 Movie: %s\n" +
                "📅 Date: %s\n" +
                "⏰ Time: %s\n" +
                "💺 Seat: %s\n" +
                "💰 Price: ₹%s\n\n" +
                "Thank you for choosing MovieBooking!",
                notification.getBookingUserName(),
                notification.getBookingUserEmail(),
                notification.getBookingUserMobile(),
                notification.getMovieName(),
                notification.getMovieDate(),
                notification.getMovieTime(),
                notification.getSeatNumber(),
                notification.getPrice()
            );

            // Generate QR code with all booking details
            String qrCodeData = String.format(
                "Booking Details:\n" +
                "Name: %s\n" +
                "Email: %s\n" +
                "Phone: %s\n" +
                "Movie: %s\n" +
                "Date: %s\n" +
                "Time: %s\n" +
                "Seat: %s\n" +
                "Price: ₹%s",
                notification.getBookingUserName(),
                notification.getBookingUserEmail(),
                notification.getBookingUserMobile(),
                notification.getMovieName(),
                notification.getMovieDate(),
                notification.getMovieTime(),
                notification.getSeatNumber(),
                notification.getPrice()
            );

            // Generate QR code as Base64 string
            String qrCodeBase64 = generateQRCode(qrCodeData);

            // Send email with QR code
            sendEmailWithQRCode(notification, bookingDetails, qrCodeBase64);
            logger.info("[JMS Listener] Email sent to: {}", notification.getBookingUserEmail());

            // WhatsApp message: Use template messaging for business-initiated messages
            Map<String, String> contentVariables = new HashMap<>();
            // Map your template variables here. Adjust keys as per your template.
            contentVariables.put("1", notification.getMovieDate()); // Example: date
            contentVariables.put("2", notification.getMovieTime()); // Example: time
            // Add more variables as needed for your template

            Twilio.init(twilioAccountSid, twilioAuthToken);
            Message.creator(
                new com.twilio.type.PhoneNumber("whatsapp:" + notification.getBookingUserMobile()),
                new com.twilio.type.PhoneNumber(twilioWhatsappNumber),
                "" // body is ignored for template messages
            )
            .setContentSid(twilioWhatsappTemplateSid)
            .setContentVariables(new org.json.JSONObject(contentVariables).toString())
            .create();
            logger.info("[JMS Listener] WhatsApp template message sent to: {}", notification.getBookingUserMobile());

            // SMS: Ensure E.164 format, improve error logging, use dynamic ticket link
            String smsNumber = notification.getBookingUserMobile();
            if (!smsNumber.startsWith("+")) {
                smsNumber = "+91" + smsNumber.replaceFirst("^91", "");
                logger.warn("SMS number was not in E.164 format, converted to: {}", smsNumber);
            }
            // Define ticketLink before using it
            String ticketLink = "https://yourdomain.com/ticket/" + notification.getBookingId();
            try {
                logger.info("Sending SMS to: {}", smsNumber);
                String smsText = bookingDetails + "\nView your ticket: " + ticketLink;
                Message smsMessage = Message.creator(
                    new com.twilio.type.PhoneNumber(smsNumber),
                    new com.twilio.type.PhoneNumber(twilioSmsNumber),
                    smsText
                ).create();
                logger.info("SMS sent. SID: {}", smsMessage.getSid());
            } catch (Exception smsEx) {
                logger.error("Error sending SMS: " + smsEx.getMessage(), smsEx);
            }

        } catch (Exception e) {
            logger.error("[JMS Listener] Error sending notification: " + e.getMessage(), e);
        }
    }

    private String generateQRCode(String data) throws WriterException {
        try {
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(data, BarcodeFormat.QR_CODE, 200, 200);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream);
            String base64 = Base64.getEncoder().encodeToString(outputStream.toByteArray());
            if (base64 == null || base64.isEmpty()) {
                logger.error("QR code generation failed or returned empty string!");
            } else {
                logger.info("QR code generated successfully, length: {}", base64.length());
            }
            return base64;
        } catch (Exception e) {
            logger.error("Error generating QR code: " + e.getMessage(), e);
            return null;
        }
    }

    private void sendEmailWithQRCode(BookingNotification notification, String bookingDetails, String qrCodeBase64) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            
            helper.setTo(notification.getBookingUserEmail());
            helper.setSubject("🎬 Your Movie Booking Confirmation");
            
            // Ensure no line breaks in the Base64 string
            if (qrCodeBase64 != null) {
                qrCodeBase64 = qrCodeBase64.replaceAll("\r|\n", "");
                logger.info("QR code Base64 (first 100 chars): {}", qrCodeBase64.substring(0, Math.min(100, qrCodeBase64.length())));
            }

            String htmlContent = String.format(
                "<!DOCTYPE html>" +
                "<html>" +
                "<head><meta charset='UTF-8'></head>" +
                "<body style='font-family: Arial, sans-serif; max-width: 600px; margin: 0 auto;'>" +
                "<div style='background: linear-gradient(135deg, #667eea 0%%, #764ba2 100%%); color: white; padding: 20px; text-align: center; border-radius: 10px 10px 0 0;'>" +
                "<h1 style='margin: 0;'>🎬 Booking Confirmed!</h1>" +
                "<p style='margin: 10px 0 0 0;'>Thank you for choosing MovieBooking</p>" +
                "</div>" +
                "<div style='background: white; padding: 20px; border: 1px solid #ddd; border-radius: 0 0 10px 10px;'>" +
                "<h2 style='color: #333;'>Booking Details</h2>" +
                "<table style='width: 100%%; border-collapse: collapse; margin-bottom: 20px;'>" +
                "<tr><td style='padding: 8px; border-bottom: 1px solid #eee;'><strong>Name:</strong></td><td style='padding: 8px; border-bottom: 1px solid #eee;'>%s</td></tr>" +
                "<tr><td style='padding: 8px; border-bottom: 1px solid #eee;'><strong>Email:</strong></td><td style='padding: 8px; border-bottom: 1px solid #eee;'>%s</td></tr>" +
                "<tr><td style='padding: 8px; border-bottom: 1px solid #eee;'><strong>Phone:</strong></td><td style='padding: 8px; border-bottom: 1px solid #eee;'>%s</td></tr>" +
                "<tr><td style='padding: 8px; border-bottom: 1px solid #eee;'><strong>Movie:</strong></td><td style='padding: 8px; border-bottom: 1px solid #eee;'>%s</td></tr>" +
                "<tr><td style='padding: 8px; border-bottom: 1px solid #eee;'><strong>Date:</strong></td><td style='padding: 8px; border-bottom: 1px solid #eee;'>%s</td></tr>" +
                "<tr><td style='padding: 8px; border-bottom: 1px solid #eee;'><strong>Time:</strong></td><td style='padding: 8px; border-bottom: 1px solid #eee;'>%s</td></tr>" +
                "<tr><td style='padding: 8px; border-bottom: 1px solid #eee;'><strong>Seat:</strong></td><td style='padding: 8px; border-bottom: 1px solid #eee;'>%s</td></tr>" +
                "<tr><td style='padding: 8px; border-bottom: 1px solid #eee;'><strong>Price:</strong></td><td style='padding: 8px; border-bottom: 1px solid #eee;'>₹%s</td></tr>" +
                "</table>" +
                // Remove QR code section and add thank you quote
                "<div style='text-align: center; margin-top: 30px; padding: 20px; background: #f4f6fb; border-radius: 8px; font-size: 1.3rem; color: #333;'>" +
                "<strong>Thank you for booking with us!<br>We wish you a wonderful movie experience! 🍿🎉</strong>" +
                "</div>" +
                "<div style='text-align: center; margin-top: 20px; padding-top: 20px; border-top: 1px solid #eee;'>" +
                "<p style='color: #666;'>Enjoy your movie! 🍿</p>" +
                "</div>" +
                "</div>" +
                "</body>" +
                "</html>",
                notification.getBookingUserName(),
                notification.getBookingUserEmail(),
                notification.getBookingUserMobile(),
                notification.getMovieName(),
                notification.getMovieDate(),
                notification.getMovieTime(),
                notification.getSeatNumber(),
                notification.getPrice()
            );
            
            helper.setText(htmlContent, true);
            mailSender.send(message);
            
            logger.info("Email sent to {}. QR code present: {}", notification.getBookingUserEmail(), qrCodeBase64 != null && !qrCodeBase64.isEmpty());
        } catch (Exception e) {
            logger.error("Error sending email: " + e.getMessage(), e);
        }
    }

    // Helper method to upload base64 image to Imgur and return the image URL
    private String uploadToImgur(String base64Image) {
        try {
            String clientId = "YOUR_IMGUR_CLIENT_ID"; // <-- Replace with your Imgur Client-ID
            URL url = new URL("https://api.imgur.com/3/image");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", "Client-ID " + clientId);
            conn.setDoOutput(true);
            String data = "image=" + base64Image;
            try (OutputStream os = conn.getOutputStream()) {
                os.write(data.getBytes());
            }
            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                    String inputLine;
                    StringBuilder response = new StringBuilder();
                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    JSONObject json = new JSONObject(response.toString());
                    return json.getJSONObject("data").getString("link");
                }
            } else {
                logger.error("Imgur upload failed, response code: {}", responseCode);
            }
        } catch (Exception e) {
            logger.error("Error uploading image to Imgur: " + e.getMessage(), e);
        }
        return null;
    }
} 