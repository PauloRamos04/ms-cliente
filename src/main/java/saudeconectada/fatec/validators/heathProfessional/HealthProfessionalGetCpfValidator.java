package saudeconectada.fatec.validators.heathProfessional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;
import saudeconectada.fatec.repository.HealthProfessionalRepository;

@Component
public class HealthProfessionalGetCpfValidator implements HealthProfessionalValidator{

    @Autowired
    private HealthProfessionalRepository healthProfessionalRepository;

    public void validar(String cpf){
        if (healthProfessionalRepository.existsByCpf(cpf)){
            throw new BadCredentialsException("CPF ja cadastrado");
        }
    }
}
