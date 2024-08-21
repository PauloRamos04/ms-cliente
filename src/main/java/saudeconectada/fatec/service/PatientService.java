package saudeconectada.fatec.service;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import saudeconectada.fatec.domain.dto.PatientDTO;
import saudeconectada.fatec.domain.model.Patient;
import saudeconectada.fatec.repository.PatientRepository;

import java.util.List;


@Service
public class PatientService {

    private static final Logger logger = LoggerFactory.getLogger(PatientService.class);

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<Patient> getPatients() {
        return this.patientRepository.findAll();
    }

    public Patient createPatient(PatientDTO patientDTO) {
        logger.info("Iniciando o cadastro do paciente: {}", patientDTO);

        Patient patient = modelMapper.map(patientDTO, Patient.class);

        // Configura os filhos, se houver
        if (patient.getChildren() != null) {
            patient.getChildren().forEach(child -> child.setParent(patient));
        }

        return patientRepository.save(patient);
    }
}
