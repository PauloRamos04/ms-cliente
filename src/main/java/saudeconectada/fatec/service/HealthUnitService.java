package saudeconectada.fatec.service;

import org.hibernate.SessionFactory;
import org.hibernate.query.sql.internal.ParameterRecognizerImpl;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import saudeconectada.fatec.domain.model.HealthProfessional;
import saudeconectada.fatec.domain.model.HealthUnit;
import saudeconectada.fatec.repository.HealthUnitRepository;
import saudeconectada.fatec.repository.PatientRepository;

import java.util.List;

@Service
public class HealthUnitService {

    @Autowired
    private HealthUnitRepository healthUnitRepository;

    public List<HealthUnit> getAllHealthUnits() {
        return healthUnitRepository.findAll();
    }

    public HealthUnit postHealthUnit(HealthUnit healthUnit) {
       return healthUnitRepository.save(healthUnit);
    }
}
