package saudeconectada.fatec.controller;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import saudeconectada.fatec.domain.dto.HealthProfessionalDTO;
import saudeconectada.fatec.domain.dto.LoginRequest;
import saudeconectada.fatec.domain.dto.LoginResponse;
import saudeconectada.fatec.domain.model.HealthProfessional;
import saudeconectada.fatec.service.HealthProfessionalService;

import java.util.List;

@RestController
@RequestMapping("api/healthprofessional")
public class HealthProfessionalController {

    @Autowired
    private HealthProfessionalService healthProfessionalService;

    @GetMapping()
    public ResponseEntity<List<HealthProfessional>> getHealthProfessionals() {
        try {
            List<HealthProfessional> healthProfessionals = healthProfessionalService.getAllHealthProfessionals();
            if(healthProfessionals.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }
            return ResponseEntity.ok(healthProfessionals);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerHealthProfessional(@Validated @RequestBody HealthProfessionalDTO healthProfessionalDTO) {
        try {
            healthProfessionalService.postHealthProfessional(healthProfessionalDTO);
            return ResponseEntity.ok("Profissional de saúde cadastrado com sucesso.");
        } catch (ConstraintViolationException e) {
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<?> violation : e.getConstraintViolations()) {
                sb.append(violation.getMessage()).append(", ");
            }
            System.out.println("Dados recebidos: " + healthProfessionalDTO.toString());
            return ResponseEntity.badRequest().body("Erro de validação: " + sb.toString());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao cadastrar profissional de saúde.");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginHealthProfessional(@RequestBody LoginRequest loginRequest) {
        String token = healthProfessionalService.loginHealthProfessional(loginRequest.getCpf(), loginRequest.getPassword());
        return ResponseEntity.ok(new LoginResponse(token, "Login Ok"));
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logoutHealthProfessional(@RequestBody LoginRequest loginRequest) {
        healthProfessionalService.logoutHealthProfessional(loginRequest.getCpf());
        return ResponseEntity.ok("Logout realizado com sucesso.");
    }


}
