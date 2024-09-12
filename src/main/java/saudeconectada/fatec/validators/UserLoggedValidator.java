package saudeconectada.fatec.validators;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import saudeconectada.fatec.domain.model.Verifiable;
import saudeconectada.fatec.service.UserService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class UserLoggedValidator implements UserValidator {

    private final List<UserService<?>> userServices;

    @Autowired
    public UserLoggedValidator(@Lazy List<UserService<?>> userServices) {
        this.userServices = userServices;
    }

    @Override
    public void validar(String cpf) {
        for (UserService<?> service : userServices) {
            if (service.isUserLogged(cpf)) {
                throw new IllegalStateException("Usuário já está logado.");
            }
        }
    }

}
