package saudeconectada.fatec.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import saudeconectada.fatec.domain.dto.PatientDTO;
import saudeconectada.fatec.domain.model.Patient;
import saudeconectada.fatec.domain.model.Verifiable;
import saudeconectada.fatec.infra.email.EmailService;
import saudeconectada.fatec.repository.PatientRepository;
import saudeconectada.fatec.validators.patient.PatientGetCpfValidator;
import saudeconectada.fatec.validators.patient.PatientGetEmailValidator;
import saudeconectada.fatec.validators.patient.PatientValidator;
import saudeconectada.fatec.validators.user.UserValidator;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class PatientService extends UserService<PatientDTO> {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private EmailService emailService;

    @Autowired
    private List<PatientValidator> validators;

    public List<Patient> getPatients() {
        return this.patientRepository.findAll();
    }

    @Override
    public synchronized void registerUser(PatientDTO patientDTO) {

        validators.forEach(v -> {
            v.validar(patientDTO.getCpf());
            v.validar(patientDTO.getEmail());
        });

        String encryptedPassword = passwordEncoder.encode(patientDTO.getPassword());
        patientDTO.setPassword(encryptedPassword);
        Patient patient = modelMapper.map(patientDTO, Patient.class);
        patient.setPassword(encryptedPassword);
        patient.setVerificationToken(UUID.randomUUID());
        emailService.sendVerifyMail(patient);
        patientRepository.save(patient);

    }


}
