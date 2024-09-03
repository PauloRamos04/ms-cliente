package saudeconectada.fatec.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import saudeconectada.fatec.domain.dto.HealthProfessionalDTO;
import saudeconectada.fatec.domain.model.HealthProfessional;
import saudeconectada.fatec.service.HealthProfessionalService;
import saudeconectada.fatec.service.UserService;

import java.util.List;

@RestController
@RequestMapping("api/healthprofessional")
public class HealthProfessionalController extends UserController<HealthProfessionalDTO> {

    @Autowired
    private HealthProfessionalService healthProfessionalService;

    @Override
    protected UserService<HealthProfessionalDTO> getUserService() {
        return healthProfessionalService;
    }

    @GetMapping
    public ResponseEntity<List<HealthProfessional>> getHealthProfessionals() {
        List<HealthProfessional> healthProfessionals = healthProfessionalService.getAllHealthProfessionals();
        if (healthProfessionals.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(healthProfessionals);
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerHealthProfessional(@Validated @RequestBody HealthProfessionalDTO healthProfessionalDTO) {
        try {
            healthProfessionalService.registerUser(healthProfessionalDTO);
            return ResponseEntity.ok("Profissional de saúde cadastrado com sucesso.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao cadastrar profissional de saúde.");
        }
    }
}
