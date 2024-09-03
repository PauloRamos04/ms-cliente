package saudeconectada.fatec.service;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import saudeconectada.fatec.infra.security.TokenService;

import java.util.HashMap;
import java.util.Map;

public abstract class UserService<T> {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ModelMapper modelMapper;

    private Map<String, String> loggedInUsers = new HashMap<>();

    public abstract String registerUser(T userDTO);

    public String loginUser(String cpf, String password) {
        if (loggedInUsers.containsKey(cpf)) {
            logger.warn("Usuário já está logado: {}", cpf);
            throw new IllegalStateException("Usuário já está logado.");
        }
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(cpf, password);
        Authentication authentication = authenticationManager.authenticate(authToken);
        String token = tokenService.generateToken((UserDetails) authentication.getPrincipal());
        loggedInUsers.put(cpf, token);
        logger.info("Login bem-sucedido para CPF: {}", cpf);
        return token;
    }

    public void logoutUser(String cpf) {
        loggedInUsers.remove(cpf);
    }
}
