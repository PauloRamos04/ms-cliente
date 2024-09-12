package saudeconectada.fatec.validators;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import saudeconectada.fatec.domain.model.Verifiable;
import saudeconectada.fatec.repository.PatientRepository;
import saudeconectada.fatec.repository.UserRepository;
import saudeconectada.fatec.service.UserRepositoryService;

@Component
public  class UserGetCpfValidator implements UserValidator {

    private static final Logger logger = LoggerFactory.getLogger(UserGetCpfValidator.class);

    private final UserRepositoryService userRepositoryService;

    @Autowired
    public UserGetCpfValidator(UserRepositoryService userRepositoryService) {
        this.userRepositoryService = userRepositoryService;
    }

    @Override
    public void validar(String cpf) {
        Verifiable user = userRepositoryService.findUserByCpf(cpf);

        if (user == null) {
            logger.error("CPF ou senha inválidos para CPF: {}", cpf);
            throw new IllegalArgumentException("CPF ou senha inválidos.");
        }
    }




}
