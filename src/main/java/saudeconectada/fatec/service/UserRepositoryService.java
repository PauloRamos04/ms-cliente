package saudeconectada.fatec.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import saudeconectada.fatec.domain.model.Verifiable;
import saudeconectada.fatec.repository.HealthProfessionalRepository;
import saudeconectada.fatec.repository.PatientRepository;

@Service
public class UserRepositoryService {

    private final HealthProfessionalRepository healthProfessionalRepository;
    private final PatientRepository patientRepository;

    @Autowired
    public UserRepositoryService(HealthProfessionalRepository healthProfessionalRepository, PatientRepository patientRepository) {
        this.healthProfessionalRepository = healthProfessionalRepository;
        this.patientRepository = patientRepository;
    }

    public Verifiable findUserByCpf(String cpf) {
        Verifiable user = healthProfessionalRepository.findByCpf(cpf);
        if (user == null) {
            user = patientRepository.findByCpf(cpf);
        }
        return user;
    }
}
