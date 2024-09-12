package saudeconectada.fatec.validators;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import saudeconectada.fatec.domain.model.Verifiable;
import saudeconectada.fatec.repository.UserRepository;
import saudeconectada.fatec.service.UserRepositoryService;

@Component
public class UserVerifyValidator implements UserValidator {

    private static final Logger logger = LoggerFactory.getLogger(UserVerifyValidator.class);

    private final UserRepositoryService userRepositoryService;

    @Autowired
    public UserVerifyValidator(UserRepositoryService userRepositoryService) {
        this.userRepositoryService = userRepositoryService;
    }

    @Override
    public void validar(String cpf) {
        Verifiable user = userRepositoryService.findUserByCpf(cpf);

        if (!user.isVerified()) {
            logger.warn("Usuário com CPF {} ainda não confirmou o e-mail.", cpf);
            throw new IllegalStateException("Usuário não confirmou o e-mail.");
        }
    }

}
