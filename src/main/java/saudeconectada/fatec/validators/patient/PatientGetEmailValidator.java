package saudeconectada.fatec.validators.patient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import saudeconectada.fatec.repository.PatientRepository;

@Component
public class PatientGetEmailValidator implements PatientValidator {

    @Autowired
    private PatientRepository patientRepository;

    public void validar(Object valor) {
        if (valor instanceof String) {
            String email = (String) valor;
            if (patientRepository.existsByEmail(email)) {
                throw new IllegalArgumentException("E-mail já cadastrado");
            }
        }
    }
}
