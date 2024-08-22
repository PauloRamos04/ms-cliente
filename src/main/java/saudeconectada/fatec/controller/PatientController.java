package saudeconectada.fatec.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import saudeconectada.fatec.domain.dto.PatientDTO;
import saudeconectada.fatec.domain.model.Patient;
import saudeconectada.fatec.service.PatientService;

import java.util.List;

@RestController
@RequestMapping("api/patient")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @GetMapping
    public ResponseEntity<List<Patient>> getAllPatients() {
        try {
            List<Patient> patients = patientService.getPatients();
            if (patients.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }
            return ResponseEntity.ok(patients);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerPatient(@RequestBody @Validated PatientDTO patientDTO) {
        if (patientDTO.getAddress() == null || patientDTO.getAddress().isEmpty()) {
            return ResponseEntity.badRequest().body("Address cannot be null or empty");
        }
        return ResponseEntity.ok(patientService.createPatient(patientDTO));
    }
}
