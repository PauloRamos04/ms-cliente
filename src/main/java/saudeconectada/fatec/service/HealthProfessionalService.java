package saudeconectada.fatec.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import saudeconectada.fatec.domain.dto.HealthProfessionalDTO;
import saudeconectada.fatec.domain.model.HealthProfessional;
import saudeconectada.fatec.infra.security.TokenService;
import saudeconectada.fatec.repository.HealthProfessionalRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class HealthProfessionalService {

    @Autowired
    private HealthProfessionalRepository healthProfessionalRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ModelMapper modelMapper;

    public List<HealthProfessional> getAllHealthProfessionals() {
        return this.healthProfessionalRepository.findAll();
    }

    public void postHealthProfessional(HealthProfessionalDTO healthProfessionalDTO) {
        if (healthProfessionalDTO.getPassword() == null ||
                healthProfessionalDTO.getPassword().length() < 8 ||
                healthProfessionalDTO.getPassword().length() > 20) {
            throw new IllegalArgumentException("Invalid password");
        }
        String encryptedPassword = passwordEncoder.encode(healthProfessionalDTO.getPassword());
        healthProfessionalDTO.setPassword(encryptedPassword);
        HealthProfessional healthProfessional = modelMapper.map(healthProfessionalDTO, HealthProfessional.class);
        healthProfessional.setPassword(encryptedPassword);
        healthProfessionalRepository.save(healthProfessional);
    }

    private Map<String, String> loggedInUsers = new HashMap<>();

    public String loginHealthProfessional(String cpf, String password) {
        if (loggedInUsers.containsKey(cpf)) {
            throw new IllegalStateException("Profissional de saúde já está logado.");
        }

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(cpf, password);
        Authentication authentication = authenticationManager.authenticate(authToken);
        String token = tokenService.generateToken((UserDetails) authentication.getPrincipal());

        loggedInUsers.put(cpf, token);
        return token;
    }

    public void logoutHealthProfessional(String cpf) {
        loggedInUsers.remove(cpf);
    }
}
