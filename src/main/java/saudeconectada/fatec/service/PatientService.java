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

import java.util.List;

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

    public List<Patient> getPatients() {
        return this.patientRepository.findAll();
    }

    public void registerPatient(PatientDTO patientDTO) {
        // Validação da senha
        if (patientDTO.getPassword() == null ||
                patientDTO.getPassword().length() < 8 ||
                patientDTO.getPassword().length() > 20) {
            throw new IllegalArgumentException("A senha deve ter entre 8 e 20 caracteres.");
        }

        // Criptografar a senha
        String encryptedPassword = passwordEncoder.encode(patientDTO.getPassword());
        patientDTO.setPassword(encryptedPassword);

        // Mapear DTO para modelo Patient
        Patient patient = modelMapper.map(patientDTO, Patient.class);
        patient.setPassword(encryptedPassword); // garantir que a senha criptografada seja definida

        // Persistir o paciente
        patientRepository.save(patient);
    }

    public String loginPatient(String cpf, String password) {
        // Criação do token de autenticação
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(cpf, password);
        // Autenticação do token
        Authentication authentication = authenticationManager.authenticate(authToken);

        // Aqui é onde você deve procurar o paciente pelo CPF, não pelo email
        Patient patient = patientRepository.findByCpf(cpf);
        if (patient == null) {
            throw new UsernameNotFoundException("Paciente não encontrado com CPF: " + cpf);
        }

        return tokenService.generateToken((UserDetails) authentication.getPrincipal());
    }
}
