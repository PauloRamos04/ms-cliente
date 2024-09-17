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
import saudeconectada.fatec.service.UserService;

import java.util.List;

@RestController
@RequestMapping("api/patient")
public class PatientController extends UserController<PatientDTO> {

    @Autowired
    private PatientService patientService;

    @Override
    protected UserService<PatientDTO> getUserService() {
        return patientService;
    }

    @GetMapping
    public ResponseEntity<List<Patient>> getAllPatients() {
        List<Patient> patients = patientService.getPatients();
        if (patients.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(patients);
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerPatient(@Validated @RequestBody PatientDTO patientDTO) {
        patientService.registerUser(patientDTO);
        return ResponseEntity.ok("Paciente cadastrado com sucesso.");
    }

}
