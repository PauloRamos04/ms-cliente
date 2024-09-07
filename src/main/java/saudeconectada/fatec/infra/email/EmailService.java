package saudeconectada.fatec.infra.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.mail.javamail.JavaMailSender;
import saudeconectada.fatec.domain.model.Patient;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(String to, String subject, String body) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body);
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new RuntimeException("Falha ao enviar o email", e);
        }
    }

    public void sendVerifyMail(Patient patient) {
        String to = patient.getEmail();
        String subject = "Verifique seu email";
        String body = String.format("Clique no link para verificar seu email: http://localhost:8080/auth/verify?token=%s", patient.getVerificationToken());

        sendEmail(to, subject, body);
    }
}
