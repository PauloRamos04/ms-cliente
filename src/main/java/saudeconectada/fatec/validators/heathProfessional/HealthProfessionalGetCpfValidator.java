package saudeconectada.fatec.validators.heathProfessional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import saudeconectada.fatec.exception.CustomException;
import saudeconectada.fatec.repository.HealthProfessionalRepository;

@Component
public class HealthProfessionalGetCpfValidator implements HealthProfessionalValidator{

    @Autowired
    private HealthProfessionalRepository healthProfessionalRepository;

    public void validar(String cpf) {
        if (healthProfessionalRepository.existsByCpf(cpf)) {
            throw new CustomException("CPF já cadastrado", "CPF_ALREADY_REGISTERED");
        }
    }
}
