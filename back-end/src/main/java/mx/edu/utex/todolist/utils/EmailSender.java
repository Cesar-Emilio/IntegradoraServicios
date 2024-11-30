package mx.edu.utex.todolist.utils;

import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class EmailSender {
    private final static Logger logger = LoggerFactory.getLogger(EmailSender.class);

    @Autowired
    private JavaMailSender emailSender;

    String generateContentId(String prefix) {
        return String.format("%s-%s", prefix, UUID.randomUUID());
    }

    public void sendSimpleMessage(String to, String subject, String text) {
        try {
            MimeMessage mimeMessage = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            String htlmMsg = "<h3 style='color:rgb(0,46,93);'>Gestor de tareas</h3><br>" +
                    text + "<br><hr style='border-color:rgb(0,171,132);'/><p style='text-align:justify;'>Si no solicitaste este cambio, ignora el correo.</p>" +
                    "<p style='text-align:justify;'>Esta cuenta de correo no es supervisada, no responder este mensaje.</p>";

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htlmMsg, true);
            emailSender.send(mimeMessage);
        } catch (Exception e) {
            logger.error("No se pudo enviar el correo");
        }
    }

    public void sendPasswordResetEmail(String to, String resetToken) {
        try {
            MimeMessage mimeMessage = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

            String resetLink = "http://localhost:8080/reset-password?token=" + resetToken;

            String htmlMsg = "<h3 style='color:rgb(0,46,93);'>Restablecer tu contraseña</h3><br>" +
                    "<p style='text-align:justify;'>Para restablecer tu contraseña, haz clic en el siguiente enlace:</p>" +
                    "<p><a href='" + resetLink + "' style='color:rgb(0,171,132);'>Restablecer mi contraseña</a></p>" +
                    "<br><hr style='border-color:rgb(0,171,132);'/>" +
                    "<p style='text-align:justify;'>Si no solicitaste este cambio, por favor ignora este correo.</p>" +
                    "<p style='text-align:justify;'>Esta cuenta de correo no es supervisada, no respondas a este mensaje.</p>";

            // Configuración del correo
            helper.setTo(to);
            helper.setSubject("Restablecer tu contraseña");
            helper.setText(htmlMsg, true);

            // Enviar el correo
            emailSender.send(mimeMessage);
        } catch (Exception e) {
            logger.error("No se pudo enviar el correo de restablecimiento de contraseña", e);
        }
    }

}