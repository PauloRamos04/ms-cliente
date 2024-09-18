package saudeconectada.fatec.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import saudeconectada.fatec.domain.dto.HealthProfessionalDTO;
import saudeconectada.fatec.domain.model.HealthProfessional;
import saudeconectada.fatec.domain.model.Verifiable;
import saudeconectada.fatec.exception.CustomException;
import saudeconectada.fatec.infra.email.EmailService;
import saudeconectada.fatec.repository.HealthProfessionalRepository;
import saudeconectada.fatec.validators.heathProfessional.HealthProfessionalValidator;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class HealthProfessionalService extends UserService<HealthProfessionalDTO> {

    @Autowired
    private HealthProfessionalRepository healthProfessionalRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private EmailService emailService;

    @Autowired
    private List<HealthProfessionalValidator> validators;

    public List<HealthProfessional> getAllHealthProfessionals() {
        return this.healthProfessionalRepository.findAll();
    }

    public void appluToEntity(HealthProfessionalDTO dto, HealthProfessional healthProfessional){
        modelMapper.map(dto, healthProfessional);
    }

    @Override
    public synchronized void registerUser(HealthProfessionalDTO healthProfessionalDTO) {
            validators.forEach(v -> v.validar(healthProfessionalDTO.getCpf()));
            String encryptedPassword = passwordEncoder.encode(healthProfessionalDTO.getPassword());
            healthProfessionalDTO.setPassword(encryptedPassword);
            HealthProfessional healthProfessional = modelMapper.map(healthProfessionalDTO, HealthProfessional.class);
            healthProfessional.setPassword(encryptedPassword);
            healthProfessional.setVerificationToken(UUID.randomUUID());
            emailService.sendVerifyMail(healthProfessional, "healthprofessional");
            healthProfessionalRepository.save(healthProfessional);
    }

    public HealthProfessionalDTO atualizaUser(Long id, HealthProfessionalDTO healthProfessionalDTO) throws  NoSuchFieldException{
        HealthProfessional healthProfessional =  healthProfessionalRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Profissional de saude não encontrado com esse ID: " + id));

        appluToEntity(healthProfessionalDTO, healthProfessional);
        healthProfessionalRepository.save(healthProfessional);

        return healthProfessionalDTO;


    }
}
