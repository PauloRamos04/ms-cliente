package saudeconectada.fatec.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import saudeconectada.fatec.infra.security.TokenService;
import saudeconectada.fatec.validators.user.UserValidator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public abstract class UserService<T> {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private List<UserValidator> validators;

    private final Map<String, String> loggedInUsers = new HashMap<>();

    public abstract void registerUser(T userDTO);

    public synchronized String loginUser(String cpf, String password) {

        try {
            validators.forEach(v -> v.validar(cpf));
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(cpf, password);
            Authentication authentication = authenticationManager.authenticate(authToken);
            String token = tokenService.generateToken((UserDetails) authentication.getPrincipal());
            loggedInUsers.put(cpf, token);
            logger.info("Login bem-sucedido para CPF: {}", cpf);
            return token;
        } catch (BadCredentialsException e) {
            logger.error("CPF ou senha inválidos para CPF: {}", cpf);
            throw new BadCredentialsException("CPF ou senha inválidos.");
        }
    }

    public void logoutUser(String cpf) {
        loggedInUsers.remove(cpf);
    }

    public boolean isUserLogged(String cpf) {
        return loggedInUsers.containsKey(cpf);
    }
}
