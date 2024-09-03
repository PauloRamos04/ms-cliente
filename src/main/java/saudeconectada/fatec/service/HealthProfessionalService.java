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
import saudeconectada.fatec.infra.security.TokenService;
import saudeconectada.fatec.repository.HealthProfessionalRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class HealthProfessionalService extends UserService<HealthProfessionalDTO> {

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

    private Map<String, String> loggedInUsers = new HashMap<>();

    public List<HealthProfessional> getAllHealthProfessionals() {
        return this.healthProfessionalRepository.findAll();
    }

    @Override
    public String registerUser(HealthProfessionalDTO healthProfessionalDTO) {
        if (healthProfessionalDTO.getPassword() == null ||
                healthProfessionalDTO.getPassword().length() < 8 ||
                healthProfessionalDTO.getPassword().length() > 20) {
            throw new IllegalArgumentException("Invalid password");
        }
        if (healthProfessionalRepository.existsByCpf(healthProfessionalDTO.getCpf())) {
            throw new IllegalArgumentException("CPF já cadastrado.");
        }

        String encryptedPassword = passwordEncoder.encode(healthProfessionalDTO.getPassword());
        healthProfessionalDTO.setPassword(encryptedPassword);
        HealthProfessional healthProfessional = modelMapper.map(healthProfessionalDTO, HealthProfessional.class);
        healthProfessional.setPassword(encryptedPassword);
        healthProfessionalRepository.save(healthProfessional);
        return "Profissional de saúde registrado com sucesso.";
    }
}
