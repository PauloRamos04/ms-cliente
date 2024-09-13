package saudeconectada.fatec.validators.patient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;
import saudeconectada.fatec.domain.model.Verifiable;
import saudeconectada.fatec.repository.PatientRepository;
import saudeconectada.fatec.service.UserRepositoryService;

@Component
public class PatientGetCpfValidator implements PatientValidator{

    @Autowired
    private PatientRepository patientRepository;

    @Override
    public void validar(Object valor){
        if (valor instanceof String) {
            String cpf = (String) valor;
            if (patientRepository.existsByCpf(cpf)) {
                throw new BadCredentialsException("CPF já cadastrado");
            }
        }
    }
}
