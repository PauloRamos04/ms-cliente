package saudeconectada.fatec.infra.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import saudeconectada.fatec.domain.model.Patient;
import saudeconectada.fatec.repository.PatientRepository;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private PatientRepository patientRepository;

    public void sendEmail(String to, String subject, String body) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body, true);
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new RuntimeException("Falha ao enviar o email", e);
        }
    }

    public void sendVerifyMail(Patient patient) {
        String to = patient.getEmail();
        String subject = "Verifique seu e-mail";
        String body = generateVerifyMailBody(patient.getVerificationToken().toString());
        sendEmail(to, subject, body);
    }

    private String generateVerifyMailBody(String token) {
        return "<html>" +
                "<head>" +
                "<style>" +
                "body { font-family: Arial, sans-serif; line-height: 1.6; margin: 0; padding: 0; background-color: #f7f9fc; }" +
                ".container { width: 100%; max-width: 600px; margin: 20px auto; padding: 20px; background: #ffffff; border-radius: 8px; box-shadow: 0 2px 10px rgba(0,0,0,0.1); }" +
                ".header { background: #003366; color: #ffffff; padding: 20px; text-align: center; border-radius: 8px 8px 0 0; }" +
                ".header h1 { margin: 0; font-size: 36px; color: #ffffff; }" +
                ".content { padding: 20px; }" +
                "h1 { margin: 0 0 20px 0; font-size: 28px; color: #003366; }" +
                "p { margin: 10px 0; font-size: 16px; color: #333; }" +
                ".button { display: inline-block; font-size: 16px; color: #ffffff; background: #007bff; padding: 12px 24px; text-decoration: none; border-radius: 5px; text-align: center; }" +
                ".footer { margin-top: 20px; font-size: 14px; color: #777; text-align: center; padding-top: 20px; border-top: 1px solid #ddd; }" +
                ".footer p { margin: 10px 0; }" +
                ".footer a { color: #007bff; text-decoration: none; }" +
                "</style>" +
                "</head>" +
                "<body>" +
                "<div class='container'>" +
                "<div class='header'>" +
                "<h1>SAÚDE</h1>" +
                "<h1>CONECTADA</h1>" +
                "</div>" +
                "<div class='content'>" +
                "<p>Prezado(a),</p>" +
                "<p>Obrigado por se registrar na nossa plataforma. Para concluir o seu cadastro e ativar sua conta, por favor, clique no botão abaixo:</p>" +
                "<p><a href='http://localhost:8080/verification-result?token=" + token + "' class='button'>Verificar E-mail</a></p>" +
                "<p>Se você não se registrou ou se não reconhece esta solicitação, por favor ignore este e-mail.</p>" +
                "<p>Para mais informações ou assistência, não hesite em entrar em contato com nossa equipe de suporte.</p>" +
                "</div>" +
                "<div class='footer'>" +
                "<p>Atenciosamente,</p>" +
                "<p>Equipe Saúde Conectada</p>" +
                "<p>Rua Exemplo, 123 - Bairro - Cidade - Estado - CEP</p>" +
                "<p>Telefone: (11) 1234-5678 | Email: <a href='mailto:support@example.com'>support@example.com</a></p>" +
                "<p><a href='https://example.com'>Visite nosso site</a></p>" +
                "</div>" +
                "</div>" +
                "</body>" +
                "</html>";
    }





}
