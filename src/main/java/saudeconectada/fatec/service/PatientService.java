package saudeconectada.fatec.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import saudeconectada.fatec.domain.dto.PatientDTO;
import saudeconectada.fatec.domain.model.Child;
import saudeconectada.fatec.domain.model.Deficiency;
import saudeconectada.fatec.domain.model.Gender;
import saudeconectada.fatec.domain.model.Patient;
import saudeconectada.fatec.repository.PatientRepository;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PatientService {

    private static final Logger logger = LoggerFactory.getLogger(PatientService.class);

    @Autowired
    private PatientRepository patientRepository;

    public Patient createPatient(PatientDTO patientDTO) {
        logger.info("Iniciando o cadastro do paciente: {}", patientDTO);

        Patient patient = new Patient();
        patient.setFirstName(patientDTO.getFirstName());
        patient.setLastName(patientDTO.getLastName());
        patient.setCpf(patientDTO.getCpf());
        patient.setEmail(patientDTO.getEmail());
        patient.setPhone(patientDTO.getPhone());
        patient.setAddress(patientDTO.getAddress());

        // Converte a string para o tipo enum Gender
        patient.setGender(Gender.valueOf(patientDTO.getGender().toUpperCase())); // Assumindo que a string está em um formato compatível

        patient.setBirthDate(patientDTO.getBirthDate());

        // Converte a string para o tipo enum Deficiency, se necessário
        if (patientDTO.getDeficiency() != null) {
            patient.setDeficiency(Deficiency.valueOf(patientDTO.getDeficiency().toUpperCase()));
        }

        patient.setPhoto(patientDTO.getPhoto());
        patient.setPassword(patientDTO.getPassword());

        // Configura os filhos, se houver
        if (patientDTO.getChildren() != null) {
            Set<Child> children = patientDTO.getChildren().stream()
                    .map(childDTO -> new Child(childDTO.getFirstName(), childDTO.getLastName(),
                            childDTO.getBirthDate(),
                            Gender.valueOf(childDTO.getGender().toUpperCase()), // Converte para Gender
                            patient))
                    .collect(Collectors.toSet());
            patient.setChildren(children);
        }

        return patientRepository.save(patient);
    }
}
