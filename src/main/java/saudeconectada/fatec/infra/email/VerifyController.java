package saudeconectada.fatec.infra.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import saudeconectada.fatec.domain.model.HealthProfessional;
import saudeconectada.fatec.domain.model.Patient;
import saudeconectada.fatec.domain.model.Verifiable;


import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.util.UUID;

@RestController
@RequestMapping("auth")
public class VerifyController {

    @Autowired
    private VerifyService verifyService;

    @GetMapping("/verify")
    public ResponseEntity<String> verify(@RequestParam("token") String token, @RequestParam("userType") String userType) {
        try {
            UUID verificationToken = UUID.fromString(token);
            Class<? extends Verifiable> userClass;

            switch (userType.toLowerCase()) {
                case "patient":
                    userClass = Patient.class;
                    break;
                case "healthprofessional":
                    userClass = HealthProfessional.class;
                    break;
                default:
                    return createErrorRedirect("Tipo de usuário inválido.");
            }

            String message = verifyService.verifyUser(verificationToken, userClass);
            return createSuccessRedirect(message);

        } catch (IllegalArgumentException e) {
            return createErrorRedirect("Token inválido.");
        } catch (Exception e) {
            return createErrorRedirect("Erro ao autenticar usuário.");
        }
    }

    private ResponseEntity<String> createSuccessRedirect(String message) {
        try {
            String encodedMessage = URLEncoder.encode(message, "UTF-8");
            return ResponseEntity.status(HttpStatus.FOUND)
                    .location(URI.create("/verification-result.html?message=" + encodedMessage))
                    .build();
        } catch (UnsupportedEncodingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro interno ao processar a mensagem.");
        }
    }

    private ResponseEntity<String> createErrorRedirect(String errorMessage) {
        try {
            String encodedMessage = URLEncoder.encode(errorMessage, "UTF-8");
            return ResponseEntity.status(HttpStatus.FOUND)
                    .location(URI.create("/verification-result.html?message=" + encodedMessage))
                    .build();
        } catch (UnsupportedEncodingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro interno ao processar a mensagem.");
        }
    }
}
