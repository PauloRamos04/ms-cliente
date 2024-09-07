package saudeconectada.fatec.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import saudeconectada.fatec.domain.dto.HealthProfessionalDTO;
import saudeconectada.fatec.domain.model.HealthProfessional;
import saudeconectada.fatec.domain.model.Verifiable;
import saudeconectada.fatec.repository.HealthProfessionalRepository;

import java.util.List;

@Service
public class HealthProfessionalService extends UserService<HealthProfessionalDTO> {

    @Autowired
    private HealthProfessionalRepository healthProfessionalRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ModelMapper modelMapper;

    public List<HealthProfessional> getAllHealthProfessionals() {
        return this.healthProfessionalRepository.findAll();
    }

    @Override
    public void registerUser(HealthProfessionalDTO healthProfessionalDTO) {
        if (healthProfessionalDTO.getPassword() == null ||
                healthProfessionalDTO.getPassword().length() < 8 ||
                healthProfessionalDTO.getPassword().length() > 20) {
            throw new IllegalArgumentException("Invalid password");
        }
        if (healthProfessionalRepository.existsByCpf(healthProfessionalDTO.getCpf())) {
            throw new IllegalArgumentException("CPF já cadastrado.");
        }

        String encryptedPassword = passwordEncoder.encode(healthProfessionalDTO.getPassword());
        healthProfessionalDTO.setPassword(encryptedPassword);
        HealthProfessional healthProfessional = modelMapper.map(healthProfessionalDTO, HealthProfessional.class);
        healthProfessional.setPassword(encryptedPassword);
        healthProfessionalRepository.save(healthProfessional);
    }

    @Override
    protected Verifiable findUserByCpf(String cpf) {
        HealthProfessional healthProfessional = healthProfessionalRepository.findByCpf(cpf);
        return healthProfessional != null ? healthProfessional : null;
    }
}
