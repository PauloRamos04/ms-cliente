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
        List<HealthProfessional> HealthProfessionals = this.healthProfessionalRepository.findAll();
        return HealthProfessionals;
    }

    public HealthProfessional postHealthProfessional(HealthProfessional HealthProfessional) {
        HealthProfessional newHealthProfessional = this.healthProfessionalRepository.save(HealthProfessional);
        return newHealthProfessional;
    }
}
