package saudeconectada.fatec.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import saudeconectada.fatec.domain.model.HealthProfessional;
import saudeconectada.fatec.domain.model.Patient;
import saudeconectada.fatec.repository.PatientRepository;
import saudeconectada.fatec.repository.HealthProfessionalRepository;

import java.util.ArrayList;

@Service
public class HealthcareUserDetailsService implements UserDetailsService {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private HealthProfessionalRepository healthProfessionalRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails userDetails = loadPatientByCpf(username);
        if (userDetails != null) {
            return userDetails;
        }
        userDetails = loadHealthProfessionalByCpf(username);
        if (userDetails != null) {
            return userDetails;
        }
        throw new UsernameNotFoundException("Usuário não encontrado com CPF: " + username);
    }

    public UserDetails loadPatientByCpf(String cpf) throws UsernameNotFoundException {
        Patient patient = patientRepository.findByCpf(cpf);
        if (patient == null) {
            return null;
        }
        return new User(patient.getCpf(), patient.getPassword(), new ArrayList<>());
    }

    public UserDetails loadHealthProfessionalByCpf(String cpf) throws UsernameNotFoundException {
        HealthProfessional healthProfessional = healthProfessionalRepository.findByCpf(cpf);
        if (healthProfessional == null) {
            return null;
        }
        return new User(healthProfessional.getCpf(), healthProfessional.getPassword(), new ArrayList<>());
    }
}
