package saudeconectada.fatec.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import saudeconectada.fatec.domain.dto.LoginRequest;
import saudeconectada.fatec.domain.dto.PatientDTO;
import saudeconectada.fatec.domain.model.Patient;
import saudeconectada.fatec.domain.model.Verifiable;
import saudeconectada.fatec.infra.security.TokenService;

import java.util.HashMap;
import java.util.Map;

public abstract class UserService<T> {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    private final Map<String, String> loggedInUsers = new HashMap<>();

    public abstract void registerUser(T userDTO);

    public String loginUser(String cpf, String password) {

        Verifiable user = findUserByCpf(cpf);

        if (user == null) {
            logger.warn("Usuário com CPF {} não encontrado.", cpf);
            throw new IllegalArgumentException("CPF não encontrado.");
        }

        if (loggedInUsers.containsKey(cpf)) {
            logger.warn("Usuário já está logado: {}", cpf);
            throw new IllegalStateException("Usuário já está logado.");
        }

        if (!user.isVerified()) {
            logger.warn("Usuário com CPF {} ainda não confirmou o e-mail.", cpf);
            throw new IllegalStateException("Usuário não confirmou o e-mail.");
        }

        try {
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(cpf, password);
            Authentication authentication = authenticationManager.authenticate(authToken);
            String token = tokenService.generateToken((UserDetails) authentication.getPrincipal());
            loggedInUsers.put(cpf, token);
            logger.info("Login bem-sucedido para CPF: {}", cpf);
            return token;
        } catch (BadCredentialsException e) {
            logger.error("CPF ou senha inválidos para CPF: {}", cpf);
            throw new IllegalArgumentException("CPF ou senha inválidos.");
        }
    }

    protected abstract Verifiable findUserByCpf(String cpf);

    public void logoutUser(String cpf) {
        loggedInUsers.remove(cpf);
    }
}
