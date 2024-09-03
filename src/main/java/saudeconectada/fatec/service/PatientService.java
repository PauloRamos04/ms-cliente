package saudeconectada.fatec.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import saudeconectada.fatec.domain.dto.PatientDTO;
import saudeconectada.fatec.domain.model.Patient;
import saudeconectada.fatec.repository.PatientRepository;

import java.util.List;

@Service
public class PatientService extends UserService<PatientDTO> {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ModelMapper modelMapper;

    public List<Patient> getPatients() {
        return this.patientRepository.findAll();
    }

    @Override
    public String registerUser(PatientDTO patientDTO) {
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
        patientRepository.save(patient);
        return "Paciente registrado com sucesso.";
    }
}
