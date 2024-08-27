package saudeconectada.fatec.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import saudeconectada.fatec.domain.dto.HealthProfessionalDTO;
import saudeconectada.fatec.domain.model.HealthProfessional;
import saudeconectada.fatec.domain.model.HealthUnit;
import saudeconectada.fatec.infra.security.TokenService;
import saudeconectada.fatec.repository.HealthProfessionalRepository;

import java.util.List;

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
        System.out.println(healthProfessional);

        healthProfessionalRepository.save(healthProfessional);
    }

    public String loginHealthProfessional(String cpf, String password) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(cpf, password);
        Authentication authentication = authenticationManager.authenticate(authToken);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        HealthProfessional professional = healthProfessionalRepository.findByCpf(cpf);
        return tokenService.generateToken(userDetails);
    }
}
