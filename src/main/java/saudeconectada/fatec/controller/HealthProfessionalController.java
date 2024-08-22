package saudeconectada.fatec.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
    public ResponseEntity<?> addHealthProfessional(@RequestBody HealthProfessional healthProfessional) {
        try {
            HealthProfessional newHealthProfessional = healthProfessionalService.postHealthProfessional(healthProfessional);
            return ResponseEntity.ok(healthProfessional);
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
