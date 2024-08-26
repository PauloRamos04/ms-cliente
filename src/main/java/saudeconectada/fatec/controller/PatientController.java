package saudeconectada.fatec.controller;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import saudeconectada.fatec.domain.dto.LoginRequest;
import saudeconectada.fatec.domain.dto.LoginResponse;
import saudeconectada.fatec.domain.dto.PatientDTO;
import saudeconectada.fatec.domain.model.Patient;
import saudeconectada.fatec.service.PatientService;

import java.util.List;

@RestController
@RequestMapping("api/patient")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping
    public ResponseEntity<List<Patient>> getAllPatients() {
        try {
            List<Patient> patients = patientService.getPatients();
            if (patients.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }
            return ResponseEntity.ok(patients);
        } catch (Exception e) {
            e.printStackTrace(); // Adicionando log da exceção
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerPatient(@Validated @RequestBody PatientDTO patientDTO) {
        try {
            patientService.registerPatient(patientDTO);
            return ResponseEntity.ok("Paciente cadastrado com sucesso.");
        } catch (ConstraintViolationException e) {
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<?> violation : e.getConstraintViolations()) {
                sb.append(violation.getMessage()).append(", ");
            }
            System.out.println("Dados recebidos: " + patientDTO.toString());
            return ResponseEntity.badRequest().body("Erro de validação: " + sb.toString());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace(); // Adicionando log da exceção
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao cadastrar paciente.");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginPatient(@RequestBody LoginRequest loginRequest) {
        String token = patientService.loginPatient(loginRequest.getCpf(), loginRequest.getPassword());
        return ResponseEntity.ok(new LoginResponse(token, "Login Ok"));
    }
}
