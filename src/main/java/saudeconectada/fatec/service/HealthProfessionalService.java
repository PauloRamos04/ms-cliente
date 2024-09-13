package saudeconectada.fatec.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import saudeconectada.fatec.domain.dto.HealthProfessionalDTO;
import saudeconectada.fatec.domain.model.HealthProfessional;
import saudeconectada.fatec.domain.model.Verifiable;
import saudeconectada.fatec.repository.HealthProfessionalRepository;
import saudeconectada.fatec.validators.heathProfessional.HealthProfessionalValidator;

import java.util.List;

@Service
public class HealthProfessionalService extends UserService<HealthProfessionalDTO> {

    @Autowired
    private HealthProfessionalRepository healthProfessionalRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private List<HealthProfessionalValidator> validators;

    public List<HealthProfessional> getAllHealthProfessionals() {
        return this.healthProfessionalRepository.findAll();
    }

    @Override
    public synchronized void registerUser(HealthProfessionalDTO healthProfessionalDTO) {

        validators.forEach(v -> v.validar(healthProfessionalDTO.getCpf()));

        String encryptedPassword = passwordEncoder.encode(healthProfessionalDTO.getPassword());
        healthProfessionalDTO.setPassword(encryptedPassword);
        HealthProfessional healthProfessional = modelMapper.map(healthProfessionalDTO, HealthProfessional.class);
        healthProfessional.setPassword(encryptedPassword);
        healthProfessionalRepository.save(healthProfessional);
    }

}
