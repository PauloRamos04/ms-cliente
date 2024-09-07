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

    public List<Patient> getPatients() {
        return this.patientRepository.findAll();
    }

    @Override
    public void registerUser(PatientDTO patientDTO) {
        if (patientDTO.getCpf() == null || patientDTO.getCpf().isEmpty()) {
            throw new IllegalArgumentException("O CPF não deve ser nulo ou vazio.");
        }
        if (patientRepository.existsByCpf(patientDTO.getCpf())) {
            throw new IllegalArgumentException("CPF já cadastrado.");
        }
        String encryptedPassword = passwordEncoder.encode(patientDTO.getPassword());
        patientDTO.setPassword(encryptedPassword);
        Patient patient = modelMapper.map(patientDTO, Patient.class);
        patient.setPassword(encryptedPassword);
        patient.setVerificationToken(UUID.randomUUID());
        patientRepository.save(patient);
        emailService.sendVerifyMail(patient);
    }

    @Override
    protected Verifiable findUserByCpf(String cpf) {
        Patient patient = patientRepository.findByCpf(cpf);
        return patient != null ? patient : null;
    }
}
