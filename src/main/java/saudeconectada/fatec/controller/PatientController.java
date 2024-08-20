package saudeconectada.fatec.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import saudeconectada.fatec.domain.dto.PatientDTO;
import saudeconectada.fatec.service.PatientService;

@RestController
@RequestMapping("api/patient")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @PostMapping("/register")
    public ResponseEntity<?> registerPatient(@RequestBody @Validated PatientDTO patientDTO) {
        if (patientDTO.getAddress() == null || patientDTO.getAddress().isEmpty()) {
            return ResponseEntity.badRequest().body("Address cannot be null or empty");
        }
        return ResponseEntity.ok(patientService.createPatient(patientDTO));
    }
}
