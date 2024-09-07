package saudeconectada.fatec.infra.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import saudeconectada.fatec.domain.model.Patient;
import saudeconectada.fatec.repository.PatientRepository;

import java.util.UUID;

@RestController
@RequestMapping("auth")
public class VerifyController {

    @Autowired
    private EmailService emailService;

    @Autowired
    private VerifyService verifyService;

    @Autowired
    private PatientRepository patientRepository;

    @GetMapping("/verify")
    public ResponseEntity<String> verify(@RequestParam("token") String token) {
        try {
            UUID verificationToken = UUID.fromString(token);
            String message = verifyService.verifyUser(verificationToken);
            return ResponseEntity.ok(message);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao autenticar paciente.");
        }
    }
}
