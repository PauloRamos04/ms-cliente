package saudeconectada.fatec.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import saudeconectada.fatec.domain.model.HealthProfessional;
import saudeconectada.fatec.repository.HealthProfessionalRepository;

import java.util.List;

@Service
public class HealthProfessionalService {

    @Autowired
    private HealthProfessionalRepository healthProfessionalRepository;

    public List<HealthProfessional> getAllHealthProfessionals() {
        return this.healthProfessionalRepository.findAll();
    }

    public HealthProfessional postHealthProfessional(HealthProfessional healthProfessional) {
        return healthProfessionalRepository.save(healthProfessional);
    }
}
