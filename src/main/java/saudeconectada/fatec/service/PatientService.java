package saudeconectada.fatec.service;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import saudeconectada.fatec.domain.dto.PatientDTO;
import saudeconectada.fatec.domain.model.Patient;
import saudeconectada.fatec.infra.security.TokenService;
import saudeconectada.fatec.repository.PatientRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PatientService {

    private static final Logger logger = LoggerFactory.getLogger(PatientService.class);

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    private Map<String, String> loggedInUsers = new HashMap<>();

    public List<Patient> getPatients() {
        return this.patientRepository.findAll();
    }

    public void registerPatient(PatientDTO patientDTO) {
        if (patientDTO.getPassword() == null ||
                patientDTO.getPassword().length() < 8 ||
                patientDTO.getPassword().length() > 20) {
            throw new IllegalArgumentException("A senha deve ter entre 8 e 20 caracteres.");
        }

        String encryptedPassword = passwordEncoder.encode(patientDTO.getPassword());
        patientDTO.setPassword(encryptedPassword);

        Patient patient = modelMapper.map(patientDTO, Patient.class);
        patient.setPassword(encryptedPassword);

        patientRepository.save(patient);
    }

    public String loginPatient(String cpf, String password) {
        if (loggedInUsers.containsKey(cpf)) {
            throw new IllegalStateException("Paciente já está logado.");
        }

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(cpf, password);
        Authentication authentication = authenticationManager.authenticate(authToken);
        String token = tokenService.generateToken((UserDetails) authentication.getPrincipal());

        loggedInUsers.put(cpf, token);
        return token;
    }

    public void logoutPatient(String cpf) {
        loggedInUsers.remove(cpf);
    }
}
